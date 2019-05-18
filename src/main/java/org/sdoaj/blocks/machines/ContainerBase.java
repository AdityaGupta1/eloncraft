package org.sdoaj.blocks.machines;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import org.sdoaj.blocks.tileentities.TileEntityBase;

public abstract class ContainerBase extends Container {
    private final TileEntityBase tileEntity;

    public ContainerBase(TileEntityBase tileEntity) {
        this.tileEntity = tileEntity;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return this.tileEntity.canPlayerUse(player);
    }

    public TileEntityBase getTileEntity() {
        return tileEntity;
    }
}
