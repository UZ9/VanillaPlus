package com.yerti.vanillaplus.utils.inventory.inventories;

import com.yerti.vanillaplus.core.items.CustomItemStack;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class MultiBlockSelection {

    private Inventory inventory;

    public MultiBlockSelection() {

        inventory = Bukkit.createInventory(null, 9, "§cStructures");
        addItems();

    }

    public void addItems() {
        //inventory.addItem(new CustomItemStack(Material.WORKBENCH, ChatColor.GOLD + "Work Station", 3, ChatColor.GRAY + "Used to craft several different items.").getStack());
    }

    public void openInventory(Player player) {
        player.openInventory(inventory);
    }

}
