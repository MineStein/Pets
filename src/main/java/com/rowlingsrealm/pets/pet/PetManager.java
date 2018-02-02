package com.rowlingsrealm.pets.pet;

import com.rowlingsrealm.pets.PetsPlugin;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright Tyler Grissom 2018
 */
public class PetManager {

    private PetsPlugin plugin;
    private List<Pet> loadedPets;

    public PetsPlugin getPlugin() {
        return plugin;
    }

    public PetManager(PetsPlugin plugin) {
        this.plugin = plugin;
    }

    public List<Pet> getLoadedPets() {
        return loadedPets;
    }

    public void loadPets() {
        if (loadedPets != null && loadedPets.size() > 0) loadedPets.clear();

        FileConfiguration config = plugin.getConfig();

        if (config.get("pets") instanceof List<?>) {
            plugin.getLogger().warning("Incorrect pets type.");

            return;
        }

        List<Pet> pets = (ArrayList<Pet>) config.get("pets");

        if (pets == null || pets.size() == 0) {
            plugin.getLogger().warning("No pets configured!");

            return;
        }

        List<String> names = new ArrayList<>();

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

        plugin.getLogger().info(String.format("Loaded %b pets.", loadedPets.size()));
    }

    public Pet getPet(String name) {
        for (Pet pet : loadedPets) {
            if (pet.getName().equals(name)) return pet;
        }

        return null;
    }
}