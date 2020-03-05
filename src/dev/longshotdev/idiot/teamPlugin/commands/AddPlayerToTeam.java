package dev.longshotdev.idiot.teamPlugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.longshotdev.idiot.teamPlugin.IdiotTeamPlugin;
import dev.longshotdev.idiot.teamPlugin.core.Team;
import dev.longshotdev.idiot.teamPlugin.core.TeamManager;

public class AddPlayerToTeam implements CommandExecutor {
	
	private IdiotTeamPlugin plugin;
	private TeamManager teamManager;
	
	public AddPlayerToTeam(IdiotTeamPlugin pl, TeamManager tm) {
		plugin = pl;
		teamManager = tm;
	}

	@Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        plugin.getLogger().info(String.format("%s arg1; \n %s arg2", args[0], args[1]));
		Player player = Bukkit.getPlayerExact(args[0]);
		if(player == null) {
			sender.sendMessage("USER IS NEIN NOT IN ZE SERVER RETARDO");
			return false;
		}
		String teamID = args[1];
		plugin.getLogger().info(String.format("%s", player.getName().toString()));
		plugin.getLogger().info(String.format("%s", player.getUniqueId()));
		try {
			teamManager.addPlayerToTeam(player, sender, teamID, plugin);
		} catch(IllegalStateException e) {
			
		}
			
			
		
        
		// If the player (or console) uses our command correct, we can return true
        return true;
    }
}
