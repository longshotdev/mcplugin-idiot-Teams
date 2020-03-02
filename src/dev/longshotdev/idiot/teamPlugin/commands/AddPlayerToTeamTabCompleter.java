package dev.longshotdev.idiot.teamPlugin.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import dev.longshotdev.idiot.teamPlugin.IdiotTeamPlugin;
import dev.longshotdev.idiot.teamPlugin.core.TeamManager;

public class AddPlayerToTeamTabCompleter implements TabCompleter {
	
	private final IdiotTeamPlugin plugin;
	private TeamManager teamManager;
	public AddPlayerToTeamTabCompleter(IdiotTeamPlugin pl, TeamManager tm) {
		plugin = pl;
		teamManager = tm;
	}
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        List<String> commands = new ArrayList<>();
    
        
        if (args.length == 1) {
             for(Player player : plugin.getServer().getOnlinePlayers()) {
            	 commands.add(player.getName());
             }
            StringUtil.copyPartialMatches(args[0], commands, completions);
        } else if (args.length == 2) {
                    for(String teamName : teamManager.getTeamNames()) {
                    	commands.add(teamName);
                    }
            StringUtil.copyPartialMatches(args[1], commands, completions);
        }
        Collections.sort(completions);
        return completions;
	}
	 
}
