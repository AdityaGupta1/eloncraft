// based on CrusherRecipe from Actually Additions

package org.sdoaj.items.blocks.machines.metalroller;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import org.sdoaj.util.StackUtil;

public class MetalRollerRecipe {
    protected ItemStack input;
    protected ItemStack output;

    public MetalRollerRecipe(ItemStack input, ItemStack output) {
        this.input = input;
        this.output = output;
    }

    public MetalRollerRecipe(Item input, Item output) {
        this(new ItemStack(input), new ItemStack(output));
    }

    public boolean matches(ItemStack stack) {
        return StackUtil.ingredientApplies(input, stack);
    }

    public ItemStack getInput() {
        return this.input;
    }

    public ItemStack getOutput() {
        return this.output;
    }

}