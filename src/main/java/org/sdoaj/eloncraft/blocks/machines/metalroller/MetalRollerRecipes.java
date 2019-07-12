package org.sdoaj.eloncraft.blocks.machines.metalroller;

import net.minecraft.item.ItemStack;
import org.sdoaj.eloncraft.items.ModItems;
import org.sdoaj.eloncraft.blocks.machines.LinearRecipe;

import java.util.ArrayList;
import java.util.List;

public final class MetalRollerRecipes {
    private static final List<LinearRecipe> recipes = new ArrayList<>();

    public static void addRecipe(LinearRecipe recipe) {
        recipes.add(recipe);
    }

    public static LinearRecipe getRecipeFromInput(ItemStack input) {
        for (LinearRecipe recipe : recipes) {
            if (recipe.matches(input)) {
                return recipe;
            }
        }

        return null;
    }

    public static void init() {
        addRecipe(new LinearRecipe().setInput("ingotIron").setOutput(ModItems.IRON_PLATE));
        addRecipe(new LinearRecipe().setInput("ingotGold").setOutput(ModItems.GOLD_PLATE));
        addRecipe(new LinearRecipe().setInput("ingotAluminum").setOutput(ModItems.ALUMINUM_PLATE));
        addRecipe(new LinearRecipe().setInput("ingotTitanium").setOutput(ModItems.TITANIUM_PLATE));
        addRecipe(new LinearRecipe().setInput("ingotLithium").setOutput(ModItems.LITHIUM_PLATE));
        addRecipe(new LinearRecipe().setInput("ingotNickel").setOutput(ModItems.NICKEL_PLATE));
        addRecipe(new LinearRecipe().setInput("ingotChromium").setOutput(ModItems.CHROMIUM_PLATE));
        addRecipe(new LinearRecipe().setInput("ingotCopper").setOutput(ModItems.COPPER_PLATE));
        addRecipe(new LinearRecipe().setInput("ingotNiobium").setOutput(ModItems.NIOBIUM_PLATE));
        addRecipe(new LinearRecipe().setInput("ingotHafnium").setOutput(ModItems.HAFNIUM_PLATE));
        addRecipe(new LinearRecipe().setInput("ingotMagnesium").setOutput(ModItems.MAGNESIUM_PLATE));
        addRecipe(new LinearRecipe().setInput("ingotZinc").setOutput(ModItems.ZINC_PLATE));

        addRecipe(new LinearRecipe().setInput("ingotSteel").setOutput(ModItems.STEEL_PLATE));

        addRecipe(new LinearRecipe().setInput(ModItems.INCONEL_BARS).setOutput(ModItems.INCONEL_PLATE));
        addRecipe(new LinearRecipe().setInput(ModItems.ALUMINUM_2198_INGOT).setOutput(ModItems.ALUMINUM_2198_PLATE));
        addRecipe(new LinearRecipe().setInput(ModItems.ALUMINUM_7XXX_INGOT).setOutput(ModItems.ALUMINUM_7XXX_PLATE));
        addRecipe(new LinearRecipe().setInput(ModItems.NIOBIUM_C103_INGOT).setOutput(ModItems.NIOBIUM_C103_PLATE));

        addRecipe(new LinearRecipe().setInput(ModItems.CARBON_FIBERS).setOutput(ModItems.CARBON_FIBER_PLATE));
        addRecipe(new LinearRecipe().setInput("plateSteel").setOutput(new ItemStack(ModItems.STEEL_ROD, 2)));
    }

    public static List<LinearRecipe> getRecipes() {
        return new ArrayList<>(recipes);
    }
}