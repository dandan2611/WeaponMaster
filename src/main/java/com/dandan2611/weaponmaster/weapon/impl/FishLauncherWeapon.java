package com.dandan2611.weaponmaster.weapon.impl;

import com.dandan2611.weaponmaster.Constants;
import com.dandan2611.weaponmaster.WeaponMaster;
import com.dandan2611.weaponmaster.utils.ItemBuilder;
import com.dandan2611.weaponmaster.weapon.InteractionListener;
import com.dandan2611.weaponmaster.weapon.Weapon;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class FishLauncherWeapon extends Weapon implements InteractionListener {

    private final ItemStack weaponItem = new ItemBuilder(Material.GOLDEN_HOE)
            .displayname(ChatColor.GOLD + "Lanceur de poisson")
            .build();

    private final Integer projectilesTaskId;

    private final ArrayList<FishGrenade> grenades;

    public FishLauncherWeapon() {
        super();
        super.setInteractionListener(this);
        this.grenades = new ArrayList<>();
        projectilesTaskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(WeaponMaster.getInstance(),
                () -> {
                    for (int i = 0; i < grenades.size(); i++) {
                        FishGrenade grenade = grenades.get(i);
                        if(grenade.isAlive()
                                && !grenade.isCountdownStarted()
                                && (grenade.grenadeEntity.isOnGround() || grenade.grenadeEntity.isInWater())) {
                            grenade.startCountdown();
                        }
                        else if(!grenade.isAlive())
                            grenades.remove(grenade);
                    }
                },
                0L, Constants.FISH_LAUNCHER_TASK_TICKS);
    }

    @Override
    public String id() {
        return "FISH_LAUNCHER";
    }

    @Override
    public String name() {
        return "Fish Launcher";
    }

    @Override
    public ItemStack getItem() {
        return weaponItem;
    }

    @Override
    public void shutdown() {
        super.shutdown();
        if(projectilesTaskId != null)
            Bukkit.getScheduler().cancelTask(projectilesTaskId);
    }

    @Override
    public void onInteract(PlayerInteractEvent event, Weapon weapon) {
        Player player = event.getPlayer();
        Action action = event.getAction();

        if(Action.RIGHT_CLICK_AIR.equals(action) || Action.RIGHT_CLICK_BLOCK.equals(action)) {
            FishGrenade fishGrenade = new FishGrenade(player.getEyeLocation().clone().subtract(0.5d, 0.5d, 0.5d), player.getEyeLocation().getDirection().clone().multiply(Constants.FISH_LAUNCHER_BOMB_VELOCITY));
            grenades.add(fishGrenade);
        }
    }

    private static class FishGrenade {

        private static final int STATE_DEFLATED = 0;
        private static final int STATE_HALF = 1;
        private static final int STATE_FULL = 2;

        private PufferFish grenadeEntity;
        private Integer countDownTaskId;

        public FishGrenade(Location startingLocation, Vector velocity) {
            World world = startingLocation.getWorld();

            if(world != null) {
                grenadeEntity = (PufferFish) world.spawnEntity(startingLocation, EntityType.PUFFERFISH);
                grenadeEntity.setVelocity(velocity);
                grenadeEntity.setPuffState(STATE_DEFLATED);
                grenadeEntity.setInvulnerable(true);
            }
        }

        public void startCountdown() {
            countDownTaskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(WeaponMaster.getInstance(),
                    new Runnable() {

                        private int timeRemaining = 10 * Constants.FISH_LAUNCHER_TIMER;
                        private final int firstFillTime = 10 * Constants.FISH_LAUNCHER_FIRST_FILL;
                        private final int secondFillTime = 10 * Constants.FISH_LAUNCHER_SECOND_FILL;

                        @Override
                        public void run() {
                            if(timeRemaining == firstFillTime) {
                                grenadeEntity.setPuffState(STATE_HALF);
                            }
                            else if(timeRemaining == secondFillTime) {
                                grenadeEntity.setPuffState(STATE_FULL);
                            }
                            else if(timeRemaining <= 0) {
                                Location entityLocation = grenadeEntity.getLocation();
                                World world = entityLocation.getWorld();
                                if(world != null) {
                                    entityLocation.getWorld().createExplosion(entityLocation,
                                            Constants.FISH_LAUNCHER_EXPLOSION_RADIUS);
                                }
                                destroy();
                            }
                            timeRemaining--;
                        }
                    }, 0L, Constants.FISH_LAUNCHER_COUNTDOWN_TICKS);
        }

        public void destroy() {
            if(countDownTaskId != null)
                Bukkit.getScheduler().cancelTask(countDownTaskId);
            if(grenadeEntity != null)
                grenadeEntity.remove();
        }

        public boolean isAlive() {
            return grenadeEntity != null && !grenadeEntity.isDead();
        }

        public boolean isCountdownStarted() {
            return countDownTaskId != null;
        }

    }

}
