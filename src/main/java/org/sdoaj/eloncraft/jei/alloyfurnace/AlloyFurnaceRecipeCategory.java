package org.sdoaj.eloncraft.jei.alloyfurnace;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.crafting.Ingredient;
import org.sdoaj.eloncraft.blocks.machines.alloyfurnace.ContainerAlloyFurnace;
import org.sdoaj.eloncraft.blocks.machines.alloyfurnace.TileEntityAlloyFurnace;
import org.sdoaj.eloncraft.jei.LinearRecipeWrapper;
import org.sdoaj.eloncraft.jei.RecipeCategory;
import org.sdoaj.eloncraft.recipes.IngredientStack;
import org.sdoaj.eloncraft.util.AssetUtil;

import java.util.Arrays;
import java.util.List;

public class AlloyFurnaceRecipeCategory extends RecipeCategory<AlloyFurnaceRecipeWrapper> {
    public static String uid = "eloncraft.alloy_furnace";

    private final IDrawable background;

    public AlloyFurnaceRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(AssetUtil.getGuiLocation("alloy_furnace"), 4, 4, 168, 74);
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
    public void setRecipe(IRecipeLayout recipeLayout, AlloyFurnaceRecipeWrapper wrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiStacks = recipeLayout.getItemStacks();

        int[][] inputSlots = ContainerAlloyFurnace.getInputSlots();
        List<IngredientStack> inputs = wrapper.recipe.getInputs();

        int i = 0;
        for (int[] slot : inputSlots) {
            guiStacks.init(i, true, slot[0] - 5, slot[1] - 5);

            IngredientStack inputStack = inputs.get(0);
            inputStack.shrink(1);
            if (inputStack.getCount() == 0) {
                inputs.remove(0);
            }

            Ingredient input = inputStack.getIngredient();
            guiStacks.set(i, Arrays.asList(input.getMatchingStacks()));

            i++;
        }

        guiStacks.init(TileEntityAlloyFurnace.SLOT_OUTPUT, false, 75, 13);
        guiStacks.set(TileEntityAlloyFurnace.SLOT_OUTPUT, wrapper.recipe.getOutput());
    }
}
