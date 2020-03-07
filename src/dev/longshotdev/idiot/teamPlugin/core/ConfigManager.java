package dev.longshotdev.idiot.teamPlugin.core;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import dev.longshotdev.idiot.teamPlugin.IdiotTeamPlugin;
public class ConfigManager {
	
	private final IdiotTeamPlugin plugin;
	private ScoreboardManager manager = Bukkit.getScoreboardManager();
	private Scoreboard board = manager.getNewScoreboard();


	public ConfigManager(IdiotTeamPlugin pl) {
		plugin = pl;
	}
	public void loadConfig() {
		plugin.getConfig().addDefault("tpa-cooldown", 5);
		plugin.getConfig().addDefault("keep-alive", 30);
		plugin.getConfig().addDefault("tm", 30);
		plugin.getConfig().addDefault("team-PVP", true);
		plugin.getConfig().addDefault("team-Glow", true);
		plugin.getConfig().createSection("Teams");
		plugin.getConfig().options().copyDefaults(true);
		plugin.saveConfig();
	}
	public void saveTeams( TeamList teamList ) {
		System.out.println("HAHAHAHAHAH");
		for(Team team : teamList.getTeams()) {
			List<UUID> playerList = team.playerList;
			
			List<String> temp = new ArrayList<String>();
			
			for(UUID uid : playerList) {
				temp.add(uid.toString());
			}
			String prefix = "Teams."+team.id;
			plugin.getConfig().set(prefix+".name", team.name);
			plugin.getConfig().set(prefix+".Players", temp);

		};
		plugin.getConfig().options().copyDefaults(true);
		plugin.saveConfig();
	}
	@SuppressWarnings("unchecked")
	public TeamList loadTeams() {
		// this gets all team IDs
		TeamList teamss = new TeamList();
		ConfigurationSection Tteams = plugin.getConfig().getConfigurationSection("Teams");
		if(Tteams == null) {
			plugin.getConfig().createSection("Teams");
			return null;
		}
		Set<String> teams = Tteams.getKeys(false);
		for(String teamID : teams) {
			Team tempTeam = new Team(teamID, plugin.getConfig().getString(String.format("Teams.%s.name", teamID)));
			// Read values inside The team
			List<String> playersList = (List<String>) plugin.getConfig().getList(String.format("Teams.%s.Players", teamID));
			playersList.forEach(i -> tempTeam.addPlayer(UUID.fromString(i)));
			org.bukkit.scoreboard.Team team = board.registerNewTeam(teamID);
			teamss.addTeam(tempTeam);
		}
		

		return teamss;
	}
}
