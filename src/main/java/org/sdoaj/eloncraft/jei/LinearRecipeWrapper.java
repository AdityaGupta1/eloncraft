package org.sdoaj.eloncraft.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import org.sdoaj.eloncraft.recipes.LinearRecipe;

public class LinearRecipeWrapper implements IRecipeWrapper {
    public final LinearRecipe recipe;

    public LinearRecipeWrapper(LinearRecipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputs(VanillaTypes.ITEM, recipe.getInput().getMatchingStacks());
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getOutput());
    }
}
