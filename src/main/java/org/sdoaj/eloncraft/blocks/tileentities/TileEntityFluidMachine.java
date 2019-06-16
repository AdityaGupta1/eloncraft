package org.sdoaj.eloncraft.blocks.tileentities;

import net.minecraft.block.properties.PropertyBool;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;

import java.util.Map;

public abstract class TileEntityFluidMachine extends TileEntityInventoryMachine {
    private Map<FluidTank, EnumFacing[]> fluidTanks;

    public TileEntityFluidMachine(String name, int slots, int maxProcessTime, int energyPerOperation,
                                  CustomEnergyStorage energyStorage, PropertyBool on) {
        super(name, slots, maxProcessTime, energyPerOperation, energyStorage, on);
    }

    protected void setFluidTanks(Map<FluidTank, EnumFacing[]> fluidTanks) {
        this.fluidTanks = fluidTanks;
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type) {
        super.writeSyncableNBT(compound, type);
        if (this.fluidTanks != null) {
            this.fluidTanks.keySet().forEach(tank -> tank.writeToNBT(compound));
        }
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type) {
        super.readSyncableNBT(compound, type);
        if (this.fluidTanks != null) {
            this.fluidTanks.keySet().forEach(tank -> tank.readFromNBT(compound));
        }
    }

    @Override
    public IFluidHandler getFluidHandler(EnumFacing facing) {
        return getTank(facing);
    }

    private FluidTank getTank(EnumFacing facing) {
        for (FluidTank tank : fluidTanks.keySet()) {
            EnumFacing[] sides = fluidTanks.get(tank);

            if (sides == null) {
                return tank;
            }

            for (EnumFacing side : sides) {
                if (facing == side) {
                    return tank;
                }
            }
        }

        return null;
    }

    protected abstract void onUseBucket(EntityPlayer player, EnumHand hand);
}
