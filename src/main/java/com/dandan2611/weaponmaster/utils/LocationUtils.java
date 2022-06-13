package com.dandan2611.weaponmaster.utils;

import com.dandan2611.weaponmaster.Constants;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.Random;

/**
 * Location utils methods
 */
public class LocationUtils {

    /**
     * Determine a random location, with the same height, with given parameters
     * @param location Center location
     * @param xzRadius x and z random radius
     * @return Random determined location
     */
    public static Location randomLocation(Location location, int xzRadius) {
        Random random = new Random();
        int randX = random.nextInt(2 * xzRadius) - xzRadius;
        int randZ = random.nextInt(2 * xzRadius) - xzRadius;
        return new Location(location.getWorld(), location.getX() + randX, 0, location.getZ() + randZ);
    }

    /**
     * Determine a random spawnable location with given parameters
     * @param location Center location
     * @param xzRadius x and z random radius
     * @param maxTries Maximum number of tries before returning the provided location
     * @return Random determined location
     */
    public static Location randomSpawnableLocation(Location location, int xzRadius, int maxTries) {
        Location l;
        int tries = 0;
        do {
            tries++;
            l = randomLocation(location, xzRadius);
            for(int i = 0; i < Constants.RANDOM_LOCATION_MAX_Y_TRIES; i++) {
                l.setY(location.getY() + i);
                if(isSpawnable(l))
                    return l;
            }
        }
        while (!isSpawnable(l) && tries < maxTries); // TODO: Fix spawn in unspawnable location
        return l;
    }

    /**
     * Checks if a mob (2 blocks height) can be spawned at the specified location
     * @param location Location to check
     * @return If a mob can be spawned at the provided location
     */
    public static boolean isSpawnable(Location location) {
        Block block = location.getBlock();
        Block topBlock = block.getRelative(BlockFace.UP);
        Block bottomBlock = block.getRelative(BlockFace.DOWN);
        return Material.AIR.equals(block.getType())
                && (Material.AIR.equals(topBlock.getType()) || Material.AIR.equals(bottomBlock.getType()));
    }

}
