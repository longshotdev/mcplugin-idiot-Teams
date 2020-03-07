package dev.longshotdev.idiot.teamPlugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import dev.longshotdev.idiot.teamPlugin.core.Team;
import dev.longshotdev.idiot.teamPlugin.core.TeamManager;

public class IEditConfig implements CommandExecutor {
	/*
	 * Creating Teams and putting people on those teams
	 * 
	 * Takes in { teamID } { teamName } 
	 * */
	public IEditConfig() {
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		return true;
	}
}
