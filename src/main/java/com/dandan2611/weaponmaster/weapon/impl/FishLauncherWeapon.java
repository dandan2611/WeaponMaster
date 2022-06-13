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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collection;

public class FishLauncherWeapon extends Weapon implements InteractionListener {

    private final ItemStack weaponItem = new ItemBuilder(Material.GOLDEN_SWORD)
            .displayname(ChatColor.GOLD + "Lanceur de poisson " + ChatColor.DARK_GRAY + "(Clic droit)")
            .lore("§r",
                    "§7Lancez des poissons... spéciaux...",
                    "§7Mais un conseil, §bcourez !",
                    "§r",
                    "§8Clic droit : Utiliser")
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
                        else {
                            Location location = grenade.grenadeEntity.getLocation();
                            World world = location.getWorld();
                            if(world != null) {
                                world.spawnParticle(Particle.WATER_SPLASH, location, 4, 0.25d, 0.25d,
                                        0.25d, 0.01d);
                            }
                        }
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
            FishGrenade fishGrenade = new FishGrenade(player.getEyeLocation().clone().subtract(0.5d, 0.5d, 0.5d),
                    player.getEyeLocation().getDirection().clone().multiply(Constants.FISH_LAUNCHER_BOMB_VELOCITY),
                    player);
            grenades.add(fishGrenade);
            event.setCancelled(true);
        }
    }

    private static class FishGrenade {

        private static final int STATE_DEFLATED = 0;
        private static final int STATE_HALF = 1;
        private static final int STATE_FULL = 2;

        private PufferFish grenadeEntity;
        private Integer countDownTaskId;
        private final Entity initiator;

        public FishGrenade(Location startingLocation, Vector velocity, Entity initiator) {
            this.initiator = initiator;

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
                            if(timeRemaining == firstFillTime) { // Puff 1
                                grenadeEntity.setPuffState(STATE_HALF);
                                playPuff(grenadeEntity.getLocation(), 1f);
                            }
                            else if(timeRemaining == secondFillTime) { // Puff 2
                                grenadeEntity.setPuffState(STATE_FULL);
                                playPuff(grenadeEntity.getLocation(), 1.5f);
                            }
                            else if(timeRemaining <= 0) { // Toggle explosion
                                Location entityLocation = grenadeEntity.getLocation();
                                World world = entityLocation.getWorld();
                                playPuff(entityLocation, 2f);
                                if(world != null) {
                                    float radius = Constants.FISH_LAUNCHER_EXPLOSION_RADIUS;
                                    float poisonRadius = Constants.FISH_LAUNCHER_POISON_RADIUS;

                                    world.spawnParticle(Particle.SPELL_MOB_AMBIENT, entityLocation,
                                            216, radius/3, radius/3, radius/3, 0.085d);
                                    world.createExplosion(entityLocation,
                                            Constants.FISH_LAUNCHER_EXPLOSION_RADIUS, false, true, initiator);
                                    Collection<Entity> entities =
                                            world.getNearbyEntities(entityLocation, poisonRadius, poisonRadius,
                                                    poisonRadius);
                                    for (Entity nearbyEntity : entities) {
                                        if(nearbyEntity instanceof LivingEntity livingEntity) {
                                            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.POISON,
                                                    Constants.FISH_LAUNCHER_POISON_DURATION*20, 1));
                                        }
                                    }
                                }
                                destroy();
                            }
                            timeRemaining--;
                        }

                        private void playPuff(Location location, float pitch) {
                            World world = location.getWorld();
                            if(world != null) {
                                world.playSound(location, Sound.ENTITY_CREEPER_HURT, SoundCategory.PLAYERS, 1f,
                                        pitch);
                                Particle.DustOptions dustOptions = null;
                                if(pitch == 1f)
                                    dustOptions = new Particle.DustOptions(Color.GREEN, 1f);
                                else if(pitch == 1.5f)
                                    dustOptions = new Particle.DustOptions(Color.ORANGE, 1f);
                                else if(pitch == 2f)
                                    dustOptions = new Particle.DustOptions(Color.RED, 1f);
                                double distance = 0.25d * pitch;
                                world.spawnParticle(Particle.REDSTONE, location, 16, distance, distance,
                                        distance, 0.5d, dustOptions);
                            }
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
