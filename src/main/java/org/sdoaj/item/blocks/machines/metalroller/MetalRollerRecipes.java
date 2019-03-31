package org.sdoaj.item.blocks.machines.metalroller;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import org.sdoaj.item.items.ModItems;

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
        addRecipe(new MetalRollerRecipe(Items.IRON_INGOT, ModItems.IRON_PLATE));
        addRecipe(new MetalRollerRecipe(Items.GOLD_INGOT, ModItems.GOLD_PLATE));
        addRecipe(new MetalRollerRecipe(ModItems.ALUMINUM_INGOT, ModItems.ALUMINUM_PLATE));
        addRecipe(new MetalRollerRecipe(ModItems.TITANIUM_INGOT, ModItems.TITANIUM_PLATE));
        addRecipe(new MetalRollerRecipe(ModItems.LITHIUM_INGOT, ModItems.LITHIUM_PLATE));
        addRecipe(new MetalRollerRecipe(ModItems.NICKEL_INGOT, ModItems.NICKEL_PLATE));
        addRecipe(new MetalRollerRecipe(ModItems.CHROMIUM_INGOT, ModItems.CHROMIUM_PLATE));
        addRecipe(new MetalRollerRecipe(ModItems.COPPER_INGOT, ModItems.COPPER_PLATE));
        addRecipe(new MetalRollerRecipe(ModItems.NIOBIUM_INGOT, ModItems.NIOBIUM_PLATE));
        addRecipe(new MetalRollerRecipe(ModItems.HAFNIUM_INGOT, ModItems.HAFNIUM_PLATE));
        // addRecipe(new MetalRollerRecipe(ModItems.MAGNESIUM_INGOT, ModItems.MAGNESIUM_PLATE));
        // addRecipe(new MetalRollerRecipe(ModItems.ZINC_INGOT, ModItems.ZINC_PLATE));

        addRecipe(new MetalRollerRecipe(ModItems.STEEL_INGOT, ModItems.STEEL_PLATE));

        addRecipe(new MetalRollerRecipe(ModItems.INCONEL_BARS, ModItems.INCONEL_PLATE));
        addRecipe(new MetalRollerRecipe(ModItems.ALUMINUM_2198_INGOT, ModItems.ALUMINUM_2198_PLATE));
        addRecipe(new MetalRollerRecipe(ModItems.ALUMINUM_7XXX_INGOT, ModItems.ALUMINUM_7XXX_PLATE));
        addRecipe(new MetalRollerRecipe(ModItems.NIOBIUM_C103_INGOT, ModItems.NIOBIUM_C103_PLATE));

        addRecipe(new MetalRollerRecipe(ModItems.CARBON_FIBERS, ModItems.CARBON_FIBER_PLATE));
    }
}