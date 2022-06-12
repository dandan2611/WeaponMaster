package com.dandan2611.weaponmaster.weapon.impl;

import com.dandan2611.weaponmaster.Constants;
import com.dandan2611.weaponmaster.WeaponMaster;
import com.dandan2611.weaponmaster.entity.FriendlyMob;
import com.dandan2611.weaponmaster.utils.ItemBuilder;
import com.dandan2611.weaponmaster.utils.LocationUtils;
import com.dandan2611.weaponmaster.weapon.InteractionListener;
import com.dandan2611.weaponmaster.weapon.Weapon;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class InvokerStickWeapon extends Weapon implements InteractionListener, Listener {

    private final ItemStack weaponItem = new ItemBuilder(Material.WOODEN_HOE)
            .displayname(ChatColor.LIGHT_PURPLE + "Bâton de l'invocateur")
            .build();

    private final Integer taskId;

    private final ArrayList<FriendlyMob> mobs;

    private final Random random;

    public InvokerStickWeapon() {
        super();
        super.setInteractionListener(this);
        super.setEventListener(this);
        this.mobs = new ArrayList<>();
        this.taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(WeaponMaster.getInstance(),
                () -> {
                    for (int i = 0; i < mobs.size(); i++) {
                        FriendlyMob friendlyMob = mobs.get(i);

                        if(friendlyMob.isAlive()) {
                            friendlyMob.tickAi();
                        }
                        else {
                            friendlyMob.destroy();
                            mobs.remove(friendlyMob);
                        }
                    }
                },
                0L, Constants.INVOKER_STICK_TASK_TICKS);
        this.random = new Random();
    }

    @Override
    public String id() {
        return "INVOKER_STICK";
    }

    @Override
    public String name() {
        return "Invoker Stick";
    }

    @Override
    public ItemStack getItem() {
        return weaponItem;
    }

    @Override
    public void onInteract(PlayerInteractEvent event, Weapon weapon) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        Action action = event.getAction();

        if(Action.RIGHT_CLICK_AIR.equals(action) || Action.RIGHT_CLICK_BLOCK.equals(action)) {
            Location location = player.getLocation();

            int maxMobs = Constants.INVOKER_STICK_MAX_MOBS_SPAWN;
            int minMobs = Constants.INVOKER_STICK_MIN_MOBS_SPAWN;
            int numberOfMobs = random.nextInt(maxMobs - minMobs) + maxMobs; // TODO : Delete zombies on disable

            for (int i = 0; i < numberOfMobs; i++) {
                Location randomLocation = LocationUtils.randomSpawnableLocation(location,
                        Constants.INVOKER_STICK_MOBS_CLOSE_UP_DISTANCE,
                        Constants.INVOKER_STICK_MAX_RANDOM_LOCATION_TRIES);
                if(randomLocation == null)
                    randomLocation = location;
                FriendlyMob friendlyMob = new FriendlyMob(Constants.INVOKER_STICK_ENTITY_TYPE, randomLocation, player);
                mobs.add(friendlyMob);
            }

            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onMobTarget(EntityTargetLivingEntityEvent event) {
        Entity entity = event.getEntity();
        LivingEntity target = event.getTarget();

        if(target != null) {
            FriendlyMob mob = getMob(entity);
            if(mob == null)
                return;

            if(target.equals(mob.getOwner()))
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        Entity damager = event.getDamager();

        if(entity instanceof LivingEntity livingEntity) {
            ArrayList<FriendlyMob> entityMobs = getMobs(damager);
            for (FriendlyMob entityMob : entityMobs) {
                entityMob.setTarget(livingEntity);
            }
        }
        // TODO: Maybe add sounds and effects like anger?
    }

    public FriendlyMob getMob(Entity entity) {
        for (FriendlyMob mob : mobs) {
            if(entity.equals(mob.getEntity()))
                return mob;
        }
        return null;
    }

    public ArrayList<FriendlyMob> getMobs(Entity owner) {
        ArrayList<FriendlyMob> m = new ArrayList<>();
        for (FriendlyMob mob : mobs) {
            if(owner.equals(mob.getOwner()))
                m.add(mob);
        }
        return m;
    }

}
