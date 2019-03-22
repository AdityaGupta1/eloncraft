package org.sdoaj.items.blocks.machines.alloyfurnace;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import org.sdoaj.items.items.ModItems;

import java.util.ArrayList;
import java.util.List;

public final class AlloyFurnaceRecipes {
    private static final List<AlloyFurnaceRecipe> recipes = new ArrayList<>();

    public static void addRecipe(AlloyFurnaceRecipe recipe) {
        recipes.add(recipe);
    }

    public static AlloyFurnaceRecipe getRecipeFromInput(List<ItemStack> input) {
        for (AlloyFurnaceRecipe recipe : recipes) {
            if (recipe.matches(input)) {
                return recipe;
            }
        }

        return null;
    }

    public static void init() {
        addRecipe(new AlloyFurnaceRecipe(new ItemStack[]{
                new ItemStack(ModItems.NICKEL_INGOT, 7),
                new ItemStack(ModItems.CHROMIUM_INGOT, 2),
                new ItemStack(Items.IRON_INGOT, 1)
        }, new ItemStack(ModItems.INCONEL_BARS, 10)));

        addRecipe(new AlloyFurnaceRecipe(new ItemStack[]{
                new ItemStack(ModItems.ALUMINUM_INGOT, 8),
                new ItemStack(ModItems.COPPER_NUGGET, 1),
                new ItemStack(ModItems.LITHIUM_NUGGET, 1)
        }, new ItemStack(ModItems.ALUMINUM_2198_INGOT, 8)));

        addRecipe(new AlloyFurnaceRecipe(new ItemStack[]{
                new ItemStack(Blocks.COAL_BLOCK, 1),
                new ItemStack(Items.IRON_INGOT, 9)
        }, new ItemStack(ModItems.STEEL_INGOT, 9)));
    }
}