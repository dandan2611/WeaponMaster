package com.dandan2611.weaponmaster.utils;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtils {

    public static boolean isComparable(ItemStack firstItem, ItemStack secondItem) {
        ItemMeta firstItemMeta = firstItem.getItemMeta();
        ItemMeta secondItemMeta = secondItem.getItemMeta();
        if(firstItemMeta != null && secondItemMeta != null)
            return firstItem.getType().equals(secondItem.getType())
                    && firstItem.getAmount() == secondItem.getAmount()
                    && firstItemMeta.getDisplayName().equals(secondItemMeta.getDisplayName());
        else
            return false;
    }

}
