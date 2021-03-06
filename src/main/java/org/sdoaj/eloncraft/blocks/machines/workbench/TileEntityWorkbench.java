// based on TileEntityGrinder from Actually Additions

package org.sdoaj.eloncraft.blocks.machines.workbench;

import net.minecraft.item.ItemStack;
import org.sdoaj.eloncraft.blocks.tileentities.TileEntityInventoryBase;
import org.sdoaj.eloncraft.util.ItemStackHandler;
import org.sdoaj.eloncraft.util.StackUtil;
import scala.tools.cmd.Opt;

import java.util.Optional;
import java.util.stream.IntStream;

public class TileEntityWorkbench extends TileEntityInventoryBase {
    public static final int SLOT_INPUT_1 = 0;
    public static final int INPUT_SLOTS = 13 * 13;
    public static final int SLOT_OUTPUT = SLOT_INPUT_1 + INPUT_SLOTS;

    public TileEntityWorkbench() {
        super("elon_workbench", 13 * 13 + 1);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();

        if (this.world.isRemote) {
            return;
        }

        this.inventory.setStackInSlot(SLOT_OUTPUT, getOutput().orElse(ItemStack.EMPTY));
    }

    @Override
    public ItemStackHandler.IRemover getRemover() {
        return (slot, automation) -> !automation || slot == SLOT_OUTPUT;
    }

    private ItemStack[][] getInputStacks() {
        ItemStack[][] stacks = new ItemStack[13][13];

        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 13; j++) {
                stacks[i][j] = this.inventory.getStackInSlot(SLOT_INPUT_1 + 13 * i + j);
            }
        }

        return stacks;
    }

    private ItemStack[][] shiftClickInputs = null;
    private ItemStack shiftClickOutput = null;

    void prepareShiftClick() {
        ItemStack[][] inputs = getInputStacks();
        shiftClickInputs = new ItemStack[13][13];

        for (int x = 0; x < 13; x++) {
            for (int y = 0; y < 13; y++) {
                shiftClickInputs[x][y] = inputs[x][y].copy();
            }
        }

        shiftClickOutput = getRecipeOutput().orElse(null);
    }

    void stopShiftClick() {
        shiftClickInputs = null;
        shiftClickOutput = null;
    }

    public Optional<ItemStack> getOutput() {
        if (shiftClickInputs != null) {
            if (shiftClickOutput == null) {
                return Optional.empty();
            }

            ItemStack[][] inputs = getInputStacks();

            for (int x = 0; x < 13; x++) {
                for (int y = 0; y < 13; y++) {
                    if (inputs[x][y].getItem() != shiftClickInputs[x][y].getItem()) {
                        return Optional.empty();
                    }
                }
            }

            return Optional.of(shiftClickOutput.copy());
        }

       return getRecipeOutput();
    }

    private Optional<ItemStack> getRecipeOutput() {
        WorkbenchRecipe recipe = WorkbenchRecipes.getRecipeFromInput(getInputStacks(), this.world);

        if (recipe == null) {
            return Optional.empty();
        }

        return Optional.of(recipe.getOutput().copy());
    }

    void finishCraftingIfPossible() {
        if (getOutput().isPresent()) {
            decrementInputs();
        }
    }

    private void decrementInputs() {
        IntStream.range(SLOT_INPUT_1, INPUT_SLOTS).forEach(i -> {
            ItemStack stack = this.inventory.getStackInSlot(i);
            if (StackUtil.isValid(stack)) {
                if (stack.getItem().hasContainerItem(stack)) {
                    this.inventory.setStackInSlot(i, stack.getItem().getContainerItem(stack));
                } else {
                    stack.shrink(1);
                }
            }
        });
    }
}