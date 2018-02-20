package com.rowlingsrealm.pets.command;

import com.rowlingsrealm.pets.Message;
import com.rowlingsrealm.pets.PetsPlugin;
import com.rowlingsrealm.pets.gui.PetsGui;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
        if (args.length == 0) {
            if (sender instanceof Player) {
                Player player = (Player) sender;

                new PetsGui(plugin, player).open(player);
            } else {
                sender.sendMessage(Message.ONLY_PLAYERS.get());
            }
        } else {
            if (args[0].equalsIgnoreCase("reload") && sender.hasPermission("rowlingsrealm.pets.reload")) {
                try {
                    plugin.reload();
                } catch (Exception e) {
                    sender.sendMessage("§cAn error occurred while reloading Pets. The error has been logged to console.");

                    e.printStackTrace();
                }

                sender.sendMessage("§aReloaded Pets.");
            } else {
                sender.sendMessage(Message.NO_PERMISSION.get());
            }
        }
    }
}
