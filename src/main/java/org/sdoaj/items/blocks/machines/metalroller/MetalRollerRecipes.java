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
        addRecipe(new MetalRollerRecipe(ModItems.aluminumIngot, ModItems.aluminumPlate));
        addRecipe(new MetalRollerRecipe(ModItems.titaniumIngot, ModItems.titaniumPlate));
        addRecipe(new MetalRollerRecipe(ModItems.lithiumIngot, ModItems.lithiumPlate));
        addRecipe(new MetalRollerRecipe(ModItems.nickelIngot, ModItems.nickelPlate));
        addRecipe(new MetalRollerRecipe(ModItems.chromiumIngot, ModItems.chromiumPlate));
    }
}