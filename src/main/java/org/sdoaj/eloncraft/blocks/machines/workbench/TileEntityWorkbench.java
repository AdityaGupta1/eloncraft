// based on TileEntityGrinder from Actually Additions

package org.sdoaj.eloncraft.blocks.machines.workbench;

import net.minecraft.item.ItemStack;
import org.sdoaj.eloncraft.blocks.tileentities.TileEntityInventoryBase;
import org.sdoaj.eloncraft.util.ItemStackHandler;
import org.sdoaj.eloncraft.util.StackUtil;

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

    public Optional<ItemStack> getOutput() {
        WorkbenchRecipe recipe = WorkbenchRecipes.getRecipeFromInput(getInputStacks());

        if (recipe == null) {
            return Optional.empty();
        }

        return Optional.of(recipe.getOutput().copy());
    }

    public boolean finishCraftingIfPossible() {
        if (getOutput().isPresent()) {
            decrementInputs();
            return true;
        } else {
            return false;
        }
    }

    // TODO make this work with non-consumed ingredients (e.g. lava bucket turns into regular bucket)
    private void decrementInputs() {
        IntStream.range(SLOT_INPUT_1, INPUT_SLOTS).forEach(i -> {
            ItemStack stack = this.inventory.getStackInSlot(i);
            if (StackUtil.isValid(stack)) {
                if (stack.getItem().getContainerItem() != null) {

                }
                stack.shrink(1);
            }
        });
    }
}