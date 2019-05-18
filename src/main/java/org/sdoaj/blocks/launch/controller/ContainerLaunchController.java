package org.sdoaj.blocks.launch.controller;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import org.sdoaj.blocks.machines.ContainerBase;
import org.sdoaj.blocks.tileentities.TileEntityBase;

public class ContainerLaunchController extends ContainerBase {
    public final TileEntityLaunchController tileEntity;

    public ContainerLaunchController(InventoryPlayer inventory, TileEntityBase tileEntity) {
        super(tileEntity);

        this.tileEntity = (TileEntityLaunchController) tileEntity;

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