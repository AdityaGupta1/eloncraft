package org.sdoaj.item.blocks.machines.metalroller;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.sdoaj.util.StackUtil;

public class MetalRollerRecipe {
    private ItemStack input;
    private ItemStack output;

    public MetalRollerRecipe(ItemStack input, ItemStack output) {
        this.input = input;
        this.output = output;
    }

    public MetalRollerRecipe(Item input, Item output) {
        this(new ItemStack(input), new ItemStack(output));
    }

    public boolean matches(ItemStack stack) {
        return StackUtil.itemStackApplies(input, stack);
    }

    public ItemStack getInput() {
        return this.input;
    }

    public ItemStack getOutput() {
        return this.output;
    }
}