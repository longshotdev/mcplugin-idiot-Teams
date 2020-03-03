package dev.longshotdev.idiot.teamPlugin.core;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.longshotdev.idiot.teamPlugin.IdiotTeamPlugin;

public class TeamManager {
	
	private TeamList teams;
	private IdiotTeamPlugin plugin;
	public TeamManager(TeamList t, IdiotTeamPlugin pl) {
		plugin = pl;
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
	public Team addPlayerToTeam(Player player, CommandSender sender, String teamID, IdiotTeamPlugin pl) {
		if(player == null) {
			sender.sendMessage(ChatColor.RED + "Error: Could not add player to team. Maybe they are already in the team?");
			throw new IllegalStateException();
		}
		// TODO: fuck make sure this doesnt take in duplicate players
		// HACK: dont give a fuck about this
			Team team = teams.addPlayerToList(player.getUniqueId(), teamID, pl);
			sender.sendMessage(String.format("%s has been drafted into: Team %s with %s other people.", player.getDisplayName(), team.name, String.valueOf(team.playerList.size())));
			if(player == (Player) sender) {
				
			} else {
				player.sendMessage(String.format("You have been drafted into: Team %s, with %s other people.", team.name, String.valueOf(team.playerList.size())));
			}
			return team;

	}
	public boolean findPlayersInTeam(Player p1, Player p2) {
		return teams.searchWithPlayerUUID(p1, p2);
	}
	public Team removePlayerFromTeam(Player player, String teamID) {
		return teams.removePlayerFromList(player.getUniqueId(), teamID);
	}
	public List<String> getTeamNames() {
		return teams.getTeamNames();
	}
	public List<String> getTeamIDs() {
		// TODO Auto-generated method stub
		return teams.getTeamIds();
	}
}
