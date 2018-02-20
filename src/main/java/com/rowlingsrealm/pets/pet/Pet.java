package com.rowlingsrealm.pets.pet;

import com.rowlingsrealm.pets.PetsPlugin;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.permissions.Permission;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright Tyler Grissom 2018
 */
public class Pet implements ConfigurationSerializable {

    private String name, description;
    private Permission permission;
    private int idleFrame, interactFrame;
    private boolean rideable, shoulder, enabled;
    private List<Integer> walkFrames;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Permission getPermission() {
        return permission;
    }

    public int getIdleFrame() {
        return idleFrame;
    }

    public int getInteractFrame() {
        return interactFrame;
    }

    public boolean isRideable() {
        return rideable;
    }

    public boolean isShoulder() {
        return shoulder;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public List<Integer> getWalkFrames() {
        return walkFrames;
    }

    public Pet(String name, String description, String permission, int idleFrame, int interactFrame, boolean rideable, boolean shoulder, boolean enabled, List<Integer> walkFrames) {
        this.name = name;
        this.description = description;
        this.permission = new Permission(permission);
        this.idleFrame = idleFrame;
        this.interactFrame = interactFrame;
        this.rideable = rideable;
        this.shoulder = shoulder;
        this.enabled = enabled;
        this.walkFrames = walkFrames;
    }

    public Pet(Map<String, Object> map) {
        this.name = (String) map.get("name");
        this.description = (String) map.get("description");
        this.permission = new Permission((String) map.get("permission"));
        if (map.get("idle") != null) this.idleFrame = (int) map.get("idle");
        if (map.get("interact") != null) this.interactFrame = (int) map.get("interact");
        this.rideable = map.get("rideable") != null && (boolean) map.get("rideable");
        this.shoulder = map.get("shoulder") != null && (boolean) map.get("shoulder");

        if (map.get("enabled") == null) this.enabled = true;
        else this.enabled = (boolean) map.get("enabled");

        this.walkFrames = (List<Integer>) map.get("walk");
    }

    public static Pet deserialize(Map<String, Object> map) {
        return new Pet(map);
    }

    public static Pet valueOf(Map<String, Object> map) {
        return new Pet(map);
    }

    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();

        map.put("name", name);
        map.put("description", description);
        map.put("permission", permission.toString());
        map.put("idle", idleFrame);
        map.put("interact", interactFrame);
        map.put("rideable", rideable);
        map.put("shoulder", shoulder);
        map.put("enabled", enabled);
        map.put("walk", walkFrames);

        return map;
    }

    private ItemStack getModel(short frame) {
        ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE, 1, frame); {
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName("§3" + getName());
            meta.setUnbreakable(true);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);

            item.setItemMeta(meta);
        }

        return item;
    }

    public ItemStack getIdleModel(Player player) {
        ItemStack i = getModel((short) getIdleFrame()); {
            ItemMeta meta = i.getItemMeta();

            String owned = PetsPlugin.instance.getPetManager().hasPet(player, this) ? "§aYes" : "§cNo";
            String rideable = isRideable() ? "§aYes" : "§cNo";
            String type = isShoulder() ? "§aShoulder" : "§aFollowing";

            if (!isEnabled()) {
                meta.setLore(Arrays.asList("", "§7" + getDescription(), "", "§c§lComing soon!"));
            } else {
                meta.setLore(Arrays.asList("", "§7" + getDescription(), "", "§3Owned§8: " + owned, "§3Rideable§8: " + rideable, "§3Type§8: " + type));
            }

            i.setItemMeta(meta);
        }

        return i;
    }

    public ItemStack getInteractModel() {
        return getModel((short) getInteractFrame());
    }
}
