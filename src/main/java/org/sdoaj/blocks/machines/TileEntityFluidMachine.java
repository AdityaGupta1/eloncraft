package org.sdoaj.blocks.machines;

import net.minecraft.block.properties.PropertyBool;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.sdoaj.blocks.tileentities.CustomEnergyStorage;

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
    public IFluidHandler getFluidHandler(EnumFacing facing) {
        return getTank(facing);
    }

    private FluidTank getTank(EnumFacing facing) {
        for (FluidTank tank : fluidTanks.keySet()) {
            for (EnumFacing side : fluidTanks.get(tank)) {
                if (facing == side) {
                    return tank;
                }
            }
        }

        return null;
    }

    public int getTankScaled(EnumFacing facing, int i) {
        FluidTank tank = getTank(facing);
        return tank.getFluidAmount() * i / tank.getCapacity();
    }

    protected abstract void onUseBucket(EntityPlayer player, EnumHand hand);
}
