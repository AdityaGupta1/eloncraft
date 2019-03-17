// based on CrusherRecipeRegistry from Actually Additions

package org.sdoaj.items.blocks.machines.metalroller;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public final class MetalRollerRecipes {
    private static final List<MetalRollerRecipe> recipes = new ArrayList<>();

    public static void addRecipe(MetalRollerRecipe recipe) {
        recipes.add(recipe);
    }

    public static MetalRollerRecipe getRecipeFromInput(ItemStack input) {
        return recipes.stream().filter(recipe -> recipe.matches(input)).findFirst().orElse(null);

    }
}