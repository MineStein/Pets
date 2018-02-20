package com.rowlingsrealm.pets.task;

import com.rowlingsrealm.pets.PetsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Copyright Tyler Grissom 2018
 */
public class InventoryCheckTask extends BukkitRunnable {

    private PetsPlugin plugin;

    public PetsPlugin getPlugin() {
        return plugin;
    }

    public InventoryCheckTask(PetsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (Player player :
                Bukkit.getOnlinePlayers()) {
            for (ItemStack item : player.getInventory().getContents()) {
                if (item == null || item.getType().equals(Material.AIR) || item.getData() == null || item.getData().getData() == 0) return;

                if (plugin.getPetManager().getPetFromFrame(item.getData().getData()) != null) player.getInventory().remove(item);
            }
        }
    }
}
