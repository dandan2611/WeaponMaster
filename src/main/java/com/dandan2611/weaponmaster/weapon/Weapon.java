package com.dandan2611.weaponmaster.weapon;

import com.dandan2611.weaponmaster.utils.ItemUtils;
import org.bukkit.inventory.ItemStack;

public abstract class Weapon {

    protected InteractionListener interactionListener;

    public Weapon(InteractionListener interactionListener) {
        this.interactionListener = interactionListener;
    }

    public Weapon() {
        this(null);
    }

    public abstract String id();

    public abstract String name();

    public abstract ItemStack getItem();

    public boolean isWeapon(ItemStack item) {
        return ItemUtils.isComparable(getItem(), item);
    }

    public InteractionListener getInteractionListener() {
        return interactionListener;
    }

    public void setInteractionListener(InteractionListener interactionListener) {
        this.interactionListener = interactionListener;
    }

}
