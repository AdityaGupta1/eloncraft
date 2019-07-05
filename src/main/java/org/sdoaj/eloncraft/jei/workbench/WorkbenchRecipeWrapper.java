package org.sdoaj.eloncraft.jei.workbench;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import org.sdoaj.eloncraft.blocks.machines.workbench.WorkbenchRecipe;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class WorkbenchRecipeWrapper implements IRecipeWrapper {
    public final WorkbenchRecipe recipe;

    public WorkbenchRecipeWrapper(WorkbenchRecipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputs(VanillaTypes.ITEM, Arrays.stream(recipe.getInputs()).flatMap(Arrays::stream)
                .filter(Objects::nonNull).flatMap(ingredient -> Arrays.stream(ingredient.getMatchingStacks()))
                .collect(Collectors.toList()));
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getOutput());
    }
}
