package dev.longshotdev.idiot.teamPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedDataWatcher.Registry;
import com.comphenix.protocol.wrappers.WrappedDataWatcher.Serializer;

import dev.longshotdev.idiot.teamPlugin.core.Team;
import dev.longshotdev.idiot.teamPlugin.core.TeamList;
import dev.longshotdev.idiot.teamPlugin.core.TeamManager;
import dev.longshotdev.idiot.teamPlugin.core.UserDataHandler;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class IdiotEventListeners implements Listener {

	private final IdiotTeamPlugin plugin;
	private final TeamManager teamManager;
	private final ProtocolManager protocolManager;
	private int playerJoinTaskID;
	private int playerGlowTaskID;
	private int playerCoordsTaskID;

	public IdiotEventListeners(IdiotTeamPlugin pl, TeamManager tm, ProtocolManager pm) {
		plugin = pl;
		teamManager = tm;
		protocolManager = pm;
	}

	/*
	 * HACK: right here buddy vvv This will litterally break the server if too many
	 * players join.
	 */
	@EventHandler
	public void PlayerJoin(PlayerJoinEvent e) {
		final Player p = e.getPlayer();
		 UserDataHandler user = new UserDataHandler(e.getPlayer().getUniqueId(), plugin);
		 
	        user.createUser(e.getPlayer());
		playerJoinTaskID = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			public void run() {
				ScoreboardManager manager = Bukkit.getScoreboardManager();
				final Scoreboard board = manager.getNewScoreboard();
				final Objective objective = board.registerNewObjective("test", "dummy");
				objective.setDisplaySlot(DisplaySlot.SIDEBAR);
				objective.setDisplayName(String.format("%sTeam: %s", ChatColor.RED,
						isTeamExist(p) != null ? isTeamExist(p).name : "None"));
				Score score = objective.getScore(String.format("%sTeam Glow:%s %s     ", ChatColor.GOLD,
						plugin.getConfig().getBoolean("team-Glow") ? ChatColor.GREEN : ChatColor.RED,
						plugin.getConfig().getBoolean("team-Glow") ? "ON" : "OFF"));
				score.setScore(10);
				Score score1 = objective.getScore(String.format("%sMembers:%s %s     ", ChatColor.GOLD, ChatColor.GREEN,
						isTeamExist(p) != null ? isTeamExist(p).playerList.size() : "None"));
				score1.setScore(9);
				Score score3 = objective.getScore("§6Colors");
				score3.setScore(7);
				p.setScoreboard(board);

			}
		}, 0, 5);

		/*
		 * this is sever intensive
		 * 
		 * */
		if(user.getCoords(p)) {
			playerCoordsTaskID = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					List<Entity> near = p.getNearbyEntities(5000.0D, 500.0D, 5000.0D);
					for (Entity entity : near) {
						if (entity instanceof Player) {
							Player nearPlayer = (Player) entity;
							if (teamManager.findPlayersInTeam(p, nearPlayer)) {
								p.spigot().sendMessage(ChatMessageType.ACTION_BAR,
										new ComponentBuilder(String.format("%sClosest Member:%s %s %sX:%s Y:%s Z:%s",
												ChatColor.GOLD, ChatColor.AQUA, nearPlayer.getName(), ChatColor.RESET,
												(Math.round(nearPlayer.getLocation().getX() * 10)) / 10,
												(Math.round(nearPlayer.getLocation().getY() * 10)) / 10,
												(Math.round(nearPlayer.getLocation().getZ() * 10)) / 10)).create());
							}
						}
					}
				}
			
			}, 0, 5);
		}

		
		if (user.getGlow(p)) {
			playerGlowTaskID = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
				public void run() {
					List<Player> playersInRange = getPlayersWithin(p, 20);
					for (Player playerIR : playersInRange) {
						if (teamManager.teams.searchWithPlayerUUID(p, playerIR)) {
							PacketContainer packet = protocolManager
									.createPacket(PacketType.Play.Server.ENTITY_METADATA);
							packet.getIntegers().write(0, p.getEntityId()); // Set packet's entity id
							WrappedDataWatcher watcher = new WrappedDataWatcher(); // Create data watcher, the Entity
																					// Metadata packet requires this
							Serializer serializer = Registry.get(Byte.class); // Found this through google, needed for
																				// some stupid reason
							watcher.setEntity(p); // Set the new data watcher's target
							watcher.setObject(0, serializer, (byte) (0x40)); // Set status to glowing, found on protocol
																				// page
							packet.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects()); // Make the
																												// packet's
																												// datawatcher
																												// the
																												// one
																												// we
																												// created
							try {
								protocolManager.sendServerPacket(playerIR, packet);
							} catch (InvocationTargetException e) {
								System.out.println("ERROREWOKJRLWELTHEJWLGJAHL");
								e.printStackTrace();
							}
						}
					}
				}
			}, 0, 2 * 2L);
		}
	}

	@EventHandler
	public void PlayerQuit(PlayerQuitEvent e) {
		Bukkit.getServer().getScheduler().cancelTask(playerJoinTaskID);
		Bukkit.getServer().getScheduler().cancelTask(playerGlowTaskID);
		Bukkit.getServer().getScheduler().cancelTask(playerCoordsTaskID);
	}

	private Team isTeamExist(Player p) {
		return plugin.teamManager.findPlayerInTeam(p);
	}

	private List<Player> getPlayersWithin(Player player, int distance) {
		List<Player> res = new ArrayList<Player>();
		int d2 = distance * distance;
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			if (p.getWorld() == player.getWorld() && p.getLocation().distanceSquared(player.getLocation()) <= d2) {
				res.add(p);
			}
		}
		return res;
	}
}
