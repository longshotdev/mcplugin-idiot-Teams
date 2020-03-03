package dev.longshotdev.idiot.teamPlugin.commands;


import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.longshotdev.idiot.teamPlugin.IdiotTeamPlugin;
import dev.longshotdev.idiot.teamPlugin.core.TeamConfigManager;
import dev.longshotdev.idiot.teamPlugin.core.TeamManager;
import dev.longshotdev.idiot.teamPlugin.core.TeleportManager;

public class ITeleport implements CommandExecutor {
	
	private final IdiotTeamPlugin plugin;
	private final TeamManager teamManager;
	private final TeleportManager teleportManager;
	
	public ITeleport(IdiotTeamPlugin pl, TeamManager tm, TeamConfigManager cfg, TeleportManager tpm) {
		plugin = pl;
		teamManager = tm;
		teleportManager = tpm;
		
	}
	
	@Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
       /*
        * first arg can either be person or { cancel, accept, deny }
        * second arg is person
        * */
		
		// Check what type of Command
		if(sender instanceof Player) {
			Player player = (Player) sender;
			final Player target = plugin.getServer().getPlayer(args[0]);
			long keepAlive = plugin.getConfig().getLong("keep-alive") * 20;
			
			if (target == null) {
				sender.sendMessage(ChatColor.RED + "Error: You can only send a teleport request to online players!");
				return false;
			}
			
			if (target == player) {
				sender.sendMessage(ChatColor.RED + "Error: You can't teleport to yourself!");
				return false;
			}
			if(!teamManager.findPlayersInTeam(player, target)) {
				sender.sendMessage(ChatColor.RED + "Error: You can't teleport to the other team!");
				return false;
			} else {
				teleportManager.sendRequest(player, target);
			}
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				public void run() {
					teleportManager.killRequest(target.getName());
				}
			}, keepAlive);
			
			teleportManager.addPlayerToCooldown(player);

		}

		
        // If the player (or console) uses our command correct, we can return true
        return true;
    }
}
