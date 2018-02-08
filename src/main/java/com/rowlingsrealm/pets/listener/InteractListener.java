package com.rowlingsrealm.pets.listener;

import com.rowlingsrealm.pets.PetsPlugin;
import com.rowlingsrealm.pets.pet.Pet;
import com.rowlingsrealm.pets.pet.PetManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Copyright Tyler Grissom 2018
 */
public class InteractListener implements Listener {

    private PetsPlugin plugin;

    public PetsPlugin getPlugin() {
        return plugin;
    }

    public InteractListener(PetsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteractArmorStand(final PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();

        if (event.getRightClicked().getType().equals(EntityType.ARMOR_STAND)) {
            final ArmorStand as = (ArmorStand) event.getRightClicked();
            PetManager petManager = plugin.getPetManager();

            if (petManager.getFollowing().containsKey(player.getUniqueId())) {
                ItemStack model = as.getItemInHand();
                final Pet pet = petManager.getPetFromFrame(model.getDurability());

                System.out.println(pet);

                as.setItemInHand(pet.getInteractModel());

                Bukkit.getScheduler().runTaskLater(plugin, new BukkitRunnable() {
                    @Override
                    public void run() {
                        as.setItemInHand(pet.getIdleModel());
                    }
                }, 40);
            }
        }
    }
}
