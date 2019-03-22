package org.sdoaj.items.blocks.machines.workbench;

import net.minecraft.item.ItemStack;
import org.sdoaj.util.StackUtil;

import java.util.Arrays;

public class WorkbenchRecipe {
    protected ItemStack[][] inputs;
    protected ItemStack output;

    public WorkbenchRecipe(ItemStack[][] inputs, ItemStack output) {
        this.inputs = inputs;
        this.output = output;

        Arrays.stream(inputs).flatMap(Arrays::stream).forEach(stack -> {
            if (stack.getCount() > 1) {
                stack.setCount(1);
                System.err.println("warning: ItemStack with count > 1 passed to WorkbenchRecipe");
            }
        });
    }

    public boolean matches(ItemStack[][] stacks) {
        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;
        for (int i = 0; i < stacks.length; i++) {
            for (int j = 0; j < stacks[i].length; j++) {
                if (!StackUtil.isValid(stacks[i][j])) {
                    continue;
                }

                minX = Math.min(minX, i);
                minY = Math.min(minY, j);
                maxX = Math.max(maxX, i);
                maxY = Math.max(maxY, i);
            }
        }

        if (maxX - minX != inputs.length || maxY - minY != inputs[0].length) {
            return false;
        }

        for (int i = 0; i < inputs.length; i++) {
            for (int j = 0; j < inputs[i].length; j++) {
                if (!ItemStack.areItemStacksEqual(stacks[minX + i][minY + j], inputs[i][j])) {
                    return false;
                }
            }
        }

        return true;
    }

    public ItemStack[][] getInputs() {
        return this.inputs;
    }

    public ItemStack getOutput() {
        return this.output;
    }

}