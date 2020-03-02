package dev.longshotdev.idiot.teamPlugin.core;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import dev.longshotdev.idiot.teamPlugin.IdiotTeamPlugin;

public class TeamList {
	
	private static ArrayList<Team> teams = new ArrayList<Team>();
	
	public TeamList() {
		
	}
	
	public ArrayList<Team> getTeams() {
		return teams;
	}
	public ArrayList<Team> addTeam(Team team) {
		teams.add(team);
		return teams;
	}
	public int getNumTeams() {
		return teams.size();
	}
	/*
	 * this method is just a whole fuck up like shit
	 * 
	 * java litterally wont wait for the for loop to finish it just returns null; even though its in the fucking array
	 * 
	 * the quick  hack
	 * teams.stream().forEach(team -> pl.getLogger().info(String.format("Comparing %s ?? %s ?? %s", team.id, teamID, team.id.equals(teamID))));
	 * 
	 * */
	public Team addPlayerToList(UUID uuid, String teamID,IdiotTeamPlugin pl) {

		return TeamList.searchWithId(teamID).addPlayer(uuid);
	}
	public Team removePlayerFromList(UUID uuid, String teamID) {
		
		return TeamList.searchWithId(teamID).removePlayer(uuid);
	}
	public int getSizeOfTeam(String teamID) {
		for(int i = 0; i < teams.size(); i++) {
			if(teamID == teams.get(i).id) {
				return teams.get(i).getSize();
			}
		}
		return 0;
	}
	
	public static Team searchWithId(String teamID) {
		// Loop thru teams array
				Predicate<Team> equalTo = i -> i.id.equals(teamID);
				List<Team> team = teams.stream().filter(equalTo).collect(Collectors.toList());
				if(team.isEmpty()) {
					/* 
					 * just fuck off java 
					 * go fuck you rself and take a illigal shit with u too
					 * */
					throw new IllegalStateException();
				}
				return team.get(0);
	}
}
