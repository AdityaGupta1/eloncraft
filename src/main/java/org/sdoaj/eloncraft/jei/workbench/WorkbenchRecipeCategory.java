package org.sdoaj.eloncraft.jei.workbench;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import org.sdoaj.eloncraft.blocks.machines.alloyfurnace.ContainerAlloyFurnace;
import org.sdoaj.eloncraft.blocks.machines.workbench.TileEntityWorkbench;
import org.sdoaj.eloncraft.jei.RecipeCategory;
import org.sdoaj.eloncraft.recipes.IngredientStack;
import org.sdoaj.eloncraft.util.AssetUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WorkbenchRecipeCategory extends RecipeCategory<WorkbenchRecipeWrapper> {
    public static String uid = "eloncraft.elon_workbench";

    private final IDrawable background;

    public WorkbenchRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.drawableBuilder(AssetUtil.getGuiLocation("workbench_jei"), 0, 0, 240, 278)
                .setTextureSize(512, 512).build();
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
    public void setRecipe(IRecipeLayout recipeLayout, WorkbenchRecipeWrapper wrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiStacks = recipeLayout.getItemStacks();

        int xOffset = (13 - wrapper.recipe.getWidth()) / 2;
        int yOffset = 13 - wrapper.recipe.getHeight();
        yOffset = yOffset - yOffset / 2;

        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 13; j++) {
                int slot = TileEntityWorkbench.SLOT_INPUT_1 + 13 * i + j;
                guiStacks.init(slot, true, 3 + 18 * i, 3 + 18 * j);
                try {
                    Ingredient ingredient = wrapper.recipe.getInputs()[i - xOffset][j - yOffset];
                    if (ingredient != null) {
                        recipeLayout.getItemStacks().set(slot, Arrays.asList(ingredient.getMatchingStacks()));
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    // do nothing
                }
            }
        }

        guiStacks.init(TileEntityWorkbench.SLOT_OUTPUT, false, 111, 250);
        guiStacks.set(TileEntityWorkbench.SLOT_OUTPUT, wrapper.recipe.getOutput());
    }
}
