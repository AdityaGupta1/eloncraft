package org.sdoaj.eloncraft.jei.refinery;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import org.sdoaj.eloncraft.blocks.machines.alloyfurnace.AlloyFurnaceRecipe;
import org.sdoaj.eloncraft.blocks.machines.refinery.RefineryRecipe;

import java.util.Collections;
import java.util.stream.Collectors;

public class RefineryRecipeWrapper implements IRecipeWrapper {
    public final RefineryRecipe recipe;

    public RefineryRecipeWrapper(RefineryRecipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(VanillaTypes.FLUID, recipe.getInput());
        ingredients.setOutput(VanillaTypes.FLUID, recipe.getOutput());
    }
}
