package com.rowlingsrealm.pets;

import com.rowlingsrealm.pets.command.PetsCommand;
import com.rowlingsrealm.pets.command.PetsTabCompleter;
import com.rowlingsrealm.pets.pet.Pet;
import com.rowlingsrealm.pets.pet.PetManager;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Copyright Tyler Grissom 2018
 */
public class PetsPlugin extends JavaPlugin {

    private PetsPlugin plugin;
    private PetManager petManager;

    public PetsPlugin getPlugin() {
        return plugin;
    }

    public PetManager getPetManager() {
        return petManager;
    }

    public void reload() throws Exception {
        try {
            reloadConfig();

            if (petManager != null) petManager.loadPets();
        } catch (Exception exception) {
            throw new Exception(exception);
        }
    }

    private void registerCommand(String pets, CommandExecutor exec, TabCompleter tab) {
        getCommand(pets).setExecutor(exec);
        getCommand(pets).setTabCompleter(tab);
    }

    @Override
    public void onEnable() {
        plugin = this;

        getConfig().options().copyDefaults(true);
        saveConfig();

        ConfigurationSerialization.registerClass(Pet.class);

        petManager = new PetManager(this);

        petManager.loadPets();

        {
            registerCommand(
                    "pets",
                    new PetsCommand(this),
                    new PetsTabCompleter()
            );
        }
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }
}
