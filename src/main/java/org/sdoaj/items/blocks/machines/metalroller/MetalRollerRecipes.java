// based on CrusherRecipeRegistry from Actually Additions

package org.sdoaj.items.blocks.machines.metalroller;

import net.minecraft.item.ItemStack;
import org.sdoaj.items.items.ModItems;

import java.util.ArrayList;
import java.util.List;

public final class MetalRollerRecipes {
    private static final List<MetalRollerRecipe> recipes = new ArrayList<>();

    public static void addRecipe(MetalRollerRecipe recipe) {
        recipes.add(recipe);
    }

    public static MetalRollerRecipe getRecipeFromInput(ItemStack input) {
        for (MetalRollerRecipe recipe : recipes) {
            if (recipe.matches(input)) {
                return recipe;
            }
        }

        return null;
    }

    public static void init() {
        addRecipe(new MetalRollerRecipe(ModItems.ALUMINUM_INGOT, ModItems.ALUMINUM_PLATE));
        addRecipe(new MetalRollerRecipe(ModItems.TITANIUM_INGOT, ModItems.TITANIUM_PLATE));
        addRecipe(new MetalRollerRecipe(ModItems.LITHIUM_INGOT, ModItems.LITHIUM_PLATE));
        addRecipe(new MetalRollerRecipe(ModItems.NICKEL_INGOT, ModItems.NICKEL_PLATE));
        addRecipe(new MetalRollerRecipe(ModItems.CHROMIUM_INGOT, ModItems.CHROMIUM_PLATE));
    }
}