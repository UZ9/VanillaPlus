package com.yerti.vanillaplus.structures.storage.gui;

import com.yerti.vanillaplus.core.inventories.CustomInventory;
import com.yerti.vanillaplus.core.items.CustomItemStack;
import com.yerti.vanillaplus.core.items.ItemStackUtils;
import com.yerti.vanillaplus.core.menus.Pagination;
import com.yerti.vanillaplus.core.utils.SkullUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class InventoryStorage  {

    private final ItemStack leftArrow = new CustomItemStack(SkullUtils.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ==")).name("&cPrevious Page");
    private final ItemStack rightArrow = new CustomItemStack(SkullUtils.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTliZjMyOTJlMTI2YTEwNWI1NGViYTcxM2FhMWIxNTJkNTQxYTFkODkzODgyOWM1NjM2NGQxNzhlZDIyYmYifX19")).name("&cNext Page");

    private Material[] materials = new Material[] {Material.STONE, Material.GRASS, Material.DIAMOND, Material.GOLD_AXE, Material.WOOD};

    public static Map<UUID, Integer> pageNumber = new HashMap<>();
    private List<CustomInventory> inventories = new ArrayList<>();


    public InventoryStorage(List<CustomInventory> inventories) {

        System.out.println("SIZE OF INVENTORIES: " + inventories.size());

        List<ItemStack> itemStacks = new ArrayList<>();


        for (CustomInventory inventory : inventories) {
            Collections.addAll(itemStacks, inventory.getInventory().getContents());
        }

        pagInit(itemStacks);





    }

    public InventoryStorage() {

        List<ItemStack> stacks = new ArrayList<>();
        stacks.add(new ItemStack(Material.DIAMOND));
        stacks.add(new ItemStack(Material.WOODEN_DOOR));

        for (int x = 0; x < 1000; x++) {
            ItemStack stack = new ItemStack(materials[new Random().nextInt(materials.length)]);

            AtomicBoolean found = new AtomicBoolean(false);

            stacks.forEach(item -> {
                if (item.getType().equals(stack.getType())) {
                    ItemMeta meta = item.getItemMeta();
                    List<String> lore = item.getItemMeta().getLore();
                    if (lore == null) {
                        lore = new ArrayList<>();
                        lore.add(ChatColor.RED + "Amount: 0");
                    }

                    if (lore.get(0).startsWith(ChatColor.RED + "Amount: ")) {
                        lore.set(0,  ChatColor.RED + "Amount: " + (Integer.parseInt(lore.get(0).replaceAll("[\\D]", "")) + 1));
                    } else {
                        lore.add(0,  ChatColor.RED + "Amount: " + (Integer.parseInt(lore.get(0).replaceAll("[\\D]", "")) + 1));
                    }


                    if (item.getAmount() < 64) {
                        item.setAmount(item.getAmount() + 1);
                    }

                    meta.setLore(lore);
                    item.setItemMeta(meta);
                    found.set(true);
                }
            });

            if (!found.get()) {
                if (stack.getType() == null) continue;

                CustomItemStack s = new CustomItemStack(stack.getType(), 1).lore(ChatColor.RED + "Amount: 1");

                stacks.add(s);
            }

        }

        Pagination<ItemStack> pagination = new Pagination<>(54, stacks);

        stacks.clear();
        System.out.println(pagination.totalPages());
        for (int i = 0; i < pagination.totalPages(); i++) {
            CustomInventory newInventory = new CustomInventory(new InventoryStorageHolder(), 54, "&cCrafting Terminal");
            newInventory.fill(45, 54, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15));



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


    public List<CustomInventory> getInventories() {
        return inventories;
    }

    public void setInventories(List<CustomInventory> inventories) {
        List<ItemStack> itemStacks = new ArrayList<>();

        for (CustomInventory inventory : inventories) {
            itemStacks.addAll(Arrays.asList(inventory.getInventory().getContents()));
        }

        inventories.clear();
        pagInit(itemStacks);
    }

    public void pagInit(List<ItemStack> stacks) {
        System.out.println("Calleda");
        Pagination<ItemStack> pagination = new Pagination<>(54, stacks);


        stacks.clear();
        System.out.println(pagination.totalPages());
        for (int i = 0; i < pagination.totalPages(); i++) {
            System.out.println("yoink");
            CustomInventory newInventory = new CustomInventory(new InventoryStorageHolder(), 54, "&cCrafting Terminal");
            newInventory.fill(45, 54, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15));



            newInventory.getInventory().setItem(49, new CustomItemStack(Material.NAME_TAG, 1).name("&cSearch"));
            System.out.println("doink");

            if (i >= 1) newInventory.getInventory().setItem(47, leftArrow);
            if (i < pagination.totalPages() - 1) newInventory.getInventory().setItem(51, rightArrow);

            for (ItemStack stack : pagination.getPage(i)) {
                if (stack == null) continue;
                if (stack.getType() == null) continue;
                if (stack.getType() == Material.AIR) continue;
                System.out.println("ADDING " + stack.getType());
                newInventory.getInventory().addItem(stack);
                System.out.println("Finished");
            }

            System.out.println("stonks");

            inventories.add(newInventory);
        }

        System.out.println("Finished successfully");

    }
}
