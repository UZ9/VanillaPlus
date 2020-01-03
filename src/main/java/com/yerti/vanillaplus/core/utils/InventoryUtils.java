package com.yerti.vanillaplus.core.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryUtils {

    /**
     * Checks if the player can fit the ItemStack within their inventory.
     *
     * @param player
     * @param itemStack
     * @return
     */
    public static boolean hasInventorySpace(Player player, ItemStack itemStack) {

        int freeSpace = 0;

        for (int i = 0; i < 36; i++) {
            ItemStack currentItemStack = player.getInventory().getItem(i);

            //Itemstack is Material.AIR
            if (currentItemStack == null) {
                freeSpace += itemStack.getMaxStackSize();
                //Item has same item meta
            } else if (currentItemStack.isSimilar(itemStack)) {
                freeSpace += itemStack.getMaxStackSize() - currentItemStack.getAmount();
            }

        }

        if (freeSpace >= itemStack.getAmount()) return true;

        return false;
    }

    /**
     * Checks if the inventory can fit the ItemStack within their inventory.
     *
     * @param inventory
     * @param itemStack
     * @return
     */
    public static boolean hasInventorySpace(Inventory inventory, ItemStack itemStack) {

        int freeSpace = 0;

        for (int i = 0; i < 9; i++) {
            ItemStack currentItemStack = inventory.getItem(i);

            //Itemstack is Material.AIR
            if (currentItemStack == null) {
                freeSpace += itemStack.getMaxStackSize();
                //Item has same item meta
            } else if (currentItemStack.isSimilar(itemStack)) {
                freeSpace += itemStack.getMaxStackSize() - currentItemStack.getAmount();
            }

        }

        if (freeSpace >= itemStack.getAmount()) return true;

        return false;
    }

    /**
     * Checks if the player have an amount of slots open
     *
     * @param player
     * @param slots
     * @return
     */
    public static boolean hasOpenSlots(Player player, int slots) {

        int freeSlots = 0;

        for (int i = 0; i < 36; i++) {
            ItemStack currentItemStack = player.getInventory().getItem(i);

            if (currentItemStack == null) {
                freeSlots++;
            }
        }

        return freeSlots >= slots;
    }

    /**
     * Returns the amount of a material in a given inventory
     * @param inventory
     * @param material
     * @return
     */
    public static int getItemCount(Inventory inventory, Material material) {
        int amount = 0;
        for (ItemStack stack : inventory.all(material).values()) {
            amount += stack.getAmount();

        }

        return amount;
    }


}
