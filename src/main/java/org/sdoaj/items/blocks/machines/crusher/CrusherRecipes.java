package org.sdoaj.items.blocks.machines.crusher;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public final class CrusherRecipes {
    private static final List<CrusherRecipe> recipes = new ArrayList<>();

    public static void addRecipe(CrusherRecipe recipe) {
        recipes.add(recipe);
    }

    public static CrusherRecipe getRecipeFromInput(ItemStack input) {
        for (CrusherRecipe recipe : recipes) {
            if (recipe.matches(input)) {
                return recipe;
            }
        }

        return null;
    }

    public static void init() {
        addRecipe(new CrusherRecipe(new ItemStack(Blocks.COAL_ORE), new ItemStack(Items.COAL, 2)));
    }
}