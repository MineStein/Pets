package com.rowlingsrealm.pets.listener;

import com.rowlingsrealm.pets.PetsPlugin;
import com.rowlingsrealm.pets.pet.Pet;
import com.rowlingsrealm.pets.pet.PetManager;
import net.minecraft.server.v1_12_R1.EnumItemSlot;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityEquipment;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
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
    public void onInteractArmorStand(final PlayerInteractAtEntityEvent event) {
        final Player player = event.getPlayer();

        if (event.getRightClicked().getType().equals(EntityType.ARMOR_STAND)) {
            final ArmorStand as = (ArmorStand) event.getRightClicked();
            PetManager petManager = plugin.getPetManager();

            if (petManager.getFollowing().containsKey(player.getUniqueId())) {
                ItemStack model = as.getItemInHand();
                final Pet pet = petManager.getPetFromFrame(model.getDurability());

                if (pet.getInteractFrame() == 0 || pet.getIdleFrame() == pet.getInteractFrame()) return;

                System.out.println(pet);

                PacketPlayOutEntityEquipment packet = new PacketPlayOutEntityEquipment(as.getEntityId(), EnumItemSlot.HEAD, CraftItemStack.asNMSCopy(pet.getInteractModel()));

                for (Player op :
                        player.getWorld().getPlayers()) {
                    ((CraftPlayer) op).getHandle().playerConnection.sendPacket(packet);
                }

                Bukkit.getScheduler().runTaskLater(plugin, new BukkitRunnable() {
                    @Override
                    public void run() {
                        PacketPlayOutEntityEquipment packet1 = new PacketPlayOutEntityEquipment(as.getEntityId(), EnumItemSlot.HEAD, CraftItemStack.asNMSCopy(pet.getIdleModel(player)));

                        for (Player op :
                                Bukkit.getOnlinePlayers()) {
                            ((CraftPlayer) op).getHandle().playerConnection.sendPacket(packet1);
                        }
                    }
                }, 40);
            }
        }
    }
}
