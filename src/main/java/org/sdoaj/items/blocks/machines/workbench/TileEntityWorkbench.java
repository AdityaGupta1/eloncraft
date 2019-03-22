// based on TileEntityGrinder from Actually Additions

package org.sdoaj.items.blocks.machines.workbench;

import net.minecraft.item.ItemStack;
import org.sdoaj.items.blocks.tileentities.TileEntityInventoryBase;
import org.sdoaj.util.ItemStackHandler;
import org.sdoaj.util.StackUtil;

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

        Optional<ItemStack> output = getOutput();
        if (output.isPresent()) {
            this.inventory.setStackInSlot(SLOT_OUTPUT, output.get());
        } else {
            this.inventory.setStackInSlot(SLOT_OUTPUT, ItemStack.EMPTY);
        }
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

    public Optional<ItemStack> getOutput() {
        WorkbenchRecipe recipe = WorkbenchRecipes.getRecipeFromInput(getInputStacks());

        if (recipe == null) {
            return Optional.empty();
        }

        return Optional.of(recipe.output.copy());
    }

    public void decrementInputs() {
        IntStream.range(SLOT_INPUT_1, INPUT_SLOTS).forEach(i -> StackUtil.shrink(this.inventory.getStackInSlot(i), 1));
    }
}