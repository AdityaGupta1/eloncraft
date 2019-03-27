package org.sdoaj.items.blocks.machines.alloyfurnace;

import net.minecraft.item.ItemStack;
import org.sdoaj.util.StackUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AlloyFurnaceRecipe {
    private List<ItemStack> inputs;
    private ItemStack output;

    public AlloyFurnaceRecipe(List<ItemStack> inputs, ItemStack output) {
        this.inputs = inputs;
        this.output = output;

        if (inputs.stream().mapToInt(ItemStack::getCount).sum() != TileEntityAlloyFurnace.INPUT_SLOTS - TileEntityAlloyFurnace.SLOT_INPUT_1) {
            throw new IllegalArgumentException("wrong amount of inputs in AlloyFurnaceRecipe");
        }
    }

    public AlloyFurnaceRecipe(ItemStack[] inputs, ItemStack output) {
        this(Arrays.asList(inputs), output);
    }

    private List<ItemStack> mergeStacks(List<ItemStack> stacks) {
        List<ItemStack> merged = new ArrayList<>();

        outer:
        for (ItemStack stack : stacks) {
            for (ItemStack mergedStack : merged) {
                if (mergedStack.isItemEqual(stack)) {
                    mergedStack.grow(stack.getCount());
                    continue outer;
                }
            }

            merged.add(stack.copy());
        }

        return merged;
    }

    public boolean matches(List<ItemStack> stacks) {
        stacks = mergeStacks(stacks);

        for (ItemStack ingredient : inputs) {
            boolean matches = false;

            for (ItemStack actual : stacks) {
                if (StackUtil.itemStackApplies(ingredient, actual)) {
                    matches = true;
                }
            }

            if (!matches) {
                return false;
            }
        }

        return true;
    }

    public List<ItemStack> getInputs() {
        return this.inputs;
    }

    public ItemStack getOutput() {
        return this.output;
    }
}