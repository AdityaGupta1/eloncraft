package org.sdoaj.eloncraft.jei;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import org.sdoaj.eloncraft.blocks.ModBlocks;
import org.sdoaj.eloncraft.blocks.machines.alloyfurnace.AlloyFurnaceRecipe;
import org.sdoaj.eloncraft.blocks.machines.alloyfurnace.AlloyFurnaceRecipes;
import org.sdoaj.eloncraft.blocks.machines.crusher.CrusherRecipes;
import org.sdoaj.eloncraft.blocks.machines.metalroller.MetalRollerRecipes;
import org.sdoaj.eloncraft.jei.alloyfurnace.AlloyFurnaceRecipeCategory;
import org.sdoaj.eloncraft.jei.alloyfurnace.AlloyFurnaceRecipeWrapper;
import org.sdoaj.eloncraft.jei.crusher.CrusherRecipeCategory;
import org.sdoaj.eloncraft.jei.metalroller.MetalRollerRecipeCategory;
import org.sdoaj.eloncraft.blocks.machines.LinearRecipe;

@mezz.jei.api.JEIPlugin
public class JEIPlugin implements IModPlugin {
    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IJeiHelpers helpers = registry.getJeiHelpers();

        registry.addRecipeCategories(new MetalRollerRecipeCategory(helpers.getGuiHelper()));
        registry.addRecipeCategories(new CrusherRecipeCategory(helpers.getGuiHelper()));
        registry.addRecipeCategories(new AlloyFurnaceRecipeCategory(helpers.getGuiHelper()));
    }

    @Override
    public void register(IModRegistry registry) {
        registry.handleRecipes(LinearRecipe.class, LinearRecipeWrapper::new, MetalRollerRecipeCategory.uid);
        registry.handleRecipes(LinearRecipe.class, LinearRecipeWrapper::new, CrusherRecipeCategory.uid);
        registry.handleRecipes(AlloyFurnaceRecipe.class, AlloyFurnaceRecipeWrapper::new, AlloyFurnaceRecipeCategory.uid);

        registry.addRecipes(MetalRollerRecipes.getRecipes(), MetalRollerRecipeCategory.uid);
        registry.addRecipes(CrusherRecipes.getRecipes(), CrusherRecipeCategory.uid);
        registry.addRecipes(AlloyFurnaceRecipes.getRecipes(), AlloyFurnaceRecipeCategory.uid);

        registry.addRecipeCatalyst(new ItemStack(ModBlocks.METAL_ROLLER), MetalRollerRecipeCategory.uid);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.CRUSHER), CrusherRecipeCategory.uid);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.ALLOY_FURNACE), AlloyFurnaceRecipeCategory.uid);
    }
}
