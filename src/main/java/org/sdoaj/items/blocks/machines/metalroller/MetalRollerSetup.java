package org.sdoaj.items.blocks.machines.metalroller;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public class MetalRollerSetup {
    public static void init() {
        MetalRollerRecipes.addRecipe(new MetalRollerRecipe(Ingredient.fromItem(Items.APPLE), new ItemStack(Items.REDSTONE)));
    }
}
