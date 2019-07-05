package org.sdoaj.eloncraft.jei.lox_collector;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraftforge.fluids.Fluid;
import org.sdoaj.eloncraft.jei.Drawables;
import org.sdoaj.eloncraft.jei.RecipeCategory;
import org.sdoaj.eloncraft.util.AssetUtil;

public class LoxCollectorRecipeCategory extends RecipeCategory<LoxCollectorRecipeWrapper> {
    public static String uid = "eloncraft.lox_collector";

    private final IDrawable background;

    public LoxCollectorRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(AssetUtil.getGuiLocation("lox_collector_jei"), 0, 0, 45, 95);
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
    public void setRecipe(IRecipeLayout recipeLayout, LoxCollectorRecipeWrapper wrapper, IIngredients ingredients) {
        IGuiFluidStackGroup guiStacks = recipeLayout.getFluidStacks();

        guiStacks.init(0, true, 6, 6, 16, 83, Fluid.BUCKET_VOLUME / 40, false, Drawables.getInstance().FLUID_LINES);
        guiStacks.set(0, wrapper.recipe.output);
    }
}
