package org.sdoaj.eloncraft.blocks.machines.generator;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

// generic recipe class for any recipe with one input and one output
public class GeneratorRecipe {
    private final Ingredient input;
    private final int fuel;
    private final int output;

    GeneratorRecipe(Ingredient input, int fuel, int output) {
        this.input = input;
        this.fuel = fuel;
        this.output = output;
    }

    public boolean matches(ItemStack stack) {
        return input.apply(stack);
    }

    public Ingredient getInput() {
        return this.input;
    }

    public int getFuel() {
        return fuel;
    }

    public int getOutput() {
        return this.output;
    }

    public int getEnergyPerFuel() {
        return output / fuel;
    }
}