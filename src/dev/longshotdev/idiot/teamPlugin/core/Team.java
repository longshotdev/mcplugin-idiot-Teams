package dev.longshotdev.idiot.teamPlugin.core;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.entity.Player;

public class Team {
	
	public String name;
	public String id;
	public ArrayList<UUID> playerList = new ArrayList<UUID>();
	
	public Team(String Cname, String ID) {
		name = Cname;
		id = ID;
	}
	
	public void addPlayer(Player player) {
		playerList.add(player.getUniqueId());
	}
	public Team addPlayer(UUID uuid) {
		playerList.add(uuid);
		return this;
	}
	public void removePlayer(Player player) {
		playerList.remove(player.getUniqueId());
	}
	public Team removePlayer(UUID uuid) {
		playerList.add(uuid);
		return this;
	}
	public int getSize() {
		return playerList.size();
	}
}
