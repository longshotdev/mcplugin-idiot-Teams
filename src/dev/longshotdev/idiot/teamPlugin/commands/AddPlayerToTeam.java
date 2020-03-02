package dev.longshotdev.idiot.teamPlugin.commands;

import org.bukkit.Bukkit;
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
		String teamID = args[1];
		plugin.getLogger().info(String.format("%s", player.getName().toString()));
		plugin.getLogger().info(String.format("%s", player.getUniqueId()));
		
		Team team = teamManager.addPlayerToTeam(player, teamID, plugin);
		player.sendMessage(String.format("You have been drafted into: Team %s, with %s other people.", team.name, String.valueOf(team.playerList.size())));
		sender.sendMessage(String.format("%s has been drafted into: Team %s with %s other people.", player.getDisplayName(), team.name, String.valueOf(team.playerList.size())));
        
		// If the player (or console) uses our command correct, we can return true
        return true;
    }
}
