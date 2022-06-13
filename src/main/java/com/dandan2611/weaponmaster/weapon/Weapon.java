package com.dandan2611.weaponmaster.weapon;

import com.dandan2611.weaponmaster.utils.ItemUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public abstract class Weapon {

    protected InteractionListener interactionListener;
    protected Listener eventListener;
    protected HashMap<UUID, Long> playerCooldownMap;
    protected Long defaultCooldownTime;

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

    public void cooldown(OfflinePlayer player) {
        playerCooldownMap.put(player.getUniqueId(), System.currentTimeMillis());
    }

    public boolean isInCooldown(OfflinePlayer player) {
        return isInCooldown(player, defaultCooldownTime);
    }

    public boolean isInCooldown(OfflinePlayer player, Long cooldownTime) {
        Long cooldownStartTime = playerCooldownMap.get(player.getUniqueId());
        return cooldownStartTime != null && cooldownStartTime < System.currentTimeMillis() + cooldownTime;
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

    public HashMap<UUID, Long> getPlayerCooldownMap() {
        return playerCooldownMap;
    }

    public void setPlayerCooldownMap(HashMap<UUID, Long> playerCooldownMap) {
        this.playerCooldownMap = playerCooldownMap;
    }

    public Long getDefaultCooldownTime() {
        return defaultCooldownTime;
    }

    public void setDefaultCooldownTime(Long defaultCooldownTime) {
        this.defaultCooldownTime = defaultCooldownTime;
    }

}
