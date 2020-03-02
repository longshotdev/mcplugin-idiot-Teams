package dev.longshotdev.idiot.teamPlugin.commands;

import java.util.function.Function;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import dev.longshotdev.idiot.teamPlugin.IdiotTeamPlugin;
import dev.longshotdev.idiot.teamPlugin.core.TeamConfigManager;
import dev.longshotdev.idiot.teamPlugin.core.TeamManager;
import dev.longshotdev.idiot.teamPlugin.core.TeleportManager;

public class ITeleport implements CommandExecutor {
	
	private final IdiotTeamPlugin plugin;
	private final TeamManager teamManager;
	private final TeamConfigManager config;
	private final TeleportManager teleportManager;
	
	public ITeleport(IdiotTeamPlugin pl, TeamManager tm, TeamConfigManager cfg, TeleportManager tpm) {
		plugin = pl;
		teamManager = tm;
		config = cfg;
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
			Player destination = Bukkit.getPlayer(args[0]);
			
			if(destination instanceof Player) {
				// they're trying to TP
				
				
				teleportManager.teleport(player, destination, plugin.getLogger());
				
				
				
			} else if(args[0].equalsIgnoreCase("cancel")) {
				// they cancel all requests to person // needs second argument
			} else if(args[0].equalsIgnoreCase("accept")) {
				// they accept request
			} else if(args[0].equalsIgnoreCase("deny")) {
				// they deny request 
			} else {
				// probably put some random ass shit in here 
				return false;
			}
			
		}

		
        // If the player (or console) uses our command correct, we can return true
        return true;
    }
}
