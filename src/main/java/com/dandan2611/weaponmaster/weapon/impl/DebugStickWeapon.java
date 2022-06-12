package com.dandan2611.weaponmaster.weapon.impl;

import com.dandan2611.weaponmaster.utils.ItemBuilder;
import com.dandan2611.weaponmaster.weapon.InteractionListener;
import com.dandan2611.weaponmaster.weapon.Weapon;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class DebugStickWeapon extends Weapon implements InteractionListener {

    private final ItemStack weaponItem = new ItemBuilder(Material.STICK, ChatColor.AQUA + "Debug Stick")
            .build();

    public DebugStickWeapon() {
        super();
        super.setInteractionListener(this);
    }

    @Override
    public String id() {
        return "DEBUG_STICK";
    }

    @Override
    public String name() {
        return "Debug Stick";
    }

    @Override
    public ItemStack getItem() {
        return weaponItem;
    }

    @Override
    public void onInteract(PlayerInteractEvent event, Weapon weapon) {
        Player player = event.getPlayer();
        player.getVelocity().add(new Vector(0f, 5f, 0f));
        player.sendMessage(ChatColor.GREEN + "Pouf!");
    }

}
