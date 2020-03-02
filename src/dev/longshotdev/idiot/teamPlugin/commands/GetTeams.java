package dev.longshotdev.idiot.teamPlugin.commands;


import java.util.ArrayList;
import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


import dev.longshotdev.idiot.teamPlugin.IdiotTeamPlugin;
import dev.longshotdev.idiot.teamPlugin.core.Team;
import dev.longshotdev.idiot.teamPlugin.core.TeamManager;


public class GetTeams implements CommandExecutor {
	
	private final IdiotTeamPlugin plugin;
	private TeamManager teamManager;
	public GetTeams(IdiotTeamPlugin pl, TeamManager teamManagerR) {
		teamManager = teamManagerR;
		plugin = pl;
		
	}
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
       
    	if(sender instanceof Player) {
    		Player player = (Player) sender;
    		
    		ArrayList<Team> teamList = teamManager.getTeams();
    		String sFact = String.format("Teams [%s]:§6 ", teamList.size());
    		
    		String seperated = teamList.stream().map(i -> i.name.toString()).collect(Collectors.joining("§6 ","",""));
    		player.sendMessage(sFact.concat(seperated.toString()));
    	}
        // If the player (or console) uses our command correct, we can return true
        return true;
    }
}
