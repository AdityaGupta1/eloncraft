package org.sdoaj.eloncraft.blocks.machines.refinery;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import org.sdoaj.eloncraft.blocks.tileentities.ContainerBase;
import org.sdoaj.eloncraft.blocks.tileentities.TileEntityBase;

public class ContainerRefinery extends ContainerBase {
    public final TileEntityRefinery tileEntity;

    public ContainerRefinery(InventoryPlayer inventory, TileEntityBase tileEntity) {
        super(tileEntity);

        this.tileEntity = (TileEntityRefinery) tileEntity;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, this.tileEntity.guiTopHeight + 4 + i * 18));
            }
        }
        for (int i = 0; i < 9; i++) {
            this.addSlotToContainer(new Slot(inventory, i, 8 + i * 18, this.tileEntity.guiTopHeight + 62));
        }
    }
}