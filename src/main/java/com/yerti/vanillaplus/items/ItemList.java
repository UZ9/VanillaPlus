package com.yerti.vanillaplus.items;

import com.yerti.vanillaplus.core.items.CustomItemStack;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class ItemList {

    // COMPONENTS
    public static final ItemStack GENERATOR_CORE = new CustomItemStack(Material.FIREBALL, 1)
                    .name("&eGenerator Core")
                    .lore("&cCrafting component for generators")
                    .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                    .addFlag(ItemFlag.HIDE_DESTROYS)
                    .addFlag(ItemFlag.HIDE_ENCHANTS)
                    .damage(0)
                    .enchant(Enchantment.ARROW_FIRE, 1);


    // GENERATORS
    public static final ItemStack COAL_GENERATOR = new CustomItemStack(Material.FURNACE, 1)
            .name("&eCoal Generator")
            .lore("&cConsumes &fcoal&c to produce VU")
            .enchant(Enchantment.ARROW_FIRE, 1)
            .addFlag(ItemFlag.HIDE_ENCHANTS);





}