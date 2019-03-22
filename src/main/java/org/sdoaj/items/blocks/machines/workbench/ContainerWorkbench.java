// based on ContainerGrinder from Actually Additions

package org.sdoaj.items.blocks.machines.workbench;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.sdoaj.items.blocks.gui.slot.SlotItemHandlerUnconditioned;
import org.sdoaj.items.blocks.gui.slot.SlotOutput;
import org.sdoaj.items.blocks.machines.ContainerMachine;
import org.sdoaj.items.blocks.tileentities.TileEntityBase;
import org.sdoaj.util.StackUtil;

public class ContainerWorkbench extends ContainerMachine {
    public final TileEntityWorkbench tileEntity;

    public ContainerWorkbench(InventoryPlayer inventory, TileEntityBase tileEntity) {
        super(tileEntity);
        this.tileEntity = (TileEntityWorkbench) tileEntity;

        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 13; j++) {
                this.addSlotToContainer(new SlotItemHandlerUnconditioned(this.tileEntity.inventory,
                        TileEntityWorkbench.SLOT_INPUT_1 + 13 * i + j, 9 + 18 * i, 9 + 18 * j));
            }
        }
        this.addSlotToContainer(new SlotOutput(this.tileEntity.inventory, TileEntityWorkbench.SLOT_OUTPUT, 207, 291));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 262 + i * 18));
            }
        }
        for (int i = 0; i < 9; i++) {
            this.addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 320));
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotId) {
        int inventoryStart = TileEntityWorkbench.SLOT_OUTPUT + 1;
        int inventoryEnd = inventoryStart + 26;
        int hotbarStart = inventoryEnd + 1;
        int hotbarEnd = hotbarStart + 8;

        Slot slot = this.inventorySlots.get(slotId);

        if (slot != null && slot.getHasStack()) {
            ItemStack newStack = slot.getStack();
            ItemStack currentStack = newStack.copy();

            // purposely leaving out shift-clicking into input slots (similar to crafting table)
            if (slotId < inventoryStart) {
                if (slotId == TileEntityWorkbench.SLOT_OUTPUT) {
                    // TODO shift click crafting outputs
                    return ItemStack.EMPTY;
                }

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