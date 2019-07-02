package org.sdoaj.eloncraft.jei.crusher;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import org.sdoaj.eloncraft.jei.LinearRecipeWrapper;
import org.sdoaj.eloncraft.jei.RecipeCategory;
import org.sdoaj.eloncraft.util.AssetUtil;

public class CrusherRecipeCategory extends RecipeCategory<LinearRecipeWrapper> {
    public static String uid = "eloncraft.crusher";

    private final IDrawable background;

    public CrusherRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(AssetUtil.getGuiLocation("crusher"), 42, 25, 92, 36);
    }

    @Override
    public String getUid() {
        return uid;
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, LinearRecipeWrapper wrapper, IIngredients ingredients) {
        recipeLayout.getItemStacks().init(0, true, 5, 9);
        recipeLayout.getItemStacks().set(0, wrapper.recipe.getInput().getMatchingStacks());

        recipeLayout.getItemStacks().init(1, true, 65, 9);
        recipeLayout.getItemStacks().set(1, wrapper.recipe.getOutput());
    }
}
