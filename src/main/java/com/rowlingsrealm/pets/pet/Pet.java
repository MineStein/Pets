package com.rowlingsrealm.pets.pet;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright Tyler Grissom 2018
 */
public class Pet implements ConfigurationSerializable {

    private String name;
    private int idleFrame, interactFrame;
    private boolean rideable;
    private List<Integer> walkFrames;

    public String getName() {
        return name;
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

    public List<Integer> getWalkFrames() {
        return walkFrames;
    }

    public Pet(String name, int idleFrame, int interactFrame, boolean rideable, List<Integer> walkFrames) {
        this.name = name;
        this.idleFrame = idleFrame;
        this.interactFrame = interactFrame;
        this.rideable = rideable;
        this.walkFrames = walkFrames;
    }

    public Pet(Map<String, Object> map) {
        this.name = (String) map.get("name");
        this.idleFrame = (Integer) map.get("idle");
        this.interactFrame = (Integer) map.get("interact");
        this.rideable = (Boolean) map.get("rideable");
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
        map.put("idle", idleFrame);
        map.put("interact", interactFrame);
        map.put("rideable", rideable);
        map.put("walk", walkFrames);

        return map;
    }
}
