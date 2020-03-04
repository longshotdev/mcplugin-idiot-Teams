package dev.longshotdev.idiot.teamPlugin.core;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedDataWatcher.Registry;
import com.comphenix.protocol.wrappers.WrappedDataWatcher.Serializer;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;

import dev.longshotdev.idiot.teamPlugin.IdiotTeamPlugin;

public class TeamManager {
	
	private TeamList teams;
	private IdiotTeamPlugin plugin;
	private ProtocolManager protocolManager;
	public TeamManager(TeamList t, IdiotTeamPlugin pl, ProtocolManager po) {
		plugin = pl;
		teams = t;
		protocolManager = po;
		System.out.println(po);
		/*
		 * this was the reason 
		 * */
		if(po == null) {
			throw new IllegalStateException("KSGLEKS;RHGLKJWERSHLKJSERWHGBLIWERTHBLEWRTBHULBB");
		}
	}
	
	public void setGlow(Player player) {
		// doSomething
		System.out.println(protocolManager);
		PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.ENTITY_METADATA);
	    packet.getIntegers().write(0, player.getEntityId()); //Set packet's entity id
	    WrappedDataWatcher watcher = new WrappedDataWatcher(); //Create data watcher, the Entity Metadata packet requires this
	    Serializer serializer = Registry.get(Byte.class); //Found this through google, needed for some stupid reason
	    watcher.setEntity(player); //Set the new data watcher's target
	    watcher.setObject(0, serializer, (byte) (0x40)); //Set status to glowing, found on protocol page
	    packet.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects()); //Make the packet's datawatcher the one we created
	    try {
	    	protocolManager.sendServerPacket(player, packet);
	    } catch (InvocationTargetException e) {
	        e.printStackTrace();
	    }
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
			sender.sendMessage(String.format("%s%s %shas been drafted into: Team%s %s %swith%s %s %sother people.", ChatColor.GOLD,player.getDisplayName(), ChatColor.RESET, ChatColor.GREEN, team.name, ChatColor.RESET, ChatColor.GREEN, String.valueOf(team.playerList.size()), ChatColor.RESET));
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
	public Team getTeamById(String id) {
		
		try {
			return teams.findTeamByID(id);
		}catch(IllegalStateException e) {
			return null;
		}
		// TODO Auto-generated method stub
		
	}
}

