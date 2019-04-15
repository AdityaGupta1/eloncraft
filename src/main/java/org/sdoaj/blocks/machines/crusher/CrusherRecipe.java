package org.sdoaj.blocks.machines.crusher;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.sdoaj.util.StackUtil;

public class CrusherRecipe {
    private ItemStack input;
    private ItemStack output;

    public CrusherRecipe(ItemStack input, ItemStack output) {
        this.input = input;
        this.output = output;
    }

    public CrusherRecipe(Item input, Item output) {
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