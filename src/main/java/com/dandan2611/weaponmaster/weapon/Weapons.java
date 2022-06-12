package com.dandan2611.weaponmaster.weapon;

import com.dandan2611.weaponmaster.weapon.impl.DebugStickWeapon;
import com.dandan2611.weaponmaster.weapon.impl.FishLauncherWeapon;
import com.dandan2611.weaponmaster.weapon.impl.LaserWeapon;
import com.dandan2611.weaponmaster.weapon.impl.RailGunWeapon;

public enum Weapons {

    DEBUG_STICK(DebugStickWeapon.class),
    RAILGUN(RailGunWeapon.class),
    LASER(LaserWeapon.class),
    FISH_LAUNCHER(FishLauncherWeapon.class);

    private Class<? extends Weapon> weaponClass;

    Weapons(Class<? extends Weapon> weaponClass) {
        this.weaponClass = weaponClass;
    }

    public Class<? extends Weapon> getWeaponClass() {
        return weaponClass;
    }

}
