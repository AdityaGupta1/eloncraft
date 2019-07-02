package org.sdoaj.eloncraft.blocks.machines.refinery;

import net.minecraftforge.fluids.FluidStack;
import org.sdoaj.eloncraft.fluids.ModFluids;

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
        addRecipe(new RefineryRecipe(new FluidStack(ModFluids.OIL, 4), new FluidStack(ModFluids.RP1, 4)));
    }

    public static List<RefineryRecipe> getRecipes() {
        return new ArrayList<>(recipes);
    }
}