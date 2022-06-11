package com.dandan2611.weaponmaster.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WeaponCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player player) {
            if(player.isOp() || player.hasPermission("weapon.use")) {
                // TODO: Command logic
            }
        }
        else {
            sender.sendMessage("This command can only be executed by a player!");
        }
        return true;
    }

}
