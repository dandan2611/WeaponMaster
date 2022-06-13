package com.dandan2611.weaponmaster.commands;

import com.dandan2611.weaponmaster.WeaponMaster;
import com.dandan2611.weaponmaster.weapon.Weapon;
import org.bukkit.ChatColor;
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
                }
            }
        }
        else {
            sender.sendMessage("This command can only be executed by a player!"); // TODO: Maybe give command by console?
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

    private void sendHelp(Player player) {
        String version = WeaponMaster.getInstance().getDescription().getVersion();
        player.sendMessage(ChatColor.AQUA + "This server is using WeaponMaster v" + version + " made by dandan2611");
        player.sendMessage(ChatColor.GRAY.toString() + ChatColor.UNDERLINE +"Available weapons :");
        Collection<Weapon> weapons = WeaponMaster.getInstance().getWeaponManager().getWeapons();
        for (Weapon weapon : weapons) {
            player.sendMessage(ChatColor.GREEN + "⋅ " + weapon.name() + " : " + weapon.id());
        }
    }

    private void giveWeapon(Player player, String[] args) {
        String weaponName = args[0].toUpperCase();

        if(weaponName.equals("ALL") || weaponName.equals("*")) {
            Collection<Weapon> weapons = WeaponMaster.getInstance().getWeaponManager().getWeapons();
            weapons.forEach(weapon -> weapon.give(player));
            fine(player, "With great power comes great responsibility...");
            return;
        }

        Weapon weapon = WeaponMaster.getInstance().getWeaponManager().getWeapon(weaponName);

        if(weapon == null) {
            err(player, "Weapon '" + weaponName + "' not found!");
            return;
        }
        weapon.give(player);
        fine(player, "You now have to power of the " + weapon.name() + " weapon!");
        // TODO: Give sound
    }

    private void err(Player player, String msg) {
        player.sendMessage(ChatColor.RED + msg);
    }

    private void fine(Player player, String msg) {
        player.sendMessage(ChatColor.GREEN + msg);
    }
}
