package org.sdoaj.items.blocks.machines.workbench;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public final class WorkbenchRecipes {
    private static final List<WorkbenchRecipe> recipes = new ArrayList<>();

    public static void addRecipe(WorkbenchRecipe recipe) {
        recipes.add(recipe);
    }

    public static WorkbenchRecipe getRecipeFromInput(ItemStack[][] input) {
        for (WorkbenchRecipe recipe : recipes) {
            if (recipe.matches(input)) {
                return recipe;
            }
        }

        return null;
    }

    public static void init() {
        addRecipe(new WorkbenchRecipe(new ItemStack[][]{
                {new ItemStack(Items.COMPARATOR), ItemStack.EMPTY, ItemStack.EMPTY, new ItemStack(Items.COMPARATOR)},
                {ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY},
                {new ItemStack(Items.COMPARATOR), ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY}
        }, new ItemStack(Items.REDSTONE)));
        addRecipe(new WorkbenchRecipe(new ItemStack[][]{
                {new ItemStack(Items.REPEATER)}
        }, new ItemStack(Items.REDSTONE)));
    }
}