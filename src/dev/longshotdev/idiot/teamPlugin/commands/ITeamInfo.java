package dev.longshotdev.idiot.teamPlugin.commands;

import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import dev.longshotdev.idiot.teamPlugin.IdiotTeamPlugin;
import dev.longshotdev.idiot.teamPlugin.core.Team;
import dev.longshotdev.idiot.teamPlugin.core.TeamConfigManager;
import dev.longshotdev.idiot.teamPlugin.core.TeamManager;

public class ITeamInfo implements CommandExecutor {

	private final IdiotTeamPlugin plugin;
	private final TeamManager teamManager;
	
	public ITeamInfo(IdiotTeamPlugin pl, TeamManager tm, TeamConfigManager cfg) {
		plugin = pl;
		teamManager = tm;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		/*
		 * Takes in 1 argument { team_id }
		 * */
		// argument checking
		System.out.println("DOING SOMETHING ");
		if(args.length <= 1) {
			sender.sendMessage(ChatColor.RED + "Didn't recieve ID, did you forget to supply it?");
			return true;
		}
		// main
		Team team = teamManager.getTeamById(args[0]);
		if(team == null)
			sender.sendMessage(ChatColor.RED + "Could not find team, maybe you typed in the ID wrong?");
		
		sender.sendMessage(String.format("%sTeam:%s %s", ChatColor.YELLOW, ChatColor.GREEN, team.name));
		sender.sendMessage(String.format("%sTeam ID:%s %s", ChatColor.YELLOW, ChatColor.GREEN, team.id));
		sender.sendMessage(String.format("%sPlayer Count:%s %s", ChatColor.YELLOW, ChatColor.GREEN, team.playerList.size()));
		sender.sendMessage(String.format("%sPlayers:%s %s", ChatColor.YELLOW, ChatColor.AQUA,team.playerList.stream().map(i -> Bukkit.getPlayer(i).getName()).collect(Collectors.joining(" ","","")).toString()));
		return true;
	}
}
