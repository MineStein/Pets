package com.rowlingsrealm.pets.gui;

import com.rowlingsrealm.pets.PetsPlugin;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * Copyright Tyler Grissom 2018
 */
public abstract class PlayerGui extends Gui {

    private Player player;

    public Player getPlayer() {
        return player;
    }

    public PlayerGui(PetsPlugin plugin, Player player) {
        super(plugin);

        this.player = player;
    }

    abstract Inventory getInventory(Player player);

    @Override
    Inventory getInventory() {
        return getInventory(player);
    }
}
