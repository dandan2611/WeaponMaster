package com.dandan2611.weaponmaster.weapon.impl;

import com.dandan2611.weaponmaster.Constants;
import com.dandan2611.weaponmaster.utils.ItemBuilder;
import com.dandan2611.weaponmaster.weapon.InteractionListener;
import com.dandan2611.weaponmaster.weapon.Weapon;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.UUID;

public class LaserWeapon extends Weapon implements InteractionListener {

    private final ItemStack weaponItem = new ItemBuilder(Material.DIAMOND_HOE)
            .displayname(ChatColor.WHITE + "Laser")
            .build();

    public LaserWeapon() {
        super();
        setInteractionListener(this);
    }

    @Override
    public String id() {
        return "LASER";
    }

    @Override
    public String name() {
        return "Laser";
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
            Location location = player.getEyeLocation();
            Vector direction = player.getEyeLocation().getDirection();
            double distance = Constants.MAX_LASER_DISTANCE;

            LaserSender originSender = new LaserSender(location.clone().subtract(0d, 0.5d, 0d),
                    direction,
                    distance);
            originSender.drawLaser();
            event.setCancelled(true);
        }
    }

    private record LaserSender(Location source, Vector direction, double distance) {

        public void drawLaser() {
            source.setDirection(direction);
            for(double d = Constants.LASER_STEPS; d < distance; d += Constants.LASER_STEPS) {
                Location location = source.clone().add(direction.clone().multiply(d));
                Block block = location.getBlock();

                if(!Material.AIR.equals(block.getType())) {
                    // Block detected, reflection needed
                    Location previousLocation = location.clone().subtract(direction);

                    BlockFace collisionFace = block.getFace(previousLocation.getBlock());

                    double mulX = 1d;
                    double mulY = 1d;
                    double mulZ = 1d;

                    if(collisionFace != null) {
                        switch (collisionFace) {
                            case WEST, NORTH_WEST, EAST, SOUTH_EAST -> mulX = -1;
                            case SOUTH, SOUTH_WEST, NORTH, NORTH_EAST -> mulZ = -1;
                            case UP, DOWN -> mulY = -1;
                        }
                    }
                    else {
                        mulY = 0.5;
                        mulX = -0.5;
                        mulZ = -0.5;
                    }

                    Vector newDirection = direction.clone().multiply(new Vector(mulX, mulY, mulZ));
                    LaserSender laserSender = new LaserSender(previousLocation,
                            newDirection,
                            distance - d);
                    laserSender.drawLaser();
                    break;
                }

                Particle.DustOptions dust = new Particle.DustOptions(Color.RED, 1f);
                location.getWorld().spawnParticle(Particle.REDSTONE, location, 1, 0d, 0d, 0d, 0d, dust);
            }
        }

    }

}