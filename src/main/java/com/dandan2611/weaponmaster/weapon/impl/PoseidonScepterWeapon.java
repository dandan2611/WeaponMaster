package com.dandan2611.weaponmaster.weapon.impl;

import com.dandan2611.weaponmaster.Constants;
import com.dandan2611.weaponmaster.WeaponMaster;
import com.dandan2611.weaponmaster.utils.ItemBuilder;
import com.dandan2611.weaponmaster.weapon.InteractionListener;
import com.dandan2611.weaponmaster.weapon.Weapon;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PoseidonScepterWeapon extends Weapon implements InteractionListener, Listener {

    private final ItemStack weaponItem = new ItemBuilder(Material.NETHERITE_HOE)
            .displayname(ChatColor.AQUA.toString() + ChatColor.BOLD + "Sceptre de Poséidon")
            .build();

    private final Integer scepterTaskId;

    private final HashMap<UUID, WeaponContext> activatedPlayers;

    public PoseidonScepterWeapon() {
        super();
        super.setInteractionListener(this); // TODO: Interaction listener if null
        super.setEventListener(this);
        this.activatedPlayers = new HashMap<>();
        this.scepterTaskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(WeaponMaster.getInstance(),
                () -> {

                    double maxRadius = Constants.POSEIDON_SCEPTER_CYLINDER_MAX_RADIUS;
                    int steps = Constants.POSEIDON_SCEPTER_CYLINDER_STEPS;
                    int floors = Constants.POSEIDON_SCEPTER_NUM_FLOORS;
                    double separation = Constants.POSEIDON_SCEPTER_FLOORS_SEPARATION;

                    for (Map.Entry<UUID, WeaponContext> entry : activatedPlayers.entrySet()) {
                        WeaponContext context = entry.getValue();
                        Location location = context.location.clone().add(0d, 0.25d, 0d);

                        double radius = context.radius + 0.25d;
                        if(radius > maxRadius)
                            radius = maxRadius;
                        context.radius = radius;

                        for (int i = 0; i < floors; i++) {
                            for (double theta = 0; theta < 360; theta += 360d/steps) { // TODO: Constant
                                double rad = Math.toRadians(theta);
                                double x = Math.cos(rad) * radius;
                                double z = Math.sin(rad) * radius;
                                Location l = location.clone().add(x, 0, z);
                                location.getWorld().spawnParticle(Particle.WATER_DROP, l, 1, 0d, 0d, 0d, 0d);
                            }
                            location.add(0d, separation, 0d);
                        }
                    }
                },
                0L, Constants.POSEIDON_SCEPTER_TASK_TICKS);

    }

    @Override
    public String id() {
        return "POSEIDON_SCEPTER";
    }

    @Override
    public String name() {
        return "Poseidon Scepter";
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
            Location location = player.getLocation();

            if(activatedPlayers.containsKey(uuid)) { // Disable scepter
                activatedPlayers.remove(uuid);
            }
            else { // Enable scepter
                activatedPlayers.put(uuid, new WeaponContext(location, Constants.POSEIDON_SCEPTER_CYLINDER_START_RADIUS));
            }

            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        WeaponContext context = activatedPlayers.get(uuid);
        if(context != null) {
            Location location = context.location;
            if (location.distance(player.getLocation()) > 0.25D)
                player.teleport(location.clone().setDirection(player.getEyeLocation().getDirection()));
        }
    }

    private static class WeaponContext {

        protected final Location location;
        protected double radius;

        public WeaponContext(Location location, double radius) {
            this.location = location;
            this.radius = radius;
        }

    }

}
