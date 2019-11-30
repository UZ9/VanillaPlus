package com.yerti.vanillaplus.core.recipe;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.util.HashMap;
import java.util.Map;

public class CustomRecipe {

    private ShapedRecipe recipe;
    //Output, Matrix Input
    private Map<Character, ItemStack> matrixKey = new HashMap<>();
    private static Map<ItemStack, ItemStack[]> matrixMap = new HashMap<>();



    public CustomRecipe(ItemStack output) {
        recipe = new ShapedRecipe(output);
    }

    public void shape(String... shape) {
        recipe.shape(shape);
    }

    public void setIngredient(char key, ItemStack ingredient) {
        recipe.setIngredient(key, ingredient.getType());
        matrixKey.put(key, ingredient);
    }

    public ShapedRecipe build() {
        ItemStack[] matrix = new ItemStack[9];

        int index = 0;

        for (String string : recipe.getShape()) {
            for (char c : string.toCharArray()) {
                matrix[index] = matrixKey.get(c);
                index++;
            }
        }

        matrixMap.put(recipe.getResult(), matrix);

        System.out.println("Adding recipe" + recipe.getResult().getItemMeta().getDisplayName());
        for (ItemStack stack : matrix) {
            System.out.println(stack.getType());
        }


        return recipe;


    }

    public static Map<ItemStack, ItemStack[]> getMatrixMap() {
        return matrixMap;
    }
}
