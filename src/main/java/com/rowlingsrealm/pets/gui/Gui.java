package com.rowlingsrealm.pets.gui;

import com.rowlingsrealm.pets.PetsPlugin;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * Copyright Tyler Grissom 2018
 */
public abstract class Gui {

    private PetsPlugin plugin;

    public PetsPlugin getPlugin() {
        return plugin;
    }

    public Gui(PetsPlugin plugin) {
        this.plugin = plugin;
    }

    abstract Inventory getInventory();

    public void open(Player... players) {
        for (Player player :
                players) {
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1F, 1F);
            player.openInventory(getInventory());
        }
    }
}
