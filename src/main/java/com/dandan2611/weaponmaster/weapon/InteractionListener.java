package com.dandan2611.weaponmaster.weapon;

import org.bukkit.event.player.PlayerInteractEvent;

public interface InteractionListener {

    void onInteract(PlayerInteractEvent event, Weapon weapon);

}
