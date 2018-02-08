package com.rowlingsrealm.pets.pet;

import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class EntityArmorStandCustom extends EntityArmorStand {

    // Using an UUID to store the player. Storing a player instance is not
    // recommended
    public UUID player;

    public EntityArmorStandCustom(World world, Player p, Pet pet) {
        super(world);
        player = p.getUniqueId();

        setPosition(p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ());
    }

    // This is the update method of the entity
    @Override
    public void B_() {

        // Getting the nms class of the player to have easier access to some
        // data.
        EntityPlayer p = ((CraftPlayer) Bukkit.getPlayer(player)).getHandle();
        // Setting the position
        setPosition(p.locX, p.locY, p.locZ);
        //Setting the yaw and pitch of the armor stand. If it fails we get another change to set it with the codes below.
        this.yaw = p.yaw;
        this.pitch = p.pitch;
        // Setting the yaw of the armor stand. I don't know for sure if this
        // works.
        setBodyPose(new Vector3f(0.0F, p.yaw, 0.0F));
        // Setting the pitch of the head. I also don't know for sure if this
        // works.
        setHeadPose(new Vector3f(p.pitch, 0.0F, 0.0F));

        // If you want to still do the normal entity update instead of just
        // standing totally still. Use this line.
        // super.m();
    }

    // This method has to be called to spawn a new instance of the armor stand.
    public static EntityArmorStandCustom SPAWN(Player p, Pet pet) {
        // Getting the nms world to add the entity
        net.minecraft.server.v1_12_R1.World w = ((CraftWorld) p.getWorld())
                .getHandle();
        // Creating the new instance of the entity
        EntityArmorStandCustom armorStand = new EntityArmorStandCustom(w, p, pet);
        // Adding the entity to the world
        w.addEntity(armorStand, SpawnReason.CUSTOM);
        armorStand.setLocation(p.getLocation().getX(), p.getLocation().getY(),
                p.getLocation().getZ(), p.getLocation().getYaw(), p
                        .getLocation().getPitch());
        // Returning the armor stand instance. If you want to get the Bukkit api
        // for the entity you can use getEntity() method.
        return armorStand;
    }

    // This method has to be called on onEnable() in the main plugin class! This
    // has to be called to add the entity in the registry of types to prefent
    // crashing.
    public static void registerEntity() {
        try {
            Class<EntityTypes> entityTypeClass = EntityTypes.class;

            Field c = entityTypeClass.getDeclaredField("c");
            c.setAccessible(true);
            HashMap<String, Class<?>> c_map = (HashMap) c.get(null);
            c_map.put("customArmorStand", EntityArmorStandCustom.class);

            Field d = entityTypeClass.getDeclaredField("d");
            d.setAccessible(true);
            HashMap<Class<?>, String> d_map = (HashMap) d.get(null);
            d_map.put(EntityArmorStandCustom.class, "customArmorStand");

            Field e = entityTypeClass.getDeclaredField("e");
            e.setAccessible(true);
            HashMap<Integer, Class<?>> e_map = (HashMap) e.get(null);
            e_map.put(Integer.valueOf(63), EntityArmorStandCustom.class);

            Field f = entityTypeClass.getDeclaredField("f");
            f.setAccessible(true);
            HashMap<Class<?>, Integer> f_map = (HashMap) f.get(null);
            f_map.put(EntityArmorStandCustom.class, Integer.valueOf(63));

            Field g = entityTypeClass.getDeclaredField("g");
            g.setAccessible(true);
            HashMap<String, Integer> g_map = (HashMap) g.get(null);
            g_map.put("customArmorStand", Integer.valueOf(63));

        } catch (Exception exc) {
            Field d;
            int d_map;
            Method[] e;
            Class[] paramTypes = { Class.class, String.class, Integer.TYPE };
            try {
                Method method = EntityTypes.class.getDeclaredMethod(
                        "addMapping", paramTypes);
                method.setAccessible(true);
            } catch (Exception ex) {
                exc.addSuppressed(ex);
                try {
                    d_map = (e = EntityTypes.class.getDeclaredMethods()).length;
                    for (int d1 = 0; d1 < d_map; d1++) {
                        Method method = e[d1];
                        if (Arrays.equals(paramTypes,
                                method.getParameterTypes())) {
                            method.invoke(null, new Object[] {
                                    EntityArmorStandCustom.class,
                                    "customArmorStand", Integer.valueOf(63) });
                        }
                    }
                } catch (Exception exe) {
                    exc.addSuppressed(exe);
                }
                exc.printStackTrace();
            }
        }
    }
}