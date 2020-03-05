package dev.longshotdev.idiot.teamPlugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.ChatColor;

import dev.longshotdev.idiot.teamPlugin.IdiotTeamPlugin;
import dev.longshotdev.idiot.teamPlugin.core.Team;
import dev.longshotdev.idiot.teamPlugin.core.TeamList;
import dev.longshotdev.idiot.teamPlugin.core.TeamManager;

public class CreateTeams implements CommandExecutor {
	/*
	 * Creating Teams and putting people on those teams
	 * 
	 * Takes in { teamID } { teamName } 
	 * */
	private TeamManager teamManager;
	public CreateTeams(TeamManager t) {
		teamManager = t;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// Type Checking
		String teamID = args[0];
		String teamName = args[1];
		
		Team teamFactory = new Team(teamName, teamID);
		try {
			teamManager.addTeam(teamFactory);
			sender.sendMessage(ChatColor.GREEN + "Created Team:" + ChatColor.GOLD + teamName);
		} catch(IllegalStateException e) {
			sender.sendMessage(ChatColor.RED + "Error: Maybe there is a team with this name / ID?");
		};

		return true;
	}

}
