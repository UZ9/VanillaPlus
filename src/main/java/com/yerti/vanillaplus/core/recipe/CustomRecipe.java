package com.yerti.vanillaplus.core.recipe;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.util.HashMap;
import java.util.Map;

public class CustomRecipe {

    private ShapedRecipe recipe;
    //Output, Matrix Input
    private static Map<ItemStack, ItemStack[]> matrixMap;



    public CustomRecipe(ItemStack output) {
        matrixMap = new HashMap<>();
        recipe = new ShapedRecipe(output);
    }

    public void shape(String... shape) {
        recipe.shape(shape);
    }

    public void setIngredient(char key, ItemStack ingredient) {
        recipe.setIngredient(key, ingredient.getType());
    }

    public void build() {
        ItemStack[] matrix = new ItemStack[9];

        int index = 0;

        for (String string : recipe.getShape()) {
            for (char c : string.toCharArray()) {
                matrix[index] = recipe.getIngredientMap().get(c);
                index++;
            }
        }

        matrixMap.put(recipe.getResult(), matrix);


    }

    public static Map<ItemStack, ItemStack[]> getMatrixMap() {
        return matrixMap;
    }
}
