// based on CrusherRecipe from Actually Additions

package org.sdoaj.items.blocks.machines.metalroller;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public class MetalRollerRecipe {
    protected Ingredient input;
    protected ItemStack output;

    public MetalRollerRecipe(Ingredient input, ItemStack output) {
        this.input = input;
        this.output = output;
    }

    public MetalRollerRecipe(Item input, Item output) {
        this(Ingredient.fromItem(input), new ItemStack(output));
    }

    public boolean matches(ItemStack stack) {
        return this.input.apply(stack);
    }

    public Ingredient getInput() {
        return this.input;
    }

    public ItemStack getOutput() {
        return this.output;
    }

}