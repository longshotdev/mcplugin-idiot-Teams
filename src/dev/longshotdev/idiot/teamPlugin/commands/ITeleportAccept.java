package dev.longshotdev.idiot.teamPlugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import dev.longshotdev.idiot.teamPlugin.IdiotTeamPlugin;
import dev.longshotdev.idiot.teamPlugin.core.TeamConfigManager;
import dev.longshotdev.idiot.teamPlugin.core.TeamManager;
import dev.longshotdev.idiot.teamPlugin.core.TeleportManager;

public class ITeleportAccept implements CommandExecutor {
	
	private final IdiotTeamPlugin plugin;
	private final TeamManager teamManager;
	private final TeleportManager teleportManager;
	
	public ITeleportAccept(IdiotTeamPlugin pl, TeamManager tm, TeleportManager tpm) {
		plugin = pl;
		teamManager = tm;
		teleportManager = tpm;
		
	}
	
	@SuppressWarnings("unused")
	@Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
       /*
        * first arg can either be person or { cancel, accept, deny }
        * second arg is person
        * */
		
		// Check what type of Command
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if (!(player == null)) {
				if (teleportManager.currentRequest.containsKey(player.getName())) {
					
					Player heIsGoingOutOnADate = plugin.getServer().getPlayer(teleportManager.currentRequest.get(player.getName()));
					teleportManager.currentRequest.remove(player.getName());
					
					if (!(heIsGoingOutOnADate == null)) {
						int tm = plugin.getConfig().getInt("tm");
						player.sendMessage(ChatColor.GREEN + "Accepted. Teleporting in "+ String.format("%s", tm) +"seconds.");
						heIsGoingOutOnADate.sendMessage(ChatColor.GREEN + "Accepted. Teleporting in 30 seconds.");
						plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
							public void run() {
								heIsGoingOutOnADate.teleport(player);
								player.sendMessage(ChatColor.GRAY + "Teleporting...");
								heIsGoingOutOnADate.sendMessage(ChatColor.GRAY + "Teleporting...");
							}
						}, tm * 10);
						
					} else {
						sender.sendMessage(ChatColor.RED + "Error: It appears that the person trying to teleport to you doesn't exist anymore. WHOA!");
						return false;
					}
				} else {
					sender.sendMessage(ChatColor.RED + "Error: It doesn't appear that there are any current tp requests. Maybe it timed out?");
					return false;
				}
			} else {
				sender.sendMessage(ChatColor.RED + "Error: The console can't accept teleport requests, silly!");
				return false;
			}
			return true;

		}

		
        // If the player (or console) uses our command correct, we can return true
        return true;
    }
}
