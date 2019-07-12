package org.sdoaj.eloncraft.blocks.machines.crusher;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.sdoaj.eloncraft.items.ModItems;
import org.sdoaj.eloncraft.blocks.machines.LinearRecipe;
import org.sdoaj.eloncraft.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public final class CrusherRecipes {
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
        addRecipe(new LinearRecipe().setInput(Blocks.COAL_ORE).setOutput(new ItemStack(Items.COAL, 2)));
        addRecipe(new LinearRecipe().setInput(Items.COAL).setOutput(new ItemStack(ModItems.CRUSHED_COAL, 2)));

        addOreRecipe("iron", ModItems.IRON_DUST);
        addOreRecipe("gold", ModItems.GOLD_DUST);
        addOreRecipe("aluminum", ModItems.ALUMINUM_DUST);
        addOreRecipe("titanium", ModItems.TITANIUM_DUST);
        addOreRecipe("lithium", ModItems.LITHIUM_DUST);
        addOreRecipe("nickel", ModItems.NICKEL_DUST);
        addOreRecipe("chromium", ModItems.CHROMIUM_DUST);
        addOreRecipe("copper", ModItems.COPPER_DUST);
        addOreRecipe("niobium", ModItems.NIOBIUM_DUST);
        addOreRecipe("hafnium", ModItems.HAFNIUM_DUST);
        addOreRecipe("magnesium", ModItems.MAGNESIUM_DUST);
        addOreRecipe("zinc", ModItems.ZINC_DUST);
    }

    public static void addOreRecipe(String material, Item dust) {
        String capitalized = StringUtil.capitalizeFirstLetter(material);
        addRecipe(new LinearRecipe().setInput("ore" + capitalized).setOutput(new ItemStack(dust, 2)));
        addRecipe(new LinearRecipe().setInput("ingot" + capitalized).setOutput(dust));
    }

    public static List<LinearRecipe> getRecipes() {
        return new ArrayList<>(recipes);
    }
}