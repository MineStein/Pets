package com.rowlingsrealm.pets.listener;

import com.rowlingsrealm.pets.PetsPlugin;
import com.rowlingsrealm.pets.pet.PetManager;
import net.minecraft.server.v1_12_R1.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Copyright Tyler Grissom 2018
 */
public class QuitListener implements Listener {

    private PetsPlugin plugin;

    public PetsPlugin getPlugin() {
        return plugin;
    }

    public QuitListener(PetsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PetManager petManager = plugin.getPetManager();

        if (petManager.getFollowing().containsKey(player.getUniqueId())) {
            Entity as = petManager.getFollowing().get(player.getUniqueId());

            as.killEntity();

            petManager.getFollowing().remove(player.getUniqueId());
        }
    }
}
