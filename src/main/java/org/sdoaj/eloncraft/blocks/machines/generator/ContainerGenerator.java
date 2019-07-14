// based on ContainerGrinder from Actually Additions

package org.sdoaj.eloncraft.blocks.machines.generator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import org.sdoaj.eloncraft.blocks.gui.slot.SlotItemHandlerUnconditioned;
import org.sdoaj.eloncraft.blocks.gui.slot.SlotOutput;
import org.sdoaj.eloncraft.blocks.tileentities.ContainerBase;
import org.sdoaj.eloncraft.blocks.tileentities.TileEntityBase;
import org.sdoaj.eloncraft.util.StackUtil;

public class ContainerGenerator extends ContainerBase {
    public final TileEntityGenerator tileEntity;

    public ContainerGenerator(InventoryPlayer inventory, TileEntityBase tileEntity) {
        super(tileEntity);
        this.tileEntity = (TileEntityGenerator) tileEntity;

        this.addSlotToContainer(new SlotItemHandlerUnconditioned(this.tileEntity.inventory, TileEntityGenerator.SLOT_INPUT, 68, 50)
                .setOnSlotChanged((slot, stack) -> this.tileEntity.addFuelIfHasRecipe()));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, this.tileEntity.guiTopHeight + 4 + i * 18));
            }
        }
        for (int i = 0; i < 9; i++) {
            this.addSlotToContainer(new Slot(inventory, i, 8 + i * 18, this.tileEntity.guiTopHeight + 62));
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotId) {
        int inventoryStart = TileEntityGenerator.SLOT_INPUT + 1;
        int inventoryEnd = inventoryStart + 26;
        int hotbarStart = inventoryEnd + 1;
        int hotbarEnd = hotbarStart + 8;

        Slot slot = this.inventorySlots.get(slotId);

        if (slot != null && slot.getHasStack()) {
            ItemStack newStack = slot.getStack();
            ItemStack currentStack = newStack.copy();

            if (slotId == TileEntityGenerator.SLOT_INPUT) {
                if (!this.mergeItemStack(newStack, inventoryStart, hotbarEnd + 1, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(newStack, currentStack);
            } else {
                if (GeneratorRecipes.getRecipeFromInput(newStack) != null) {
                    if (!this.mergeItemStack(newStack, TileEntityGenerator.SLOT_INPUT, TileEntityGenerator.SLOT_INPUT + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (slotId <= inventoryEnd) {
                    if (!this.mergeItemStack(newStack, hotbarStart, hotbarEnd + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (slotId < hotbarEnd + 1 && !this.mergeItemStack(newStack, inventoryStart, inventoryEnd + 1, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (!StackUtil.isValid(newStack)) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (newStack.getCount() == currentStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(player, newStack);

            return currentStack;
        }
        return ItemStack.EMPTY;
    }
}