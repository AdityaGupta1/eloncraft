package org.sdoaj.items.blocks.machines;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * maps a one-character key to an ItemStack - good for crafting table recipes
 * TODO use Ingredient instead of ItemStack
 */
public class RecipeKey {
    private final char key;
    private final ItemStack stack;

    public RecipeKey(char key, ItemStack stack) {
        this.key = key;
        this.stack = stack;
    }

    public RecipeKey(char key, Item item) {
        this(key, new ItemStack(item));
    }

    public RecipeKey(char key, Block block) {
        this(key, new ItemStack(block));
    }

    public boolean matches(char key) {
        return this.key == key;
    }

    public ItemStack get() {
        return stack.copy();
    }
}
