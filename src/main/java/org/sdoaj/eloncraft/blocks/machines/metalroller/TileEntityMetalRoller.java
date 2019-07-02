// based on TileEntityGrinder from Actually Additions

package org.sdoaj.eloncraft.blocks.machines.metalroller;

import net.minecraft.item.ItemStack;
import org.sdoaj.eloncraft.blocks.machines.BlockMachine;
import org.sdoaj.eloncraft.blocks.tileentities.TileEntityInventoryMachine;
import org.sdoaj.eloncraft.blocks.tileentities.CustomEnergyStorage;
import org.sdoaj.eloncraft.blocks.machines.LinearRecipe;
import org.sdoaj.eloncraft.util.ItemStackHandler;
import org.sdoaj.eloncraft.util.StackUtil;
import org.sdoaj.eloncraft.util.Util;

public class TileEntityMetalRoller extends TileEntityInventoryMachine {
    public static final int SLOT_INPUT = 0;
    public static final int SLOT_OUTPUT = 1;

    final int guiTopHeight = 79;

    public TileEntityMetalRoller() {
        super("metal_roller", 2, 120, 5000,
                new CustomEnergyStorage(100000, 100000, 0), BlockMachine.IS_ON);
    }

    @Override
    public ItemStackHandler.IAcceptor getAcceptor() {
        return (slot, stack, automation) -> !automation || slot == SLOT_INPUT && MetalRollerRecipes.getRecipeFromInput(stack) != null;
    }

    @Override
    public ItemStackHandler.IRemover getRemover() {
        return (slot, automation) -> !automation || slot == SLOT_OUTPUT;
    }

    @Override
    public boolean canProcess() {
        if (StackUtil.isValid(this.inventory.getStackInSlot(SLOT_INPUT))) {
            LinearRecipe recipe = MetalRollerRecipes.getRecipeFromInput(this.inventory.getStackInSlot(SLOT_INPUT));
            if (recipe == null) {
                return false;
            }

            ItemStack outputStack = recipe.getOutput();
            if (StackUtil.isValid(outputStack)) {
                if (outputStack.getItemDamage() == Util.wildcard) {
                    outputStack.setItemDamage(0);
                }
                return (!StackUtil.isValid(this.inventory.getStackInSlot(SLOT_OUTPUT))
                        || this.inventory.getStackInSlot(SLOT_OUTPUT).isItemEqual(outputStack)
                        && this.inventory.getStackInSlot(SLOT_OUTPUT).getCount() <= this.inventory.getStackInSlot(SLOT_OUTPUT).getMaxStackSize() - outputStack.getCount());
            }
        }

        return false;
    }

    @Override
    public void finishProcessing() {
        LinearRecipe recipe = MetalRollerRecipes.getRecipeFromInput(this.inventory.getStackInSlot(SLOT_INPUT));
        if (recipe == null) {
            return;
        }

        ItemStack outputStack = recipe.getOutput();
        if (StackUtil.isValid(outputStack)) {
            if (outputStack.getItemDamage() == Util.wildcard) {
                outputStack.setItemDamage(0);
            }

            if (!StackUtil.isValid(this.inventory.getStackInSlot(SLOT_OUTPUT))) {
                this.inventory.setStackInSlot(SLOT_OUTPUT, outputStack.copy());
            } else if (this.inventory.getStackInSlot(SLOT_OUTPUT).getItem() == outputStack.getItem()) {
                this.inventory.setStackInSlot(SLOT_OUTPUT, StackUtil.grow(this.inventory.getStackInSlot(SLOT_OUTPUT), outputStack.getCount()));
            }
        }

        this.inventory.getStackInSlot(SLOT_INPUT).shrink(recipe.getInput().getCount());
    }
}