package org.sdoaj.eloncraft.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

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

    public static ItemStack copyCount(ItemStack stack, int newCount) {
        ItemStack newStack = stack.copy();
        newStack.setCount(newCount);
        return newStack;
    }

    public static boolean itemStackApplies(ItemStack ingredient, ItemStack actual) {
        if (ingredient == null || actual == null) {
            return false;
        }

        if (!ingredient.isItemEqual(actual) || ingredient.getCount() > actual.getCount()) {
            return false;
        }

        return ingredient.getMetadata() == Util.WILDCARD || ingredient.getMetadata() == actual.getMetadata();
    }

    public static boolean fluidStackApplies(FluidStack ingredient, FluidStack actual) {
        if (ingredient == null || actual == null) {
            return false;
        }

        return actual.containsFluid(ingredient);
    }
}
