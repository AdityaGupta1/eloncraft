package org.sdoaj.eloncraft.jei.refinery;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.Fluid;
import org.sdoaj.eloncraft.blocks.machines.alloyfurnace.ContainerAlloyFurnace;
import org.sdoaj.eloncraft.blocks.machines.alloyfurnace.TileEntityAlloyFurnace;
import org.sdoaj.eloncraft.jei.Drawables;
import org.sdoaj.eloncraft.jei.JEIPlugin;
import org.sdoaj.eloncraft.jei.RecipeCategory;
import org.sdoaj.eloncraft.recipes.IngredientStack;
import org.sdoaj.eloncraft.util.AssetUtil;

import java.util.Arrays;
import java.util.List;

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

        // set capacity to 25 mB so that fluid shows as more than just a small line at the bottom
        guiStacks.init(0, true, 6, 6, 16, 83, Fluid.BUCKET_VOLUME / 40, false, Drawables.getInstance().FLUID_LINES);
        guiStacks.set(0, wrapper.recipe.getInput());

        guiStacks.init(1, false, 54, 6, 16, 83, Fluid.BUCKET_VOLUME / 40, false, Drawables.getInstance().FLUID_LINES);
        guiStacks.set(1, wrapper.recipe.getOutput());
    }
}
