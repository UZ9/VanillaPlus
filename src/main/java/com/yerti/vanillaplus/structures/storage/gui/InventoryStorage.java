package com.yerti.vanillaplus.structures.storage.gui;

import com.yerti.vanillaplus.core.inventories.CustomInventory;
import com.yerti.vanillaplus.core.menus.Page;
import com.yerti.vanillaplus.core.menus.Row;
import com.yerti.vanillaplus.core.menus.Size;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InventoryStorage {

    List<Page> pages = new ArrayList<>();


    public InventoryStorage() {
        Page first = new Page("&cCrafting Terminal", Size.SIX_ROW);

        CustomInventory inventory = new CustomInventory(null, 54, "&cCrafting Terminal", new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 8));
        

        pages.add(new Page("&cCrafting Terminal", Size.SIX_ROW));


    }






}
