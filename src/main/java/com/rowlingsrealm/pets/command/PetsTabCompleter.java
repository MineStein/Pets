package com.rowlingsrealm.pets.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright Tyler Grissom 2018
 */
public class PetsTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> results = new ArrayList<>();

        if (args.length == 0) {
            results.add("reload");
        }

        return results;
    }
}
