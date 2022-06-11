package com.dandan2611.weaponmaster;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class WeaponMaster extends JavaPlugin {

    private static WeaponMaster PLUGIN_INSTANCE;

    private Logger logger;

    @Override
    public void onLoad() {
        PLUGIN_INSTANCE = this;
        logger = getLogger();
    }

    @Override
    public void onEnable() {
        logger.info("Enabling plugin...");



        logger.fine("Plugin enabled!");
    }

    @Override
    public void onDisable() {
        logger.info("Disabling plugin...");



        logger.fine("Plugin disabled!");
    }

    public static WeaponMaster getInstance() {
        return PLUGIN_INSTANCE;
    }

}
