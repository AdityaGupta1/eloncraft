package org.sdoaj.eloncraft.jei;

import mezz.jei.api.IGuiHelper;
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
import org.sdoaj.eloncraft.blocks.machines.refinery.RefineryRecipe;
import org.sdoaj.eloncraft.blocks.machines.refinery.RefineryRecipes;
import org.sdoaj.eloncraft.blocks.machines.workbench.WorkbenchRecipe;
import org.sdoaj.eloncraft.blocks.machines.workbench.WorkbenchRecipes;
import org.sdoaj.eloncraft.jei.alloyfurnace.AlloyFurnaceRecipeCategory;
import org.sdoaj.eloncraft.jei.alloyfurnace.AlloyFurnaceRecipeWrapper;
import org.sdoaj.eloncraft.jei.crusher.CrusherRecipeCategory;
import org.sdoaj.eloncraft.jei.metalroller.MetalRollerRecipeCategory;
import org.sdoaj.eloncraft.blocks.machines.LinearRecipe;
import org.sdoaj.eloncraft.jei.refinery.RefineryRecipeCategory;
import org.sdoaj.eloncraft.jei.refinery.RefineryRecipeWrapper;
import org.sdoaj.eloncraft.jei.workbench.WorkbenchRecipeCategory;
import org.sdoaj.eloncraft.jei.workbench.WorkbenchRecipeWrapper;

@mezz.jei.api.JEIPlugin
public class JEIPlugin implements IModPlugin {
    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IJeiHelpers helpers = registry.getJeiHelpers();
        IGuiHelper guiHelper = helpers.getGuiHelper();

        Drawables.init(guiHelper);

        registry.addRecipeCategories(new WorkbenchRecipeCategory(guiHelper));
        registry.addRecipeCategories(new MetalRollerRecipeCategory(guiHelper));
        registry.addRecipeCategories(new CrusherRecipeCategory(guiHelper));
        registry.addRecipeCategories(new AlloyFurnaceRecipeCategory(guiHelper));
        registry.addRecipeCategories(new RefineryRecipeCategory(guiHelper));
    }

    @Override
    public void register(IModRegistry registry) {
        registry.handleRecipes(WorkbenchRecipe.class, WorkbenchRecipeWrapper::new, WorkbenchRecipeCategory.uid);
        registry.handleRecipes(LinearRecipe.class, LinearRecipeWrapper::new, MetalRollerRecipeCategory.uid);
        registry.handleRecipes(LinearRecipe.class, LinearRecipeWrapper::new, CrusherRecipeCategory.uid);
        registry.handleRecipes(AlloyFurnaceRecipe.class, AlloyFurnaceRecipeWrapper::new, AlloyFurnaceRecipeCategory.uid);
        registry.handleRecipes(RefineryRecipe.class, RefineryRecipeWrapper::new, RefineryRecipeCategory.uid);

        registry.addRecipes(WorkbenchRecipes.getRecipes(), WorkbenchRecipeCategory.uid);
        registry.addRecipes(MetalRollerRecipes.getRecipes(), MetalRollerRecipeCategory.uid);
        registry.addRecipes(CrusherRecipes.getRecipes(), CrusherRecipeCategory.uid);
        registry.addRecipes(AlloyFurnaceRecipes.getRecipes(), AlloyFurnaceRecipeCategory.uid);
        registry.addRecipes(RefineryRecipes.getRecipes(), RefineryRecipeCategory.uid);

        registry.addRecipeCatalyst(new ItemStack(ModBlocks.ELON_WORKBENCH), WorkbenchRecipeCategory.uid);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.METAL_ROLLER), MetalRollerRecipeCategory.uid);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.CRUSHER), CrusherRecipeCategory.uid);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.ALLOY_FURNACE), AlloyFurnaceRecipeCategory.uid);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.REFINERY), RefineryRecipeCategory.uid);
    }
}
