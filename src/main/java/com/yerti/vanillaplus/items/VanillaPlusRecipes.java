package com.yerti.vanillaplus.items;

import com.yerti.vanillaplus.VanillaPlus;
import com.yerti.vanillaplus.core.items.CustomItemStack;
import com.yerti.vanillaplus.core.recipe.CustomRecipe;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class VanillaPlusRecipes {

    public VanillaPlusRecipes() {
        CustomRecipe genCore = new CustomRecipe(new CustomItemStack(Material.FIREBALL, 1)
                .name("&eGenerator Core")
                .lore("&cCrafting component for generators")
                .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                .addFlag(ItemFlag.HIDE_DESTROYS)
                .addFlag(ItemFlag.HIDE_ENCHANTS)
                .damage(0)
                .enchant(Enchantment.ARROW_FIRE, 1));

        genCore.shape("%%%", "%@%", "%%%");
        genCore.setIngredient('%', new ItemStack(Material.IRON_INGOT));
        genCore.setIngredient('@', new ItemStack(Material.REDSTONE_BLOCK));
        VanillaPlus.getInstance().getServer().addRecipe(genCore.build());

        CustomRecipe coalGen = new CustomRecipe(ItemList.COAL_GENERATOR);


        coalGen.shape("%t%", "q@q", "efe");
        coalGen.setIngredient('%', new ItemStack(Material.IRON_INGOT));
        coalGen.setIngredient('t', new ItemStack(Material.GLASS));
        coalGen.setIngredient('q', new ItemStack(Material.IRON_BLOCK));

        //coalGen.setIngredient('@', new ItemStack(Material.FIREBALL));
        coalGen.setIngredient('@', ItemList.GENERATOR_CORE);
        coalGen.setIngredient('e', new ItemStack(Material.REDSTONE));
        coalGen.setIngredient('f', new ItemStack(Material.REDSTONE_BLOCK));
        VanillaPlus.getInstance().getServer().addRecipe(coalGen.build());

        CustomRecipe terminal = new CustomRecipe(ItemList.CRAFTING_TERMINAL);

        terminal.shape("%@%", "*#*", "%*%");
        terminal.setIngredient('%', new ItemStack(Material.REDSTONE));
        terminal.setIngredient('@', new ItemStack(Material.NETHER_STAR));
        terminal.setIngredient('*', new ItemStack(Material.IRON_BLOCK));
        terminal.setIngredient('#', new ItemStack(Material.SEA_LANTERN));
        VanillaPlus.getInstance().getServer().addRecipe(terminal.build());
    }

}
