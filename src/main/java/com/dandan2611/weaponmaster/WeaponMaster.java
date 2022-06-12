package com.dandan2611.weaponmaster;

import com.dandan2611.weaponmaster.commands.WeaponCommand;
import com.dandan2611.weaponmaster.weapon.WeaponManager;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class WeaponMaster extends JavaPlugin {

    private static WeaponMaster PLUGIN_INSTANCE;

    private Logger logger;

    private WeaponManager weaponManager;

    @Override
    public void onLoad() {
        PLUGIN_INSTANCE = this;
        logger = getLogger();
    }

    @Override
    public void onEnable() {
        logger.info("Enabling plugin...");

        // Weapon command executor
        PluginCommand weaponCommand = getCommand("weapon");
        if(weaponCommand != null)
            weaponCommand.setExecutor(new WeaponCommand());

        // Managers instantiation
        this.weaponManager = new WeaponManager();
        weaponManager.registerWeapons();

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

    public WeaponManager getWeaponManager() {
        return weaponManager;
    }

}
