package com.dandan2611.weaponmaster.weapon.impl;

import com.dandan2611.weaponmaster.Constants;
import com.dandan2611.weaponmaster.WeaponMaster;
import com.dandan2611.weaponmaster.entity.FriendlyMob;
import com.dandan2611.weaponmaster.utils.ItemBuilder;
import com.dandan2611.weaponmaster.utils.LocationUtils;
import com.dandan2611.weaponmaster.weapon.InteractionListener;
import com.dandan2611.weaponmaster.weapon.Weapon;
import org.bukkit.*;
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

public class InvokerStickWeapon extends Weapon implements InteractionListener, Listener {

    private final ItemStack weaponItem = new ItemBuilder(Material.WOODEN_SWORD)
            .displayname(ChatColor.LIGHT_PURPLE + "Bâton de l'invocateur")
            .build();

    private final Integer taskId;

    private final ArrayList<FriendlyMob> mobs;

    private final Random random;

    public InvokerStickWeapon() {
        super();
        super.setInteractionListener(this);
        super.setEventListener(this);
        super.setDefaultCooldownTime(Constants.INVOKER_STICK_COOLDOWN);
        this.mobs = new ArrayList<>();
        final long depopTime = Constants.INVOKER_STICK_DESPAWN_SECONDS*20/Constants.INVOKER_STICK_TASK_TICKS;
        this.taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(WeaponMaster.getInstance(),
                () -> {
                    for (int i = 0; i < mobs.size(); i++) {
                        FriendlyMob friendlyMob = mobs.get(i);

                        if(friendlyMob.isAlive()) {
                            if(friendlyMob.getTicks() >= depopTime) {
                                friendlyMob.depop();
                                continue;
                            }
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
        Action action = event.getAction();

        if(Action.RIGHT_CLICK_AIR.equals(action) || Action.RIGHT_CLICK_BLOCK.equals(action)) {
            if(!isInCooldown(player)) {
                startCooldown(player);
                Location location = player.getLocation();
                World world = location.getWorld();

                int maxMobs = Constants.INVOKER_STICK_MAX_MOBS_SPAWN;
                int minMobs = Constants.INVOKER_STICK_MIN_MOBS_SPAWN;
                int numberOfMobs = random.nextInt(maxMobs - minMobs) + maxMobs;

                for (int i = 0; i < numberOfMobs; i++) {
                    Location randomLocation = LocationUtils.randomSpawnableLocation(location,
                            Constants.INVOKER_STICK_MOBS_CLOSE_UP_DISTANCE,
                            Constants.INVOKER_STICK_MAX_RANDOM_LOCATION_TRIES);
                    if(randomLocation == null)
                        randomLocation = location;

                    EntityType[] availableMobs = Constants.INVOKER_STICK_ENTITIES_TYPES;
                    EntityType randomMob = availableMobs[random.nextInt(availableMobs.length)];

                    FriendlyMob friendlyMob = new FriendlyMob(randomMob, randomLocation, player);
                    mobs.add(friendlyMob);

                    if(world != null) {
                        world.strikeLightningEffect(randomLocation);
                        world.spawnParticle(Particle.SMOKE_NORMAL, randomLocation, 64, 1d, 1d, 1d, 0d);
                        world.spawnParticle(Particle.SMOKE_LARGE, randomLocation, 64, 1d, 1d, 1d, 0d);
                    }
                }
            }
            else {
                player.sendMessage("§cYou can use this item again in " + getCooldown(player) + " second(s).");
            }
            event.setCancelled(true);
        }
    }

    @Override
    public void shutdown() {
        super.shutdown();
        if(taskId != null)
            Bukkit.getScheduler().cancelTask(taskId);
        for (FriendlyMob mob : mobs) {
            mob.destroy();
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

                Location location = entityMob.getEntity().getLocation().clone().add(0, 1d, 0);
                World world = location.getWorld();
                if(world != null) {
                    world.playSound(location, Sound.ITEM_TRIDENT_HIT, SoundCategory.HOSTILE, 1f, 0.5f);
                    world.spawnParticle(Particle.SMOKE_NORMAL, location, 16, 1d, 1d, 1d, 0.05d);
                }
            }
        }
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
