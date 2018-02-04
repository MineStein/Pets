package com.rowlingsrealm.pets;

import javafx.util.Pair;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Map;

/**
 * Copyright Tyler Grissom 2018
 */
public enum Message {

    SHOULDER_EQUIPPED,
    NO_SWAP,
    DEQUIPPED_PET
    ;

    public String get() {
        FileConfiguration config = PetsPlugin.instance.getConfig();

        if (config.get("messages." + this.toString().toLowerCase()) == null) throw new IllegalArgumentException(String.format("No message entry at 'messages.%s'", this.toString().toLowerCase()));

        String get = config.getString("messages." + this.toString().toLowerCase());

        return ChatColor.translateAlternateColorCodes('&', get);
    }

    public String get(String key, String value) {
        return get().replace("$" + key, value);
    }

    public String get(Pair<String, String> pair) {
        return get().replace("$" + pair.getKey(), pair.getValue());
    }

    public String get(Map<String, String> map) {
        String get = get();

        for (Map.Entry<String, String> entry :
                map.entrySet()) {
            get = get.replace("$" + entry.getKey(), entry.getValue());
        }

        return get;
    }
}
