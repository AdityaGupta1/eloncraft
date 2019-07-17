package org.sdoaj.eloncraft.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.sdoaj.eloncraft.blocks.ModBlocks;
import org.sdoaj.eloncraft.blocks.machines.LinearRecipe;
import org.sdoaj.eloncraft.blocks.machines.alloyfurnace.AlloyFurnaceRecipe;
import org.sdoaj.eloncraft.blocks.machines.alloyfurnace.AlloyFurnaceRecipes;
import org.sdoaj.eloncraft.blocks.machines.crusher.CrusherRecipes;
import org.sdoaj.eloncraft.blocks.machines.metalroller.MetalRollerRecipes;
import org.sdoaj.eloncraft.blocks.machines.refinery.RefineryRecipe;
import org.sdoaj.eloncraft.blocks.machines.refinery.RefineryRecipes;
import org.sdoaj.eloncraft.blocks.machines.workbench.WorkbenchRecipe;
import org.sdoaj.eloncraft.blocks.machines.workbench.WorkbenchRecipes;
import org.sdoaj.eloncraft.fluids.ModFluids;
import org.sdoaj.eloncraft.items.ModItems;
import org.sdoaj.eloncraft.jei.alloyfurnace.AlloyFurnaceRecipeCategory;
import org.sdoaj.eloncraft.jei.alloyfurnace.AlloyFurnaceRecipeWrapper;
import org.sdoaj.eloncraft.jei.crusher.CrusherRecipeCategory;
import org.sdoaj.eloncraft.jei.lox_collector.LoxCollectorRecipe;
import org.sdoaj.eloncraft.jei.lox_collector.LoxCollectorRecipeCategory;
import org.sdoaj.eloncraft.jei.lox_collector.LoxCollectorRecipeWrapper;
import org.sdoaj.eloncraft.jei.metalroller.MetalRollerRecipeCategory;
import org.sdoaj.eloncraft.jei.refinery.RefineryRecipeCategory;
import org.sdoaj.eloncraft.jei.refinery.RefineryRecipeWrapper;
import org.sdoaj.eloncraft.jei.workbench.WorkbenchRecipeCategory;
import org.sdoaj.eloncraft.jei.workbench.WorkbenchRecipeWrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        registry.addRecipeCategories(new LoxCollectorRecipeCategory(guiHelper));
    }

    @Override
    public void register(IModRegistry registry) {
        registry.handleRecipes(WorkbenchRecipe.class, WorkbenchRecipeWrapper::new, WorkbenchRecipeCategory.uid);
        registry.handleRecipes(LinearRecipe.class, LinearRecipeWrapper::new, MetalRollerRecipeCategory.uid);
        registry.handleRecipes(LinearRecipe.class, LinearRecipeWrapper::new, CrusherRecipeCategory.uid);
        registry.handleRecipes(AlloyFurnaceRecipe.class, AlloyFurnaceRecipeWrapper::new, AlloyFurnaceRecipeCategory.uid);
        registry.handleRecipes(RefineryRecipe.class, RefineryRecipeWrapper::new, RefineryRecipeCategory.uid);
        registry.handleRecipes(LoxCollectorRecipe.class, LoxCollectorRecipeWrapper::new, LoxCollectorRecipeCategory.uid);

        registry.addRecipes(WorkbenchRecipes.getRecipes(), WorkbenchRecipeCategory.uid);
        registry.addRecipes(MetalRollerRecipes.getRecipes(), MetalRollerRecipeCategory.uid);
        registry.addRecipes(CrusherRecipes.getRecipes(), CrusherRecipeCategory.uid);
        registry.addRecipes(AlloyFurnaceRecipes.getRecipes(), AlloyFurnaceRecipeCategory.uid);
        registry.addRecipes(RefineryRecipes.getRecipes(), RefineryRecipeCategory.uid);
        registry.addRecipes(Collections.singleton(new LoxCollectorRecipe()), LoxCollectorRecipeCategory.uid);

        registry.addRecipeCatalyst(new ItemStack(ModBlocks.ELON_WORKBENCH), VanillaRecipeCategoryUid.CRAFTING);

        registry.addRecipeCatalyst(new ItemStack(ModBlocks.ELON_WORKBENCH), WorkbenchRecipeCategory.uid);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.METAL_ROLLER), MetalRollerRecipeCategory.uid);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.CRUSHER), CrusherRecipeCategory.uid);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.ALLOY_FURNACE), AlloyFurnaceRecipeCategory.uid);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.REFINERY), RefineryRecipeCategory.uid);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.LOX_COLLECTOR), LoxCollectorRecipeCategory.uid);


        registry.addIngredientInfo(Collections.singletonList(new ItemStack(ModItems.FALCON9)), VanillaTypes.ITEM,
                "To launch the Falcon 9,",
                "- place it on a valid launchpad configuration",
                "- load it up with fuel and LOX",
                "- press \"LAUNCH\" from the launch controller",
                "- and follow the prompts to begin your journey to space.",
                "", "WARNING: If you don't have a tower directly centered with and adjacent to the launch platform, as well as in the direction the rocket's hatch is facing, you will fall to your death upon dismounting!");
        List<ItemStack> launchBlocks = new ArrayList<>();
        launchBlocks.add(new ItemStack(ModBlocks.LAUNCHPAD));
        launchBlocks.add(new ItemStack(ModBlocks.LAUNCH_CONTROLLER));
        registry.addIngredientInfo(launchBlocks, VanillaTypes.ITEM,
                "A full launchpad requires a 7x7 square of launchpad blocks with a launch controller in the center of any of the edges.",
                "", "Launchpads can be placed only on blocks that are full cubes.");
        List<FluidStack> fuelFluids = new ArrayList<>();
        fuelFluids.add(new FluidStack(ModFluids.OIL, Fluid.BUCKET_VOLUME));
        fuelFluids.add(new FluidStack(ModFluids.RP1, Fluid.BUCKET_VOLUME));
        registry.addIngredientInfo(fuelFluids, VanillaTypes.FLUID,
                "Oil can be found in lakes across the Overworld. Its main purpose is to be refined into RP-1 for fueling rockets.");
    }
}
