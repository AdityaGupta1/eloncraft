package org.sdoaj.eloncraft.jei.alloyfurnace;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import org.sdoaj.eloncraft.blocks.machines.alloyfurnace.AlloyFurnaceRecipe;

import java.util.stream.Collectors;

public class AlloyFurnaceRecipeWrapper implements IRecipeWrapper {
    public final AlloyFurnaceRecipe recipe;

    public AlloyFurnaceRecipeWrapper(AlloyFurnaceRecipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputs(VanillaTypes.ITEM, recipe.getInputs().stream()
                .flatMap(stack -> stack.getMatchingStacks().stream()).collect(Collectors.toList()));
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getOutput());
    }
}
