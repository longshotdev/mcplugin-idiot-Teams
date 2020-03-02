package dev.longshotdev.idiot.teamPlugin;

import org.bukkit.plugin.java.JavaPlugin;

import dev.longshotdev.idiot.teamPlugin.commands.*;
import dev.longshotdev.idiot.teamPlugin.core.TeamList;
import dev.longshotdev.idiot.teamPlugin.core.TeamManager;

public class IdiotTeamPlugin extends JavaPlugin {
	
	private TeamList teams = new TeamList();
	private TeamManager teamManager = new TeamManager(teams);
	/*
	 * On Enable Event
	 * */
	@Override
	public void onEnable() {
		// Register command
		this.getCommand("aa").setExecutor(new CommandTest());
		this.getCommand("createteams").setExecutor(new CreateTeams(teams));
		this.getCommand("getteams").setExecutor(new GetTeams(this, teamManager));
		this.getCommand("addplayertoteam").setExecutor(new AddPlayerToTeam(this, teamManager));
	}
	
	/*
	 * On Disable Event
	 * */
	@Override
	public void onDisable() {
		
	}
}
