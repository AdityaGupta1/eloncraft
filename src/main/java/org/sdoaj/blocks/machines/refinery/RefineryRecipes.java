package org.sdoaj.blocks.machines.refinery;

import net.minecraftforge.fluids.FluidStack;
import org.sdoaj.fluids.ModFluids;

import java.util.ArrayList;
import java.util.List;

public final class RefineryRecipes {
    private static final List<RefineryRecipe> recipes = new ArrayList<>();

    public static void addRecipe(RefineryRecipe recipe) {
        recipes.add(recipe);
    }

    public static RefineryRecipe getRecipeFromInput(FluidStack input) {
        for (RefineryRecipe recipe : recipes) {
            if (recipe.matches(input)) {
                return recipe;
            }
        }

        return null;
    }

    public static void init() {
        addRecipe(new RefineryRecipe(new FluidStack(ModFluids.OIL, 10), new FluidStack(ModFluids.RP1, 10)));
    }
}