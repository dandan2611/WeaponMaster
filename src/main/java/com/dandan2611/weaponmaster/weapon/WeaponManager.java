package com.dandan2611.weaponmaster.weapon;

import com.dandan2611.weaponmaster.WeaponMaster;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 * The {@link WeaponManager} class is used to register and manage {@link Weapon} implementations
 */
public class WeaponManager {

    private static final Logger LOGGER = WeaponMaster.getInstance().getLogger();

    /**
     * Weapon map used to reference registered weapons
     */
    private final HashMap<String, Weapon> weaponMap;

    /**
     * Construct WeaponManager
     */
    public WeaponManager() {
        this.weaponMap = new HashMap<>();
    }

    /**
     * Register weapons from the {@link Weapons} enum
     */
    public void registerWeapons() {
        for (Weapons weaponType : Weapons.values()) {
            Class<? extends Weapon> weaponClass = weaponType.getWeaponClass();
            Constructor<?>[] constructors = weaponClass.getConstructors();
            for (Constructor<?> constructor : constructors) {
                if(constructor.getParameterCount() > 0)
                    continue;
                try {
                    Weapon weapon = (Weapon) constructor.newInstance();
                    registerWeapon(weapon);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    LOGGER.severe("Unable to load weapon " + weaponType.name());
                    e.printStackTrace();
                }
            }
        }
        LOGGER.info("Loaded " + weaponMap.size() + " weapons");
    }

    /**
     * Register a weapon
     * @param weapon Weapon to register
     */
    public void registerWeapon(Weapon weapon) {
        Listener listener = weapon.getEventListener();
        if(listener != null)
            Bukkit.getPluginManager().registerEvents(listener, WeaponMaster.getInstance());
        weaponMap.put(weapon.id(), weapon);
    }

    /**
     * Get a weapon from its id
     * @param weaponId Weapon id
     * @return The corresponding weapon or NULL if there is no weapon registered with this id
     */
    public Weapon getWeapon(String weaponId) {
        return weaponMap.get(weaponId.toUpperCase());
    }

    /**
     * Get a weapon from its {@link ItemStack} item
     * @param item Item to get the weapon
     * @return The corresponding weapon or NULL if there is no weapon matching with this item
     */
    public Weapon getWeapon(ItemStack item) {
        for (Weapon weapon : weaponMap.values()) {
            if(weapon.isWeapon(item))
                return weapon;
        }
        return null;
    }

    /**
     * Retrieve the registered weapons collection
     * @return Registered weapons
     */
    public Collection<Weapon> getWeapons() {
        return weaponMap.values();
    }

}
