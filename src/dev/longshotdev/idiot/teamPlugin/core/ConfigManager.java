package dev.longshotdev.idiot.teamPlugin.core;


import dev.longshotdev.idiot.teamPlugin.IdiotTeamPlugin;
public class ConfigManager {
	
	private final IdiotTeamPlugin plugin;
	
	public ConfigManager(IdiotTeamPlugin pl) {
		plugin = pl;
	}
	public void loadConfig() {
		plugin.getConfig().addDefault("tpa-cooldown", 5);
		plugin.getConfig().addDefault("keep-alive", 30);
		plugin.getConfig().addDefault("tm", 30);
		plugin.getConfig().addDefault("team-PVP", true);
		plugin.getConfig().addDefault("team-Glow", true);
		plugin.getConfig().options().copyDefaults(true);
		plugin.saveConfig();
	}
}
