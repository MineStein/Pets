package com.rowlingsrealm.pets.listener;

import com.rowlingsrealm.pets.Message;
import com.rowlingsrealm.pets.PetsPlugin;
import com.rowlingsrealm.pets.pet.Pet;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

/**
 * Copyright Tyler Grissom 2018
 */
public class InventoryListener implements Listener {

    private PetsPlugin plugin;

    public PetsPlugin getPlugin() {
        return plugin;
    }

    public InventoryListener(PetsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSwap(final PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();

        if (plugin.getPetManager().isPetIdleFrame(event.getOffHandItem())) {
            event.setCancelled(true);

            player.sendMessage(Message.NO_SWAP.get());
        }
    }

    // TODO items can be glitched out by using number key or shift click. Make sure they don't have other stuff in their offhand and if so do appropriate moving around
    @EventHandler
    public void onOffhandClick(final InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        PlayerInventory pi = player.getInventory();

        final int offhandSlot = 40;

        if (!player.getInventory().equals(pi)) return;
        if (event.getSlot() != offhandSlot) return;

        ItemStack item = event.getCurrentItem();

        if (plugin.getPetManager().isPetIdleFrame(item)) {
            event.setCancelled(true);

            if (event.isShiftClick()) return;

            Pet pet = plugin.getPetManager().getPet(ChatColor.stripColor(item.getItemMeta().getDisplayName()));

            pi.setItem(offhandSlot, null);

            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1F, 1F);
            player.sendMessage(Message.DEQUIPPED_PET.get("pet", pet.getName()));
        }
    }

    @EventHandler
    public void onClick(final InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();

        if (event.getInventory().getName().equals("Pets")) {
            event.setCancelled(true);

            if (item.getType().equals(Material.DIAMOND_PICKAXE)) {
                Pet pet = plugin.getPetManager().getPet(ChatColor.stripColor(item.getItemMeta().getDisplayName()));

                if (pet == null) return;

                if (!player.hasPermission(pet.getPermission())) {
                    player.sendMessage("§cYou don't own this pet! Purchase it at §4§lhttp://rowlingsrealm.com/store");

                    return;
                }

                if (pet.isShoulder()) {
                    ItemStack itemStack = pet.getIdleModel(); {
                        ItemMeta meta = itemStack.getItemMeta();

                        meta.setLore(Arrays.asList(
                                "",
                                "§7Click to remove"
                        ));

                        itemStack.setItemMeta(meta);
                    }

                    player.getInventory().setItemInOffHand(itemStack);
                    player.closeInventory();

                    player.sendMessage(Message.SHOULDER_EQUIPPED.get("pet", pet.getName()));
                }
            }
        }
    }
}
