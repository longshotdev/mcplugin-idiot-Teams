package dev.longshotdev.idiot.teamPlugin.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class CreateTeamsTabCompleter implements TabCompleter {
	public CreateTeamsTabCompleter() {
	}
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        List<String> commands = new ArrayList<>();

        Collections.sort(completions);
        return completions;
	}
}
