package com.yerti.vanillaplus.utils.inventory;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class InventoryGUI {

    int slots;
    String displayName;
    Inventory inventory;

    public InventoryGUI(String displayName, int slots) {
        this.slots = slots;
        this.displayName = displayName;
        inventory = Bukkit.createInventory(null, slots, displayName);
    }

    public void addItem(CustomItemStack itemstack, int slot) {
        inventory.setItem(slot, itemstack.getStack());
    }

    public void openInventory(Player player) {
        player.openInventory(inventory);
    }


}
