package com.rowlingsrealm.pets.pet;

import com.rowlingsrealm.pets.PetsPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * Copyright Tyler Grissom 2018
 */
public class PetManager {

    private PetsPlugin plugin;
    private List<Pet> loadedPets;
    private Map<UUID, EntityArmorStandCustom> following;

    public PetsPlugin getPlugin() {
        return plugin;
    }

    public PetManager(PetsPlugin plugin) {
        this.plugin = plugin;
        this.following = new HashMap<>();
    }

    public List<Pet> getLoadedPets() {
        return loadedPets;
    }

    public Map<UUID, EntityArmorStandCustom> getFollowing() {
        return following;
    }

    public void loadPets() {
        if (loadedPets != null && loadedPets.size() > 0) loadedPets.clear();

        FileConfiguration config = plugin.getConfig();

        List<Pet> pets = new ArrayList<>();
        List<Map<?, ?>> maps = config.getMapList("pets");

        for (Map<?, ?> listItem :
             maps) {
            pets.add(new Pet((Map<String, Object>) listItem));
        }

        if (pets.size() == 0) {
            plugin.getLogger().warning("No pets configured!");

            return;
        }

        List<String> names = new ArrayList<>();


        // TODO implement CopyOnWriteArrayList as suggested by Spigot user as loop in loop is poor practice
        // TODO check for multiple frame registrations
        // Check for multiple registrations of the same pet name to avoid conflicts with Pet#getPet
        for (Pet pet : pets) {
            if (names.contains(pet.getName())) {
                plugin.getLogger().warning(String.format("Multiple-registration for pet name '%s', disabling all...", pet.getName()));

                for (Pet innerPet :
                        pets) {
                    if (innerPet.getName().equals(pet.getName())) pets.remove(innerPet);
                }

                return;
            }

            names.add(pet.getName());
        }

        loadedPets = pets;

        plugin.getLogger().info(String.format("Loaded %s pets.", String.valueOf(loadedPets.size())));
    }

    public Pet getPet(String name) {
        for (Pet pet : loadedPets) {
            if (pet.getName().equals(name)) return pet;
        }

        return null;
    }

    public boolean hasPet(Player player, Pet pet) {
        if (pet.getPermission() == null) return true;

        return player.hasPermission(pet.getPermission());
    }

    public boolean isPetIdleFrame(ItemStack itemStack) {
        for (Pet pet :
                getLoadedPets()) {
            if (itemStack == null || itemStack.getType().equals(Material.AIR) || itemStack.getData() == null || itemStack.getData().getData() == 0) continue;

            if (pet.getName().equals(ChatColor.stripColor(itemStack.getItemMeta().getDisplayName()))) return true;
        }

        return false;
    }

    public Pet getPetFromFrame(short frame) {
        for (Pet pet :
                getLoadedPets()) {
            if (pet.getIdleFrame() == (int) frame || pet.getInteractFrame() == (int) frame || pet.getWalkFrames().contains((int) frame)) return pet;
        }

        return null;
    }
}
