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

    public static boolean itemStackApplies(ItemStack ingredient, ItemStack actual) {
        if (ingredient == null || actual == null) {
            return false;
        }

        if (!ingredient.isItemEqual(actual) || ingredient.getCount() > actual.getCount()) {
            return false;
        }

        return ingredient.getMetadata() == Util.wildcard || ingredient.getMetadata() == actual.getMetadata();
    }
}
