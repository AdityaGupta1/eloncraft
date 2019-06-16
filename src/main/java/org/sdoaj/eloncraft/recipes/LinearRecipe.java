package org.sdoaj.eloncraft.recipes;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.oredict.OreIngredient;

// generic recipe class for any recipe with one input and one output
public class LinearRecipe {
    private Ingredient input;
    private int inputCount;
    private ItemStack output;

    public LinearRecipe setInput(Ingredient input, int count) {
        this.input = input;
        this.inputCount = count;
        return this;
    }

    public LinearRecipe setInput(ItemStack stack) {
        this.input = Ingredient.fromStacks(stack);
        this.inputCount = stack.getCount();
        return this;
    }

    public LinearRecipe setInput(Item item) {
        setInput(new ItemStack(item));
        return this;
    }

    public LinearRecipe setInput(Block block) {
        setInput(new ItemStack(block));
        return this;
    }

    public LinearRecipe setInput(String oreDictName, int count) {
        setInput(new OreIngredient(oreDictName), count);
        return this;
    }

    public LinearRecipe setOutput(ItemStack stack) {
        this.output = stack;
        return this;
    }

    public LinearRecipe setOutput(Item item) {
        this.output = new ItemStack(item);
        return this;
    }

    public LinearRecipe setOutput(Block block) {
        this.output = new ItemStack(block);
        return this;
    }

    public boolean matches(ItemStack stack) {
        return input.apply(stack);
    }

    public Ingredient getInput() {
        return this.input;
    }

    public int getInputCount() {
        return inputCount;
    }

    public ItemStack getOutput() {
        return this.output;
    }
}