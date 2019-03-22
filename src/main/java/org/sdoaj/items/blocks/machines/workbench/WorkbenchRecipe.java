package org.sdoaj.items.blocks.machines.workbench;

import net.minecraft.item.ItemStack;
import org.sdoaj.items.blocks.machines.RecipeKey;
import org.sdoaj.util.StackUtil;

import java.util.Arrays;

public class WorkbenchRecipe {
    private ItemStack[][] inputs;
    private ItemStack output;

    // rotates grid clockwise and makes a copy of each input stack
    private ItemStack[][] rotate(ItemStack[][] stacks) {
        ItemStack[][] output = new ItemStack[stacks[0].length][stacks.length];
        for (int i = 0; i < stacks[0].length; i++) {
            for (int j = stacks.length - 1; j >= 0; j--) {
                output[i][j] = stacks[j][i].copy();
            }
        }
        return output;
    }

    private void checkInputs() {
        Arrays.stream(inputs).flatMap(Arrays::stream).forEach(stack -> {
            if (stack.getCount() > 1) {
                stack.setCount(1);
                System.err.println("warning: ItemStack with count > 1 passed to WorkbenchRecipe");
            }
        });
    }

    public WorkbenchRecipe(ItemStack[][] inputs, ItemStack output) {
        this.inputs = rotate(inputs);
        this.output = output.copy();
        checkInputs();
    }

    public WorkbenchRecipe(String[] grid, ItemStack output, RecipeKey... keys) {
        int maxLength = Arrays.stream(grid).mapToInt(String::length).max().getAsInt();

        ItemStack[][] stacks = new ItemStack[maxLength][grid.length];

        for (int i = 0; i < grid.length; i++) {
            String row = grid[i];

            for (int j = 0; j < maxLength; j++) {
                ItemStack stack = ItemStack.EMPTY;

                if (j < row.length()) {
                    char c = row.charAt(j);

                    if (c != ' ') {
                        for (RecipeKey key : keys) {
                            if (key.matches(c)) {
                                stack = key.get();
                            }
                        }

                        if (!StackUtil.isValid(stack)) {
                            throw new IllegalArgumentException("missing recipe key for character: " + c);
                        }
                    }
                }

                stacks[j][i] = stack;
            }
        }

        this.inputs = stacks;
        this.output = output.copy();
        checkInputs();
    }

    public boolean matches(ItemStack[][] stacks) {
        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;
        boolean valid = false;
        for (int i = 0; i < stacks.length; i++) {
            for (int j = 0; j < stacks[i].length; j++) {
                if (!StackUtil.isValid(stacks[i][j])) {
                    continue;
                }

                valid = true;

                minX = Math.min(minX, i);
                maxX = Math.max(maxX, i);
                minY = Math.min(minY, j);
                maxY = Math.max(maxY, j);
            }
        }

        if (!valid) {
            return false;
        }

        if (maxX - minX + 1 != inputs.length || maxY - minY + 1 != inputs[0].length) {
            return false;
        }

        for (int i = 0; i < inputs.length; i++) {
            for (int j = 0; j < inputs[i].length; j++) {
                if (!StackUtil.areItemsEqual(stacks[minX + i][minY + j], inputs[i][j])) {
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