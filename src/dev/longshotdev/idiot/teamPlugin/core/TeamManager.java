package dev.longshotdev.idiot.teamPlugin.core;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import dev.longshotdev.idiot.teamPlugin.IdiotTeamPlugin;

public class TeamManager {
	
	private TeamList teams;
	public TeamManager(TeamList t) {
		teams = t;
	}
	public ArrayList<Team> getTeams() {
		return teams.getTeams();
	}
	public int getNumTeams() {
		return teams.getNumTeams();
	}
	public int getSizeOfTeam(String teamID) {
		return teams.getSizeOfTeam(teamID);
	}
	public Team addPlayerToTeam(Player player, String teamID, IdiotTeamPlugin pl) {
		Team val = teams.addPlayerToList(player.getUniqueId(), teamID, pl);
		return val;
	}
	public Team removePlayerFromTeam(Player player, String teamID) {
		return teams.removePlayerFromList(player.getUniqueId(), teamID);
	}
	public List<String> getTeamNames() {
		return teams.getTeamNames();
	}
}
