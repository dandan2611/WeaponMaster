package com.dandan2611.weaponmaster.weapon;

import com.dandan2611.weaponmaster.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public abstract class Weapon {

    protected InteractionListener interactionListener;
    protected Listener eventListener;

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

    public void give(Player player) {
        player.getInventory().addItem(getItem());
    }

    public void shutdown() {
        if(eventListener != null)
            HandlerList.unregisterAll(eventListener);
    }

    public InteractionListener getInteractionListener() {
        return interactionListener;
    }

    public void setInteractionListener(InteractionListener interactionListener) {
        this.interactionListener = interactionListener;
    }

    public Listener getEventListener() {
        return eventListener;
    }

    public void setEventListener(Listener eventListener) {
        this.eventListener = eventListener;
    }

}
