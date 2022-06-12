package com.dandan2611.weaponmaster.weapon;

import com.dandan2611.weaponmaster.WeaponMaster;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.logging.Logger;

public class WeaponManager {

    private static final Logger LOGGER = WeaponMaster.getInstance().getLogger();

    private final HashMap<String, Weapon> weaponMap;

    public WeaponManager() {
        this.weaponMap = new HashMap<>();
    }

    public void registerWeapons() {
        for (Weapons weaponType : Weapons.values()) {
            Class<? extends Weapon> weaponClass = weaponType.getWeaponClass();
            Constructor<?>[] constructors = weaponClass.getConstructors();
            for (Constructor<?> constructor : constructors) {
                if(constructor.getParameterCount() > 0)
                    continue;
                try {
                    Weapon weapon = (Weapon) constructor.newInstance();
                    weaponMap.put(weapon.id(), weapon);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    LOGGER.severe("Unable to load weapon " + weaponType.name());
                    e.printStackTrace();
                }
            }
        }
        LOGGER.info("Loaded " + weaponMap.size() + " weapons");
    }

    public Weapon getWeapon(String weaponId) {
        return weaponMap.get(weaponId);
    }

}
