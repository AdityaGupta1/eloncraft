package org.sdoaj.eloncraft.blocks.machines.crusher;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import org.sdoaj.eloncraft.items.ModItems;
import org.sdoaj.eloncraft.recipes.LinearRecipe;

import java.util.ArrayList;
import java.util.List;

public final class CrusherRecipes {
    private static final List<LinearRecipe> recipes = new ArrayList<>();

    public static void addRecipe(LinearRecipe recipe) {
        recipes.add(recipe);
    }

    public static LinearRecipe getRecipeFromInput(ItemStack input) {
        for (LinearRecipe recipe : recipes) {
            if (recipe.matches(input)) {
                return recipe;
            }
        }

        return null;
    }

    public static void init() {
        addRecipe(new LinearRecipe().setInput(Blocks.COAL_ORE).setOutput(new ItemStack(Items.COAL, 2)));
        addRecipe(new LinearRecipe().setInput(Items.COAL).setOutput(new ItemStack(ModItems.CRUSHED_COAL, 2)));
    }
}