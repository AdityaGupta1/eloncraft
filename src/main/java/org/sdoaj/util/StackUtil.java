package org.sdoaj.util;

import net.minecraft.item.ItemStack;

public class StackUtil {
    public static boolean isValid(ItemStack stack) {
        return stack != null && !stack.isEmpty();
    }

    public static ItemStack grow(ItemStack stack, int amount) {
        stack.grow(amount);
        return stack;
    }

    public static ItemStack shrink(ItemStack stack, int amount) {
        stack.shrink(amount);
        return stack;
    }
}
