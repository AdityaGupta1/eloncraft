package org.sdoaj.eloncraft.jei.metalroller;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import org.sdoaj.eloncraft.jei.LinearRecipeWrapper;
import org.sdoaj.eloncraft.jei.RecipeCategory;
import org.sdoaj.eloncraft.util.AssetUtil;

public class MetalRollerRecipeCategory extends RecipeCategory<LinearRecipeWrapper> {
    public static String uid = "eloncraft.metal_roller";

    private final IDrawable background;

    public MetalRollerRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(AssetUtil.getGuiLocation("metal_roller"), 42, 25, 92, 36);
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
        IGuiItemStackGroup guiStacks = recipeLayout.getItemStacks();

        guiStacks.init(0, true, 5, 9);
        guiStacks.set(0, wrapper.recipe.getInput().getMatchingStacks());

        guiStacks.init(1, false, 65, 9);
        guiStacks.set(1, wrapper.recipe.getOutput());
    }
}
