package com.yerti.vanillaplus.structures.storage.gui;

import com.yerti.vanillaplus.core.inventories.CustomInventory;
import com.yerti.vanillaplus.core.items.CustomItemStack;
import com.yerti.vanillaplus.core.menus.Page;
import com.yerti.vanillaplus.core.menus.Pagination;
import com.yerti.vanillaplus.core.menus.Row;
import com.yerti.vanillaplus.core.menus.Size;
import com.yerti.vanillaplus.core.utils.SkullUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class InventoryStorage implements InventoryHolder {

    private final ItemStack leftArrow = new CustomItemStack(SkullUtils.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ==")).name("&cPrevious Page");
    private final ItemStack rightArrow = new CustomItemStack(SkullUtils.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTliZjMyOTJlMTI2YTEwNWI1NGViYTcxM2FhMWIxNTJkNTQxYTFkODkzODgyOWM1NjM2NGQxNzhlZDIyYmYifX19")).name("&cNext Page");

    public static Map<UUID, Integer> pageNumber = new HashMap<>();
    private List<CustomInventory> inventories = new ArrayList<>();
    public InventoryStorage() {

        List<ItemStack> stacks = new ArrayList<>();
        stacks.add(new ItemStack(Material.DIAMOND));
        stacks.add(new ItemStack(Material.WOODEN_DOOR));

        for (int x = 0; x < 4000; x++) {
            stacks.add(new ItemStack(Material.values()[(new Random().nextInt(Material.values().length))]));
        }

        Pagination<ItemStack> pagination = new Pagination<>(54, stacks);

        stacks.clear();
        System.out.println(pagination.totalPages());
        for (int i = 0; i < pagination.totalPages(); i++) {
            CustomInventory newInventory = new CustomInventory(this, 54, "&cCrafting Terminal", new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15));
            newInventory.fill(45, 54, newInventory.getBackgroundItem());



            newInventory.getInventory().setItem(49, new CustomItemStack(Material.NAME_TAG, 1).name("&cSearch"));

            if (i >= 1) newInventory.getInventory().setItem(47, leftArrow);
            if (i < pagination.totalPages() - 1) newInventory.getInventory().setItem(51, rightArrow);

            for (ItemStack stack : pagination.getPage(i)) {
                newInventory.getInventory().addItem(stack);
            }

            inventories.add(newInventory);
        }



    }

    public void open(Player player) {
        if (pageNumber.containsKey(player.getUniqueId())) {
            player.openInventory(inventories.get(pageNumber.get(player.getUniqueId())).getInventory());
        } else {
            pageNumber.put(player.getUniqueId(), 0);
            player.openInventory(inventories.get(0).getInventory());
        }


    }

    public Inventory get(Player player) {
        return inventories.get(pageNumber.get(player.getUniqueId())).getInventory();
    }


    @Override
    public Inventory getInventory() {
        return inventories.get(0).getInventory();
    }
}
