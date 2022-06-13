package com.dandan2611.weaponmaster;

import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.bukkit.World;

public enum CustomSounds {

    FISH_LAUNCHER_FIRE("weaponmaster.fishlauncher.fire");

    private final String namespacedName;

    CustomSounds(String namespacedName) {
        this.namespacedName = namespacedName;
    }

    public void playSound(Location location, SoundCategory category, float volume, float pitch) {
        World world = location.getWorld();
        if(world != null) {
            world.playSound(location, namespacedName, category, volume, pitch);
        }
    }

    public String getNamespacedName() {
        return namespacedName;
    }

}
