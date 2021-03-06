package org.sdoaj.eloncraft.jei.refinery;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraftforge.fluids.Fluid;
import org.sdoaj.eloncraft.jei.Drawables;
import org.sdoaj.eloncraft.jei.RecipeCategory;
import org.sdoaj.eloncraft.util.AssetUtil;

public class RefineryRecipeCategory extends RecipeCategory<RefineryRecipeWrapper> {
    public static String uid = "eloncraft.refinery";

    private final IDrawable background;

    public RefineryRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(AssetUtil.getGuiLocation("refinery_jei"), 0, 0, 76, 95);
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
    public void setRecipe(IRecipeLayout recipeLayout, RefineryRecipeWrapper wrapper, IIngredients ingredients) {
        IGuiFluidStackGroup guiStacks = recipeLayout.getFluidStacks();

        guiStacks.init(0, true, 6, 6, 16, 83, Fluid.BUCKET_VOLUME / 40, false, Drawables.getInstance().FLUID_LINES);
        guiStacks.set(0, wrapper.recipe.getInput());

        guiStacks.init(1, false, 54, 6, 16, 83, Fluid.BUCKET_VOLUME / 40, false, Drawables.getInstance().FLUID_LINES);
        guiStacks.set(1, wrapper.recipe.getOutput());
    }
}
