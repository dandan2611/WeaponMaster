package com.dandan2611.weaponmaster.utils;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtils {

    /**
     * Checks if two itemstacks are comparable (same type, same amount and same displayname, ignores meta)
     * @param firstItem First item to compare
     * @param secondItem Second item to compare
     * @return If the two itemstacks matches the verified properties
     */
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
