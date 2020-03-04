package dev.longshotdev.idiot.teamPlugin.core;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.bukkit.entity.Player;

import dev.longshotdev.idiot.teamPlugin.IdiotTeamPlugin;

public class TeamList {
	
	private static ArrayList<Team> teams = new ArrayList<Team>();
	
	public TeamList() {
		
	}
	
	public ArrayList<Team> getTeams() {
		return teams;
	}
	public ArrayList<Team> addTeam(Team team) {
		Predicate<Team> equalTo = i -> i.id.equals(team.id);
		List<Team> teamw = teams.stream().filter(equalTo).collect(Collectors.toList());
		if(!teamw.isEmpty()) {
			throw new IllegalStateException();
		} else {
			System.out.println("YSKETNIGERS");
			teams.add(team);
			return teams;
		}

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
	public List<String> getTeamNames() {
		List<String> temp = new ArrayList<String>();
		teams.forEach((i) -> {
			temp.add(i.name);
		});
		return temp;
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

	public boolean searchWithPlayerUUID(Player p1, Player p2) {
		// TODO Auto-generated method stub
		
		// find teams that user is in
		List<Team> team = teams.stream().filter(i -> i.playerList.contains(p1.getUniqueId())).collect(Collectors.toList());
		if(team.isEmpty()) {
			return false;
		}
		if(team.get(0).playerList.contains(p2.getUniqueId())) {
			return true;
		};
		return false;
		
	}
	public Team searchWithPlayerUUID(Player p1) {
		List<Team> team = teams.stream().filter(i -> i.playerList.contains(p1.getUniqueId())).collect(Collectors.toList());
		if(team.isEmpty()) {
			return null;
		} else {
			return team.get(0);
		}
	}
	public List<String> getTeamIds() {
		List<String> temp = new ArrayList<String>();
		teams.forEach((i) -> {
			temp.add(i.id);
		});
		return temp;
	}

	public Team findTeamByID(String id) {
		List<Team> t = teams.stream().filter(i -> i.id.equalsIgnoreCase(id)).collect(Collectors.toList());
		if(t.isEmpty()) {
			throw new IllegalStateException();
		}
		return t.get(0);
		// TODO Auto-generated method stub
		
	}
}
