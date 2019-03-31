package org.sdoaj.item.blocks.machines;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import org.sdoaj.item.blocks.tileentities.TileEntityBase;

public abstract class ContainerMachine extends Container {
    private final TileEntityBase tileEntity;

    public ContainerMachine(TileEntityBase tileEntity) {
        this.tileEntity = tileEntity;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return this.tileEntity.canPlayerUse(player);
    }
}
