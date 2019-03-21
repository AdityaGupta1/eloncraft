package org.sdoaj.items.blocks.machines.workbench;

import net.minecraft.item.ItemStack;

import java.util.Arrays;

public class WorkbenchRecipe {
    protected ItemStack[][] inputs;
    protected ItemStack output;

    public WorkbenchRecipe(ItemStack[][] inputs, ItemStack output) {
        this.inputs = inputs;
        this.output = output;

        Arrays.stream(inputs).flatMap(Arrays::stream).forEach(stack -> {
            if (stack.getCount() != 1) {
                stack.setCount(1);
                System.err.println("warning: ItemStack with count != 1 passed to WorkbenchRecipe");
            }
        });
    }

    public boolean matches(ItemStack[][] stacks) {
        // TODO
        return true;
    }

    public ItemStack[][] getInputs() {
        return this.inputs;
    }

    public ItemStack getOutput() {
        return this.output;
    }

}