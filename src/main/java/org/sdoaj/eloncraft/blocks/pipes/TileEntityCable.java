package org.sdoaj.eloncraft.blocks.pipes;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import org.sdoaj.eloncraft.blocks.tileentities.CustomEnergyStorage;
import org.sdoaj.eloncraft.blocks.tileentities.ISharingEnergyProvider;
import org.sdoaj.eloncraft.blocks.tileentities.TileEntityBase;

public class TileEntityCable extends TileEntityBase implements ISharingEnergyProvider {
    private final CustomEnergyStorage energyStorage = new CustomEnergyStorage(1000, 1000, 1000);

    public TileEntityCable() {
        super("cable");
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type) {
        super.writeSyncableNBT(compound, type);
        energyStorage.writeToNBT(compound);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type) {
        super.readSyncableNBT(compound, type);
        energyStorage.readFromNBT(compound);
    }

    @Override
    public int getEnergyToSplitShare() {
        return energyStorage.getEnergyStored();
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) {
            return (T) energyStorage;
        }

        return super.getCapability(capability, facing);
    }
}
