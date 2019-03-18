// based on CrusherRecipeRegistry from Actually Additions

package org.sdoaj.items.blocks.machines.alloyfurnace;

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
        addRecipe(new AlloyFurnaceRecipe(new ArrayList<ItemStack>() {{
            add(new ItemStack(ModItems.NICKEL_INGOT, 7));
            add(new ItemStack(ModItems.CHROMIUM_INGOT, 2));
            add(new ItemStack(Items.IRON_INGOT, 1));
        }}, new ItemStack(ModItems.INCONEL_BARS, 10)));
    }
}