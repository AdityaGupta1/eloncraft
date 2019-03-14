package org.sdoaj.items;

import net.minecraft.item.ItemStack;

public class Drop {
    private final ItemStack stack;
    private final DropRange range;
    private final double fortuneAdd;

    public Drop(ItemStack stack, DropRange range, double fortuneAdd) {
        this.stack = stack;
        this.range = range;
        this.fortuneAdd = fortuneAdd;
    }

    public ItemStack getRandomDrop(int fortune) {
        return new ItemStack(stack.getItem(), (int) range.getRandom(fortune * fortuneAdd));
    }
}
