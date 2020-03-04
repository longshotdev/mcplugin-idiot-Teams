package dev.longshotdev.idiot.teamPlugin;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedDataWatcher.Registry;
import com.comphenix.protocol.wrappers.WrappedDataWatcher.Serializer;

import dev.longshotdev.idiot.teamPlugin.commands.*;
import dev.longshotdev.idiot.teamPlugin.core.ConfigManager;
import dev.longshotdev.idiot.teamPlugin.core.TeamList;
import dev.longshotdev.idiot.teamPlugin.core.TeamManager;
import dev.longshotdev.idiot.teamPlugin.core.TeleportManager;

public class IdiotTeamPlugin extends JavaPlugin {
	
	/*
	 * this might look like shit code but its a HACK:
	 * 
	 * so what this looked like was this
	 * 
	 * private final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
	 * private TeamList teams = new TeamList();
	 * private TeamManager teamManager = new TeamManager(teams, this, protocolManager);
	 * private TeleportManager teleportManager = new TeleportManager(this);
	 * private ConfigManager cfgManager = new ConfigManager(this);
	 * 
	 * 
	 * now it looks like how it is rn
	 * reason why is becausej ava is shit and it wont initalize the stuff in time
	 * so ye
	 * */
	private ProtocolManager protocolManager;
	private TeamList teams;
	private TeamManager teamManager;
	private TeleportManager teleportManager;
	private ConfigManager cfgManager;
	

	/*
	 * On Enable Event
	 * */
	@Override
	public void onEnable() {
		protocolManager = ProtocolLibrary.getProtocolManager();
		teams = new TeamList();
		teamManager = new TeamManager(teams, this, protocolManager);
		teleportManager = new TeleportManager(this);
		cfgManager = new ConfigManager(this);
		cfgManager.loadConfig();
		// Register command
		this.getCommand("aa").setExecutor(new CommandTest());
		this.getCommand("createteams").setExecutor(new CreateTeams(teams));
		this.getCommand("getteams").setExecutor(new GetTeams(this, teamManager));
		this.getCommand("addplayertoteam").setExecutor(new AddPlayerToTeam(this, teamManager));
		this.getCommand("addplayertoteam").setTabCompleter(new AddPlayerToTeamTabCompleter(this, teamManager));
		this.getCommand("tpa").setExecutor(new ITeleport(this, teamManager, null, teleportManager));
		this.getCommand("tpa").setTabCompleter(new ITeleportTabCompleter(this, teamManager));
		this.getCommand("tpaccept").setExecutor(new ITeleportAccept(this, teamManager, teleportManager));
		this.getCommand("tinfo").setExecutor(new ITeamInfo(this, teamManager, null));
		this.getCommand("tinfo").setTabCompleter(new ITeamInfoTabCompleter(this, teamManager));
		// TPA
		// ProtoCol
		PacketType packetToListen = PacketType.Play.Client.POSITION;
		protocolManager.addPacketListener(new PacketAdapter(this, ListenerPriority.HIGHEST, packetToListen) {
			 @Override
			    public void onPacketReceiving(PacketEvent event) {
			        Player player = event.getPlayer();
			        PacketContainer packet = event.getPacket();
			        teamManager.updateGlow1(player);
			    }
		});
	}	
	/*
	 * On Disable Event
	 * */
	
	@Override
	public void onDisable() {
		
	}
}
