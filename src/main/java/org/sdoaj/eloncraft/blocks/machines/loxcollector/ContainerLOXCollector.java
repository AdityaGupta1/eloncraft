package org.sdoaj.eloncraft.blocks.machines.loxcollector;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import org.sdoaj.eloncraft.blocks.tileentities.ContainerBase;
import org.sdoaj.eloncraft.blocks.tileentities.TileEntityBase;

public class ContainerLOXCollector extends ContainerBase {
    public final TileEntityLOXCollector tileEntity;

    public ContainerLOXCollector(InventoryPlayer inventory, TileEntityBase tileEntity) {
        super(tileEntity);

        this.tileEntity = (TileEntityLOXCollector) tileEntity;

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