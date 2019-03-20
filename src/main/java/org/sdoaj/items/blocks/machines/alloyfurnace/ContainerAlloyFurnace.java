// based on ContainerGrinder from Actually Additions

package org.sdoaj.items.blocks.machines.alloyfurnace;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import org.sdoaj.items.blocks.gui.slot.SlotItemHandlerUnconditioned;
import org.sdoaj.items.blocks.gui.slot.SlotOutput;
import org.sdoaj.items.blocks.machines.ContainerMachine;
import org.sdoaj.items.blocks.tileentities.TileEntityBase;
import org.sdoaj.util.StackUtil;

public class ContainerAlloyFurnace extends ContainerMachine {
    public final TileEntityAlloyFurnace tileEntity;

    public ContainerAlloyFurnace(InventoryPlayer inventory, TileEntityBase tileEntity) {
        super(tileEntity);
        this.tileEntity = (TileEntityAlloyFurnace) tileEntity;

        int[][] slots = {
                {10, 10}, {10, 33}, {10, 56}, {34, 22}, {34, 45}, {126, 22}, {126, 45}, {150, 10}, {150, 33}, {150, 56}
        };

        for (int i = 0; i < TileEntityAlloyFurnace.INPUT_SLOTS - TileEntityAlloyFurnace.SLOT_INPUT_1; i++) {
            this.addSlotToContainer(new SlotItemHandlerUnconditioned(this.tileEntity.inventory, i, slots[i][0], slots[i][1], 1));
        }
        this.addSlotToContainer(new SlotOutput(this.tileEntity.inventory, TileEntityAlloyFurnace.SLOT_OUTPUT, 80, 18));

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
        int inventoryStart = TileEntityAlloyFurnace.SLOT_OUTPUT + 1;
        int inventoryEnd = inventoryStart + 26;
        int hotbarStart = inventoryEnd + 1;
        int hotbarEnd = hotbarStart + 8;

        Slot slot = this.inventorySlots.get(slotId);

        if (slot != null && slot.getHasStack()) {
            ItemStack newStack = slot.getStack();
            ItemStack currentStack = newStack.copy();

            // purposely leaving out shift-clicking into input slots (similar to crafting table)
            if (slotId < inventoryStart) {
                if (!this.mergeItemStack(newStack, inventoryStart, hotbarEnd + 1, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(newStack, currentStack);
            } else {
                if (slotId <= inventoryEnd) {
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