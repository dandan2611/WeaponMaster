package com.dandan2611.weaponmaster.weapon;

import com.dandan2611.weaponmaster.weapon.impl.DebugStickWeapon;
import com.dandan2611.weaponmaster.weapon.impl.RailGunWeapon;

public enum Weapons {

    DEBUG_STICK(DebugStickWeapon.class),
    RAILGUN(RailGunWeapon.class);

    private Class<? extends Weapon> weaponClass;

    Weapons(Class<? extends Weapon> weaponClass) {
        this.weaponClass = weaponClass;
    }

    public Class<? extends Weapon> getWeaponClass() {
        return weaponClass;
    }

}
