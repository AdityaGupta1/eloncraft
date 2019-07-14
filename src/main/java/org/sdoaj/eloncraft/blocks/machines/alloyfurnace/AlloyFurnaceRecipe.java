package org.sdoaj.eloncraft.blocks.machines.alloyfurnace;

import net.minecraft.item.ItemStack;
import org.sdoaj.eloncraft.recipes.IngredientStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AlloyFurnaceRecipe {
    private List<IngredientStack> inputs;
    private ItemStack output;

    public AlloyFurnaceRecipe(List<IngredientStack> inputs, ItemStack output) {
        this.inputs = inputs;
        this.output = output;

        if (inputs.stream().mapToInt(IngredientStack::getCount).sum() != TileEntityAlloyFurnace.INPUT_SLOTS) {
            throw new IllegalArgumentException("wrong amount of inputs in AlloyFurnaceRecipe");
        }
    }

    public AlloyFurnaceRecipe(IngredientStack[] inputs, ItemStack output) {
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
        stacks = mergeStacks(stacks.stream().map(ItemStack::copy).peek(stack -> stack.setCount(1)).collect(Collectors.toList()));
        List<IngredientStack> ingredients = inputs.stream().map(IngredientStack::copy).collect(Collectors.toList());

        for (IngredientStack ingredient : ingredients) {
            for (ItemStack actual : stacks) {
                if (ingredient.applyIgnoreCount(actual)) {
                    ingredient.shrink(actual.getCount());
                }
            }
        }

        for (IngredientStack ingredient : ingredients) {
            if (ingredient.getCount() != 0) {
                return false;
            }
        }

        return true;
    }

    public List<IngredientStack> getInputs() {
        return this.inputs.stream().map(IngredientStack::copy).collect(Collectors.toList());
    }

    public ItemStack getOutput() {
        return this.output.copy();
    }
}