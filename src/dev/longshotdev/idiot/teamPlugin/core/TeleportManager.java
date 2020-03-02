package dev.longshotdev.idiot.teamPlugin.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import org.bukkit.entity.Player;

public class TeleportManager {
	
	Map<String, Long> tpaCooldown = new HashMap<String, Long>();
	Map<String, String> currentRequest = new HashMap<String, String>();
	public void teleport(Player player, Player destination, Logger logger) {
		
	}
}
