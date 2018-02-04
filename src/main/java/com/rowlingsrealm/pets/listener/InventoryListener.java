package com.rowlingsrealm.pets.listener;

import com.rowlingsrealm.pets.PetsPlugin;
import com.rowlingsrealm.pets.item.ItemUtility;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Copyright Tyler Grissom 2018
 */
public class InventoryListener implements Listener {

    private PetsPlugin plugin;

    public PetsPlugin getPlugin() {
        return plugin;
    }

    public InventoryListener(PetsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(final InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();

        if (event.getInventory().getName().equals("Pets")) {
            event.setCancelled(true);

            if (ItemUtility.isSimilar(item, Material.BARRIER, "Close")) {
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1F, 1F);
                player.closeInventory();
            }
        }
    }
}
