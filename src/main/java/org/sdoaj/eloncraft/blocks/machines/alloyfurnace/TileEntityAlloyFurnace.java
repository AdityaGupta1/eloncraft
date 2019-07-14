// based on TileEntityGrinder from Actually Additions

package org.sdoaj.eloncraft.blocks.machines.alloyfurnace;

import net.minecraft.item.ItemStack;
import org.sdoaj.eloncraft.blocks.machines.BlockMachine;
import org.sdoaj.eloncraft.blocks.tileentities.TileEntityInventoryMachine;
import org.sdoaj.eloncraft.blocks.tileentities.CustomEnergyStorage;
import org.sdoaj.eloncraft.util.ItemStackHandler;
import org.sdoaj.eloncraft.util.StackUtil;
import org.sdoaj.eloncraft.util.Util;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TileEntityAlloyFurnace extends TileEntityInventoryMachine {
    public static final int SLOT_INPUT_1 = 0;
    public static final int INPUT_SLOTS = 10;
    public static final int SLOT_OUTPUT = SLOT_INPUT_1 + INPUT_SLOTS;

    final int guiTopHeight = 79;

    public TileEntityAlloyFurnace() {
        super("alloy_furnace", 11, 600, 10000,
                new CustomEnergyStorage(100000, 1000, 0), BlockMachine.IS_ON);
    }

    @Override
    public ItemStackHandler.IAcceptor getAcceptor() {
        return (slot, stack, automation) -> !automation || (slot >= SLOT_INPUT_1 && slot < SLOT_INPUT_1 + INPUT_SLOTS);
    }

    @Override
    public ItemStackHandler.IRemover getRemover() {
        return (slot, automation) -> !automation || slot == SLOT_OUTPUT;
    }

    private List<ItemStack> getInputStacks() {
        return IntStream.range(SLOT_INPUT_1, SLOT_INPUT_1 + INPUT_SLOTS).mapToObj(this.inventory::getStackInSlot).collect(Collectors.toList());
    }

    private List<ItemStack> currentInputs;

    @Override
    public void updateEntity() {
        super.updateEntity();

        this.currentInputs = this.getInputStacks();
    }

    public void reset(int slot, ItemStack newStack) {
        ItemStack currentStack = currentInputs.get(slot);

        if (currentStack == null && newStack == null) {
            return;
        }

        if (currentStack == null || newStack == null) {
            this.reset();
        } else {
            if (!newStack.isItemEqual(currentStack)) {
                this.reset();
            }
        }
    }

    @Override
    public boolean canProcess() {
        boolean valid = true;

        for (ItemStack stack : getInputStacks()) {
            if (!StackUtil.isValid(stack)) {
                valid = false;
            }
        }

        if (valid) {
            AlloyFurnaceRecipe recipe = AlloyFurnaceRecipes.getRecipeFromInput(getInputStacks());
            if (recipe == null) {
                return false;
            }

            ItemStack outputStack = recipe.getOutput();
            if (StackUtil.isValid(outputStack)) {
                if (outputStack.getItemDamage() == Util.WILDCARD) {
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
        AlloyFurnaceRecipe recipe = AlloyFurnaceRecipes.getRecipeFromInput(getInputStacks());
        if (recipe == null) {
            return;
        }

        ItemStack outputStack = recipe.getOutput();
        if (StackUtil.isValid(outputStack)) {
            if (outputStack.getItemDamage() == Util.WILDCARD) {
                outputStack.setItemDamage(0);
            }

            if (!StackUtil.isValid(this.inventory.getStackInSlot(SLOT_OUTPUT))) {
                this.inventory.setStackInSlot(SLOT_OUTPUT, outputStack.copy());
            } else if (this.inventory.getStackInSlot(SLOT_OUTPUT).getItem() == outputStack.getItem()) {
                this.inventory.setStackInSlot(SLOT_OUTPUT, StackUtil.grow(this.inventory.getStackInSlot(SLOT_OUTPUT), outputStack.getCount()));
            }
        }

        getInputStacks().forEach(stack -> stack.shrink(1));
    }
}