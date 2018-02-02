package com.rowlingsrealm.pets.command;

import com.rowlingsrealm.pets.PetsPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Copyright Tyler Grissom 2018
 */
public class PetsCommand extends CommandBase {

    private PetsPlugin plugin;

    public PetsPlugin getPlugin() {
        return plugin;
    }

    public PetsCommand(PetsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    void execute(CommandSender sender, Command command, String[] args) {
        if (sender.hasPermission("rowlingsrealm.pets.reload")) {
            try {
                plugin.reload();
            } catch (Exception e) {
                sender.sendMessage("§cAn error occurred while reloading Pets. The error has been logged to console.");

                e.printStackTrace();
            }

            sender.sendMessage("§aReloaded Pets.");
        } else {
            sender.sendMessage("§cYou don't have permission!");
        }
    }
}
