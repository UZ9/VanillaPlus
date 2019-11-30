package com.yerti.vanillaplus.core.recipe;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class CustomRecipeHandler implements Listener {

    @EventHandler
    public void onPrepareCraft(PrepareItemCraftEvent event) {

        CraftingInventory inventory = event.getInventory();

        for (Map.Entry<ItemStack, ItemStack[]> entry : CustomRecipe.getMatrixMap().entrySet()) {

            if (inventory.getResult().equals(entry.getKey())) {

                for (int i = 0; i < 9; i++) {
                    if (!inventory.getMatrix()[i].isSimilar(entry.getValue()[i])) {
                        inventory.setResult(null);
                        return;
                    }
                }


            }

        }


    }

}
