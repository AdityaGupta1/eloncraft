package org.sdoaj.items.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Drop {
    private final ItemStack stack;
    private final DropRange range;
    private final double fortuneAdd;

    private Drop(ItemStack stack, DropRange range, double fortuneAdd) {
        this.stack = stack;
        this.range = range;
        this.fortuneAdd = fortuneAdd;
    }

    public Drop(Item item, int min, int max, double fortuneAdd) {
        this(new ItemStack(item), new DropRange(min, max), fortuneAdd);
    }

    public Drop(Block block, int min, int max, double fortuneAdd) {
        this(new ItemStack(block), new DropRange(min, max), fortuneAdd);
    }

    public ItemStack getRandomDrop(int fortune) {
        return new ItemStack(stack.getItem(), range.getRandom(fortune * fortuneAdd));
    }
}
