package com.yerti.vanillaplus.utils.inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomItemStack {

    private String displayName;
    private Material material;
    private boolean glowing;
    private String[] lore;
    private ItemStack stack;
    private int amount;

    public CustomItemStack(Material material, String displayName, int amount, boolean glowing, String... lore) {

        this.displayName = ChatColor.translateAlternateColorCodes('&', displayName);;
        this.material = material;
        this.glowing = glowing;
        this.lore = lore;
        this.amount = amount;

        updateStack();
    }

    public CustomItemStack(Material material, int amount) {
        this.material = material;
        this.amount = amount;


        updateStack();
    }

    public CustomItemStack(Material material, String displayName, int amount) {
        this.displayName = ChatColor.translateAlternateColorCodes('&', displayName);;
        this.material = material;
        this.amount = amount;

        updateStack();
    }

    public CustomItemStack(Material material, String displayName, int amount, boolean glowing) {
        this.displayName = ChatColor.translateAlternateColorCodes('&', displayName);;
        this.material = material;
        this.glowing = glowing;
        this.amount = amount;

        updateStack();
    }

    public CustomItemStack(Material material, String displayName, int amount, String... lore) {
        this.displayName = ChatColor.translateAlternateColorCodes('&', displayName);
        this.material = material;
        this.lore = lore;
        this.amount = amount;

        updateStack();
    }

    public CustomItemStack(ItemStack stack) {

        if (stack.getType() == null) return;


        this.material = stack.getType();
        this.amount = stack.getAmount();
        this.stack = new ItemStack(material, amount);

        if (stack.hasItemMeta()) {
            this.stack.setItemMeta(stack.getItemMeta());
        }



    }


    public ItemStack getStack() {
        return stack;
    }

    public String getDisplayname() {
        return displayName;
    }

    public void setDisplayname(String displayname) {
        this.displayName = displayname;
        updateStack();
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
        updateStack();
    }

    public boolean isGlowing() {
        return glowing;
    }
    public void setGlowing(boolean glowing) {
        this.glowing = glowing;
        updateStack();
    }

    public String[] getLore() {
        return lore;
    }

    public void setLore(String[] lore) {
        this.lore = lore;
        updateStack();

        
    }




    public void updateStack() {
        if (material != null) {
            stack = new ItemStack(material, amount);
        } else {
            stack = new ItemStack(Material.AIR);
        }

        ItemMeta meta = stack.getItemMeta();

        if (displayName != null) {
            meta.setDisplayName(displayName);
        }

        if (glowing) {
            meta.addEnchant(Enchantment.DAMAGE_ALL, 3, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        } else {
            meta.removeItemFlags();
            meta.removeEnchant(Enchantment.DAMAGE_ALL);
        }

        if (lore != null) {

            List<String> loreList = Arrays.asList(lore);

            meta.setLore(Arrays.asList(lore));
        }

        stack.setItemMeta(meta);
    }

}