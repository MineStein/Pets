package com.rowlingsrealm.pets.gui;

import com.rowlingsrealm.pets.PetsPlugin;
import com.rowlingsrealm.pets.pet.Pet;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * Copyright Tyler Grissom 2018
 */
public class PetsGui extends PlayerGui {

    public PetsGui(PetsPlugin plugin, Player player) {
        super(plugin, player);
    }

    @Override
    Inventory getInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 27, "Pets");

        for (Pet pet :
                getPlugin().getPetManager().getLoadedPets()) {
            inventory.addItem(pet.getIdleModel(player));
        }

        return inventory;
    }
}
