package dev.longshotdev.idiot.teamPlugin.core;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import dev.longshotdev.idiot.teamPlugin.IdiotTeamPlugin;

public class UserDataHandler {
	private final IdiotTeamPlugin p;
	private UUID u;
	private File userFile;
	private FileConfiguration userConfig;

	public UserDataHandler(UUID u, IdiotTeamPlugin p) {
		this.p = p;
		this.u = u;

		userFile = new File(String.format("%s/playerConfigs/", p.getDataFolder()), u + ".yml");

		userConfig = YamlConfiguration.loadConfiguration(userFile);

	}

	public void createUser(final Player player) {

		if (!(userFile.exists())) {
			try {

				YamlConfiguration UserConfig = YamlConfiguration.loadConfiguration(userFile);
				UserConfig.set("User.Info.PreviousName", player.getName());
				UserConfig.set("User.Info.UniqueID", player.getUniqueId().toString());
				UserConfig.set("User.Info.ipAddress", player.getAddress().getAddress().getHostAddress());
				UserConfig.set("User.Info.Coords", false);
				UserConfig.set("User.Info.Glow", true);
				UserConfig.save(userFile);

			} catch (Exception e) {

				e.printStackTrace();

			}
		}
	}

	public FileConfiguration getUserFile() {

		return userConfig;

	}

	public void saveUserFile() {

		try {

			getUserFile().save(userFile);

		} catch (Exception e) {

			e.printStackTrace();

		}
	}
	public void changeCoords(final Player player) {
		YamlConfiguration UserConfig = YamlConfiguration.loadConfiguration(userFile);
		UserConfig.set("User.Info.Coords",!UserConfig.getBoolean("User.Info.Coords"));
		try {
			UserConfig.save(userFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean getCoords(final Player player) {
		YamlConfiguration UserConfig = YamlConfiguration.loadConfiguration(userFile);
		return UserConfig.getBoolean("User.Info.Coords");
	}
	public void changeGlow(final Player player) {
		YamlConfiguration UserConfig = YamlConfiguration.loadConfiguration(userFile);
		UserConfig.set("User.Info.Glow",!UserConfig.getBoolean("User.Info.Glow"));
		try {
			UserConfig.save(userFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean getGlow(final Player player) {
		YamlConfiguration UserConfig = YamlConfiguration.loadConfiguration(userFile);
		return UserConfig.getBoolean("User.Info.Glow");
	}
}