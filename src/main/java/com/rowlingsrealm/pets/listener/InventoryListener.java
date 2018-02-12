package com.rowlingsrealm.pets.listener;

import com.rowlingsrealm.pets.Message;
import com.rowlingsrealm.pets.PetsPlugin;
import com.rowlingsrealm.pets.pet.EntityArmorStandCustom;
import com.rowlingsrealm.pets.pet.Pet;
import com.rowlingsrealm.pets.pet.PetManager;
import net.minecraft.server.v1_12_R1.Entity;
import net.minecraft.server.v1_12_R1.EnumItemSlot;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityEquipment;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

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

    @EventHandler
    public void onInventoryCreativeClick(final InventoryCreativeEvent event) {
        Bukkit.getScheduler().runTaskLater(plugin, new BukkitRunnable() {
            @Override
            public void run() {
                for (ItemStack item :
                        event.getWhoClicked().getInventory()) {
                    if (item == null || item.getItemMeta().getDisplayName() == null) continue;

                    Pet pet = plugin.getPetManager().getPet(ChatColor.stripColor(item.getItemMeta().getDisplayName()));

                    if (pet != null) event.getWhoClicked().getInventory().remove(item);
                }
            }
        }, 1);
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

    private void checkOffhand(Player player) {
        PetManager pm = plugin.getPetManager();

        if (player.getInventory().getItemInOffHand() != null) {
            ItemStack off = player.getInventory().getItemInOffHand();

            if (off.getItemMeta() != null && off.getItemMeta().getDisplayName() != null) {
                Pet p = pm.getPet(ChatColor.stripColor(off.getItemMeta().getDisplayName()));

                if (p != null) {
                    player.getInventory().setItemInOffHand(null);
                }
            }
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
                    player.sendMessage(Message.DONT_OWN_PET.get());

                    return;
                }

                if (pet.isShoulder()) {
                    PetManager pm = plugin.getPetManager();

                    if (pm.getFollowing().containsKey(player.getUniqueId())) {
                        Entity entity = pm.getFollowing().get(player.getUniqueId());

                        entity.killEntity();

                        pm.getFollowing().remove(player.getUniqueId());
                    }

                    checkOffhand(player);

                    ItemStack itemStack = pet.getIdleModel(); {
                        ItemMeta meta = itemStack.getItemMeta();

                        meta.setLore(Arrays.asList(
                                "",
                                "§7Click to remove"
                        ));

                        itemStack.setItemMeta(meta);
                    }

                    // TODO do item stuff if theres already something in their offhand

                    player.getInventory().setItemInOffHand(itemStack);
                    player.closeInventory();

                    player.sendMessage(Message.SHOULDER_EQUIPPED.get("pet", pet.getName()));
                } else {
                    PetManager pm = plugin.getPetManager();

                    if (pm.getFollowing().containsKey(player.getUniqueId())) {
                        Entity entity = pm.getFollowing().get(player.getUniqueId());

                        entity.killEntity();

                        pm.getFollowing().remove(player.getUniqueId());
                    }

                    checkOffhand(player);

                    EntityArmorStandCustom custom = EntityArmorStandCustom.SPAWN(player, pet);

                    custom.setInvisible(true);

                    PacketPlayOutEntityEquipment packet = new PacketPlayOutEntityEquipment(custom.getId(), EnumItemSlot.HEAD, CraftItemStack.asNMSCopy(pet.getIdleModel()));

                    for (Player op :
                            Bukkit.getOnlinePlayers()) {
                        ((CraftPlayer) op).getHandle().playerConnection.sendPacket(packet);
                    }

                    pm.getFollowing().put(player.getUniqueId(), custom);

                    player.sendMessage(Message.FOLLOWING_EQUIPPED.get("pet", pet.getName()));
                    player.closeInventory();
                }
            }
        }
    }
}
