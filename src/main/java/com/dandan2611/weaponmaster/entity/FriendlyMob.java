package com.dandan2611.weaponmaster.entity;

import com.dandan2611.weaponmaster.Constants;
import com.dandan2611.weaponmaster.utils.LocationUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.*;

public class FriendlyMob {

    private final Mob entity;
    private final Entity owner;

    private int ticks;

    private Bat travelEntity;

    public FriendlyMob(EntityType entityType, Location location, Entity owner) {
        Class<? extends Entity> entityClass = entityType.getEntityClass();
        if(entityClass == null || !Mob.class.isAssignableFrom(entityClass))
            throw new IllegalArgumentException("Entity type must be a Mob");

        World world = location.getWorld();

        if(world == null)
            throw new NullPointerException("World is null");

        this.entity = (Mob) world.spawnEntity(location, entityType);
        entity.setCanPickupItems(false);
        entity.setInvulnerable(true);

        this.owner = owner;
        this.ticks = -1;
    }

    public void tickAi() {
        if(entity == null || entity.isDead()) {
            destroy();
            return;
        }
        if(owner == null || owner.isDead()) {
            // TODO: Proper disappear
            destroy();
            return;
        }

        Location location = entity.getLocation();

        if(ticks == -1) {
            // AI Init
        }
        else {
            if(entity.isDead()) {
                destroy();
                return;
            }
            LivingEntity target = entity.getTarget();
            if(target == null) {
                // Mob has no target, so get close to the player every 10 loops
                if(ticks % 20 == 0) {
                    Location randomLocation = LocationUtils.randomSpawnableLocation(owner.getLocation(),
                            Constants.INVOKER_STICK_MOBS_CLOSE_UP_DISTANCE,
                            Constants.INVOKER_STICK_MAX_RANDOM_LOCATION_TRIES);
                    if(randomLocation == null)
                        randomLocation = owner.getLocation();

                    if(travelEntity != null)
                        travelEntity.remove();

                    travelEntity = (Bat) randomLocation.getWorld().spawnEntity(randomLocation.clone().add(0, 2d, 0),
                            EntityType.BAT);
                    travelEntity.setAI(false);
                    travelEntity.setInvisible(true);
                    travelEntity.setInvulnerable(true);
                    travelEntity.setSilent(true);

                    entity.setTarget(travelEntity);
                }
            }
            else if(target.isDead()) {
                entity.setTarget(null);
                return;
            }
            else if(target.equals(travelEntity)) {
                if(ticks % 20 == 0) {
                    entity.setTarget(null);
                    travelEntity.remove();
                }
            }
            else {
                if(entity.getLocation().distance(target.getLocation()) > Constants.INVOKER_STICK_MAX_TARGET_DISTANCE) { // Target out of sight
                    entity.setTarget(null);
                }
            }
        }
        ticks++;
    }

    public void destroy() {
        if(entity != null)
            entity.remove();
        if(travelEntity != null)
            travelEntity.remove();
    }

    public boolean isAlive() {
        return entity != null && !entity.isDead();
    }

    public void setTarget(LivingEntity target) {
        if(!isAlive())
            return;
        entity.setTarget(target);
    }

    public Entity getEntity() {
        return entity;
    }

    public Entity getOwner() {
        return owner;
    }

}
