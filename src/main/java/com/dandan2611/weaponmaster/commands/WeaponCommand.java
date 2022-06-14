package com.dandan2611.weaponmaster.commands;

import com.dandan2611.weaponmaster.WeaponMaster;
import com.dandan2611.weaponmaster.weapon.Weapon;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WeaponCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player player) {
            if(player.isOp() || player.hasPermission("weapon.use")) {
                switch (args.length) {
                    case 0 -> sendHelp(player);
                    case 1 -> giveWeapon(player, args);
                    default -> err(player, "Correct usage: /weapon <weapon_id>");
                }
            }
        }
        else {
            sender.sendMessage("This command can only be executed by a player!");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        ArrayList<String> suggestions = new ArrayList<>();
        if(args.length == 1) {
            Collection<Weapon> weapons = WeaponMaster.getInstance().getWeaponManager().getWeapons();
            for (Weapon weapon : weapons) {
                suggestions.add(weapon.id());
            }
        }
        return suggestions;
    }

    /**
     * Send help message
     * @param player Target player
     */
    private void sendHelp(Player player) {
        String version = WeaponMaster.getInstance().getDescription().getVersion();
        player.sendMessage(ChatColor.AQUA + "This server is using WeaponMaster v" + version + " made by dandan2611");
        player.sendMessage(ChatColor.GRAY.toString() + ChatColor.UNDERLINE +"Available weapons :");
        Collection<Weapon> weapons = WeaponMaster.getInstance().getWeaponManager().getWeapons();
        for (Weapon weapon : weapons) {
            player.sendMessage(ChatColor.GREEN + "⋅ " + weapon.name() + " : " + weapon.id());
        }
    }

    /**
     * Give weapon to specified player
     * @param player Player to give weapon to
     * @param args Array of arguments
     */
    private void giveWeapon(Player player, String[] args) {
        String weaponName = args[0];

        if(weaponName.equalsIgnoreCase("all") || weaponName.equals("*")) {
            Collection<Weapon> weapons = WeaponMaster.getInstance().getWeaponManager().getWeapons();
            weapons.forEach(weapon -> weapon.give(player));
            fine(player, "With great power comes great responsibility...");
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1f, 0.5f);
            return;
        }

        Weapon weapon = WeaponMaster.getInstance().getWeaponManager().getWeapon(weaponName);

        if(weapon == null) {
            err(player, "Weapon '" + weaponName + "' not found!");
            return;
        }
        weapon.give(player);
        fine(player, "You now have to power of the " + weapon.name() + " weapon!");
        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1f, 0.5f);
    }

    /**
     * Send red message to the player
     * @param player Target player
     * @param msg Message
     */
    private void err(Player player, String msg) {
        player.sendMessage(ChatColor.RED + msg);
    }

    /**
     * Send green message to the player
     * @param player Target player
     * @param msg Message
     */
    private void fine(Player player, String msg) {
        player.sendMessage(ChatColor.GREEN + msg);
    }
}
