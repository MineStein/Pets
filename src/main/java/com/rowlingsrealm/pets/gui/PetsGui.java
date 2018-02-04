package com.rowlingsrealm.pets.gui;

import com.rowlingsrealm.pets.PetsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Copyright Tyler Grissom 2018
 */
public class PetsGui extends Gui {

    public PetsGui(PetsPlugin plugin) {
        super(plugin);
    }

    @Override
    Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(null, 27, "Pets");

        ItemStack close = new ItemStack(Material.BARRIER); {
            ItemMeta meta = close.getItemMeta();

            meta.setDisplayName("§cClose");

            close.setItemMeta(meta);
        }

        inventory.setItem(0, close);

        return inventory;
    }
}
