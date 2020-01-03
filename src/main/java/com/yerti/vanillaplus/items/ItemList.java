package com.yerti.vanillaplus.items;

import com.yerti.vanillaplus.core.items.CustomItemStack;
import com.yerti.vanillaplus.core.utils.SkullUtils;
import org.bukkit.Material;
import org.bukkit.block.Skull;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class ItemList {

    //TODO: (Maybe) rewrite this to be non static, although I'm not completely sure if I want to

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

    // STORAGE
    public static final ItemStack CRAFTING_TERMINAL = new CustomItemStack(Material.IRON_BLOCK, 1)
            .name("&eCrafting Terminal")
            .glow();

    //MACHINES
    public static final ItemStack QUARRY_CONTROLLER = new CustomItemStack(Material.DISPENSER, 1)
            .name("&eQuarry Controller")
            .glow();





}
