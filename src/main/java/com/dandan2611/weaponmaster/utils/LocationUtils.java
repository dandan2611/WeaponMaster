package com.dandan2611.weaponmaster.utils;

import com.dandan2611.weaponmaster.Constants;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.Random;

public class LocationUtils {

    public static Location randomLocation(Location location, int xzRadius) {
        Random random = new Random();
        int randX = random.nextInt(xzRadius);
        int randZ = random.nextInt(xzRadius);
        return new Location(location.getWorld(), randX, 0, randZ);
    }

    public static Location randomSpawnableLocation(Location location, int xzRadius, int maxTries) {
        Location l = null;
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
        while (!isSpawnable(location) && tries < maxTries);
        return l;
    }

    public static boolean isSpawnable(Location location) {
        Block block = location.getBlock();
        Block topBlock = block.getRelative(BlockFace.UP);
        Block bottomBlock = block.getRelative(BlockFace.DOWN);
        return Material.AIR.equals(block.getType())
                && (Material.AIR.equals(topBlock.getType()) || Material.AIR.equals(bottomBlock.getType()));
    }

}
