package dev.longshotdev.idiot.teamPlugin.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

import dev.longshotdev.idiot.teamPlugin.IdiotTeamPlugin;

public class TeleportManager {
	
	private IdiotTeamPlugin plugin;
	
	
	public Map<UUID, Long> tpaCooldown = new HashMap<UUID, Long>();
	public Map<String, String> currentRequest = new HashMap<String, String>();
	
	public TeleportManager(IdiotTeamPlugin pl) {
		plugin = pl;
	}
	
	public void addPlayerToCooldown(Player player) {
		tpaCooldown.put(player.getUniqueId(), System.currentTimeMillis());
	}
	
	
	
	
	
	public void sendRequest(Player sender, Player recipient) {
		sender.sendMessage("Sending a teleport request to " + recipient.getName() + ".");
		
		String sendtpaccept = "";
		String sendtpdeny = "";
		
		if (recipient.hasPermission("itp.tpaccept")) {
			sendtpaccept = " To accept the teleport request, type " +  ChatColor.RED + "/tpaccept" + ChatColor.RESET + ".";
		} else {
			sendtpaccept = "";
		}
		
		if (recipient.hasPermission("itp.tpdeny")) {
			sendtpdeny = " To deny the teleport request, type " + ChatColor.RED + "/tpdeny" + ChatColor.RESET + ".";
		} else {
			sendtpdeny = "";
		}
		
		recipient.sendMessage(ChatColor.RED + sender.getName() + ChatColor.RESET + " has sent a request to teleport to you." + sendtpaccept + sendtpdeny);
		currentRequest.put(recipient.getName(), sender.getName());
	}	
	public boolean killRequest(String key) {
		if (currentRequest.containsKey(key)) {
			Player loser = plugin.getServer().getPlayer(currentRequest.get(key));
			if (!(loser == null)) {
				loser.sendMessage(ChatColor.RED + "Your teleport request timed out.");
			}
			
			currentRequest.remove(key);
			
			return true;
		} else {
			return false;
		}
	}
	public boolean checkCoolDown(Player sender) {
		int cooldown = plugin.getConfig().getInt("tpa-cooldown");
		
		long diff = (System.currentTimeMillis() - tpaCooldown.get(sender.getUniqueId())) / 1000;
		
		if(diff < cooldown) {
			return false;
		}
		
		return true;
	}
}
