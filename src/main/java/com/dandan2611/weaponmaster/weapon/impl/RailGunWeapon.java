package com.dandan2611.weaponmaster.weapon.impl;

import com.dandan2611.weaponmaster.Constants;
import com.dandan2611.weaponmaster.WeaponMaster;
import com.dandan2611.weaponmaster.utils.ItemBuilder;
import com.dandan2611.weaponmaster.weapon.InteractionListener;
import com.dandan2611.weaponmaster.weapon.Weapon;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.UUID;

public class RailGunWeapon extends Weapon implements InteractionListener {

    private final ItemStack weaponItem = new ItemBuilder(Material.IRON_SWORD)
            .displayname(ChatColor.BLUE.toString() + ChatColor.BOLD + "Mitraillette")
            .build();

    private HashMap<UUID, Integer> playerTaskMap;

    public RailGunWeapon() {
        super();
        super.setInteractionListener(this);
        this.playerTaskMap = new HashMap<>();
    }

    @Override
    public String id() {
        return "RAILGUN";
    }

    @Override
    public String name() {
        return "Railgun";
    }

    @Override
    public ItemStack getItem() {
        return weaponItem;
    }

    @Override
    public void onInteract(PlayerInteractEvent event, Weapon weapon) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        Action action = event.getAction();

        if(Action.RIGHT_CLICK_AIR.equals(action) || Action.RIGHT_CLICK_BLOCK.equals(action)) {
            if(playerTaskMap.containsKey(uuid))
                Bukkit.getScheduler().cancelTask(playerTaskMap.get(uuid));
            int taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(WeaponMaster.getInstance(),
                    new Runnable() {
                        private int iterations = 0;

                        @Override
                        public void run() {
                            // Sniping particles
                            Block targetBlock = player.getTargetBlock(null, Constants.RAILGUN_MAX_DISTANCE);
                            Location playerLocation = player.getEyeLocation().subtract(0d, 0.5d, 0d);
                            Vector playerDirection = playerLocation.getDirection();
                            double distance = playerLocation.distance(targetBlock.getLocation());

                            for(double d = 0d; d < distance; d += 0.5)  {
                                Location particleLocation =
                                        playerLocation.clone().add(playerDirection.clone().multiply(d));
                                playerLocation.getWorld().spawnParticle(Particle.CRIT,
                                        particleLocation,
                                        1,
                                        0d,
                                        0d,
                                        0d,
                                        0d);
                            }

                            // Block breaking
                            if(!Material.AIR.equals(targetBlock.getType()))
                                targetBlock.breakNaturally();

                            // More iterations
                            iterations++;
                            if(iterations >= Constants.RAILGUN_MAX_ITERATIONS)
                                Bukkit.getScheduler().cancelTask(playerTaskMap.get(uuid));
                        }
                    }, 0L, Constants.RAILGUN_TASK_TICKS);
            playerTaskMap.put(uuid, taskId);
            event.setCancelled(true);
        }
    }

}
