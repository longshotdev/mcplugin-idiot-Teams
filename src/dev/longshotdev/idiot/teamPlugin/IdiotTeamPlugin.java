package dev.longshotdev.idiot.teamPlugin;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;

import dev.longshotdev.idiot.teamPlugin.commands.*;
import dev.longshotdev.idiot.teamPlugin.core.TeamList;
import dev.longshotdev.idiot.teamPlugin.core.TeamManager;
import dev.longshotdev.idiot.teamPlugin.core.TeleportManager;

public class IdiotTeamPlugin extends JavaPlugin {
	
	private TeamList teams = new TeamList();
	private TeamManager teamManager = new TeamManager(teams);
	private TeleportManager teleportManager = new TeleportManager();
	/*
	 * On Enable Event
	 * */
	@Override
	public void onEnable() {
		List<String> list = new ArrayList<String>();
		list.add("listteams");
		// Register command
		this.getCommand("aa").setExecutor(new CommandTest());
		this.getCommand("createteams").setExecutor(new CreateTeams(teams));
		this.getCommand("getteams").setExecutor(new GetTeams(this, teamManager));
		this.getCommand("getteams").setAliases(list);
		this.getCommand("addplayertoteam").setExecutor(new AddPlayerToTeam(this, teamManager));
		this.getCommand("addplayertoteam").setTabCompleter(new AddPlayerToTeamTabCompleter(this, teamManager));
		this.getCommand("itp").setExecutor(new ITeleport(this, teamManager, null, teleportManager));
		this.getCommand("itp").setTabCompleter(new ITeleportTabCompleter(this, teamManager));
	}	
	
	/*
	 * On Disable Event
	 * */
	@Override
	public void onDisable() {
		
	}
}
