package com.rowlingsrealm.pets.task;

import com.rowlingsrealm.pets.PetsPlugin;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.UUID;

/**
 * Copyright Tyler Grissom 2018
 */
public class CycleFramesTask extends BukkitRunnable {

    private PetsPlugin plugin;
    private Map<UUID, Integer> step;

    public PetsPlugin getPlugin() {
        return plugin;
    }

    public CycleFramesTask(PetsPlugin plugin) {
        this.plugin = plugin;
    }

    private void step(Player player) {

    }

    @Override
    public void run() {

    }
}
