package org.sdoaj.eloncraft.blocks.machines.generator;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import org.sdoaj.eloncraft.util.Util;

import java.util.ArrayList;
import java.util.List;

public final class GeneratorRecipes {
    private static final List<GeneratorRecipe> recipes = new ArrayList<>();

    public static void addRecipe(GeneratorRecipe recipe) {
        recipes.add(recipe);
    }

    public static GeneratorRecipe getRecipeFromInput(ItemStack input) {
        for (GeneratorRecipe recipe : recipes) {
            if (recipe.matches(input)) {
                return recipe;
            }
        }

        return null;
    }

    public static void init() {
        addRecipe(new GeneratorRecipe(Ingredient.fromStacks(new ItemStack(Items.COAL, 1, Util.wildcard)), 200, 10000));
        addRecipe(new GeneratorRecipe(Ingredient.fromStacks(new ItemStack(Blocks.COAL_BLOCK)), 9 * 200, 9 * 10000));
    }

    public static List<GeneratorRecipe> getRecipes() {
        return new ArrayList<>(recipes);
    }
}