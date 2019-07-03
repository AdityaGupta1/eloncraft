package org.sdoaj.eloncraft.jei.lox_collector;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraftforge.fluids.FluidStack;
import org.sdoaj.eloncraft.blocks.machines.alloyfurnace.AlloyFurnaceRecipe;
import org.sdoaj.eloncraft.blocks.machines.loxcollector.TileEntityLOXCollector;
import org.sdoaj.eloncraft.fluids.ModFluids;

import java.util.stream.Collectors;

public class LoxCollectorRecipeWrapper implements IRecipeWrapper {
    public final LoxCollectorRecipe recipe;

    public LoxCollectorRecipeWrapper(LoxCollectorRecipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setOutput(VanillaTypes.FLUID, new FluidStack(ModFluids.LOX, TileEntityLOXCollector.mbPerOperation));
    }
}
