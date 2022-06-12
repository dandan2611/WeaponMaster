package com.dandan2611.weaponmaster.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.Random;

public class LocationUtils {

    public static Location randomLocation(Location location, int xzRadius, int yRadius) {
        Random random = new Random();
        int randX = random.nextInt(xzRadius);
        int randZ = random.nextInt(xzRadius);
        int randY = random.nextInt(yRadius);
        return new Location(location.getWorld(), randX, randY, randZ);
    }

    public static Location randomSpawnableLocation(Location location, int xzRadius, int yRadius, int maxTries) {
        Location l = null;
        int tries = 0;
        do {
            tries++;
            l = randomLocation(location, xzRadius, yRadius);
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
