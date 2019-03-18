// based on ContainerGrinder from Actually Additions

package org.sdoaj.items.blocks.machines.metalroller;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import org.sdoaj.items.blocks.machines.gui.slot.SlotItemHandlerUnconditioned;
import org.sdoaj.items.blocks.machines.gui.slot.SlotOutput;
import org.sdoaj.items.blocks.machines.TileEntityBase;
import org.sdoaj.util.StackUtil;

public class ContainerMetalRoller extends Container {
    public final TileEntityMetalRoller tileEntity;

    public ContainerMetalRoller(InventoryPlayer inventory, TileEntityBase tileEntity) {
        this.tileEntity = (TileEntityMetalRoller) tileEntity;

        this.addSlotToContainer(new SlotItemHandlerUnconditioned(this.tileEntity.inv, TileEntityMetalRoller.SLOT_INPUT, 47, 35));
        this.addSlotToContainer(new SlotOutput(this.tileEntity.inv, TileEntityMetalRoller.SLOT_OUTPUT, 107, 35));

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
        int inventoryStart = 2;
        int inventoryEnd = inventoryStart + 26;
        int hotbarStart = inventoryEnd + 1;
        int hotbarEnd = hotbarStart + 8;

        Slot slot = this.inventorySlots.get(slotId);

        if (slot != null && slot.getHasStack()) {
            ItemStack newStack = slot.getStack();
            ItemStack currentStack = newStack.copy();

            // slots in inventory to shift from
            if (slotId == TileEntityMetalRoller.SLOT_OUTPUT) {
                if (!this.mergeItemStack(newStack, inventoryStart, hotbarEnd + 1, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(newStack, currentStack);
            }
            // exclude other slots in inventory
            else if (slotId >= inventoryStart) {
                // shift from inventory
                if (MetalRollerRecipes.getRecipeFromInput(newStack) != null) {
                    if (!this.mergeItemStack(newStack, TileEntityMetalRoller.SLOT_INPUT, TileEntityMetalRoller.SLOT_INPUT + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                }

                else if (slotId >= inventoryStart && slotId <= inventoryEnd) {
                    if (!this.mergeItemStack(newStack, hotbarStart, hotbarEnd + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (slotId >= inventoryEnd + 1 && slotId < hotbarEnd + 1 && !this.mergeItemStack(newStack, inventoryStart, inventoryEnd + 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(newStack, inventoryStart, hotbarEnd + 1, false)) {
                return ItemStack.EMPTY;
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

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return this.tileEntity.canPlayerUse(player);
    }
}