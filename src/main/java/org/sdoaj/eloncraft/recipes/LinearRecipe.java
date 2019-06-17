package org.sdoaj.eloncraft.recipes;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.oredict.OreIngredient;

// generic recipe class for any recipe with one input and one output
public class LinearRecipe {
    private IngredientStack input;
    private ItemStack output;

    public LinearRecipe setInput(IngredientStack stack) {
        this.input = stack;
        return this;
    }

    public LinearRecipe setInput(Ingredient input, int count) {
        return setInput(new IngredientStack(input, count));
    }

    public LinearRecipe setInput(ItemStack stack) {
        return setInput(new IngredientStack(stack));
    }

    public LinearRecipe setInput(Item item) {
        return setInput(new ItemStack(item));
    }

    public LinearRecipe setInput(Block block) {
        return setInput(new ItemStack(block));
    }

    public LinearRecipe setInput(String oreDictName, int count) {
        return setInput(new OreIngredient(oreDictName), count);
    }

    public LinearRecipe setOutput(ItemStack stack) {
        this.output = stack;
        return this;
    }

    public LinearRecipe setOutput(Item item) {
        return setOutput(new ItemStack(item));
    }

    public LinearRecipe setOutput(Block block) {
        return setOutput(new ItemStack(block));
    }

    public boolean matches(ItemStack stack) {
        return input.apply(stack);
    }

    public IngredientStack getInput() {
        return this.input;
    }

    public ItemStack getOutput() {
        return this.output;
    }
}