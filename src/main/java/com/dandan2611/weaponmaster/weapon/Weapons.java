package com.dandan2611.weaponmaster.weapon;

import com.dandan2611.weaponmaster.weapon.impl.*;

/**
 * Weapons enumeration used to register pre-defined weapons at startup
 * Please note that you can also register weapons at runtime!
 */
public enum Weapons {

    DEBUG_STICK(DebugStickWeapon.class),
    RAILGUN(RailGunWeapon.class),
    LASER(LaserWeapon.class),
    FISH_LAUNCHER(FishLauncherWeapon.class),
    INVOKER_STICK(InvokerStickWeapon.class),
    POSEIDON_SCEPTER(PoseidonScepterWeapon.class);

    private Class<? extends Weapon> weaponClass;

    Weapons(Class<? extends Weapon> weaponClass) {
        this.weaponClass = weaponClass;
    }

    public Class<? extends Weapon> getWeaponClass() {
        return weaponClass;
    }

}
