package com.rowlingsrealm.pets.item;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Copyright Tyler Grissom 2018
 */
public class ItemUtility {

    public static boolean isSimilar(ItemStack compare, Material material, String name) {
        return (compare.getType().equals(material) && ChatColor.stripColor(compare.getItemMeta().getDisplayName()).equalsIgnoreCase(name));
    }
}
