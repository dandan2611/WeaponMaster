package com.dandan2611.weaponmaster.listeners;

import com.dandan2611.weaponmaster.WeaponMaster;
import com.dandan2611.weaponmaster.weapon.Weapon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Weapon interaction main listener
 */
public class WeaponInteractionListener implements Listener {

    /**
     * Main class
     */
    private final WeaponMaster weaponMaster;

    /**
     * Initialize the listener
     * @param weaponMaster Main class
     */
    public WeaponInteractionListener(WeaponMaster weaponMaster) {
        this.weaponMaster = weaponMaster;
    }

    /**
     * On weapon interaction
     * @param event Interaction event
     */
    @EventHandler
    public void onInteraction(PlayerInteractEvent event) {
        ItemStack clickItem = event.getItem();
        if(clickItem == null)
            return;

        Weapon weapon = weaponMaster.getWeaponManager().getWeapon(clickItem);
        if(weapon != null)
            weapon.getInteractionListener().onInteract(event, weapon);
    }

}
