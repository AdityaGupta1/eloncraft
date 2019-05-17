package org.sdoaj.blocks.launch.controller;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import org.sdoaj.blocks.machines.ModFluidTank;
import org.sdoaj.blocks.machines.TileEntityFluidMachine;
import org.sdoaj.fluids.ModFluids;
import org.sdoaj.util.ItemStackHandler;

import java.util.HashMap;
import java.util.Map;

public class TileEntityLaunchController extends TileEntityFluidMachine {
    final int guiTopHeight = 99;

    private final int capacity = 16000;
    final ModFluidTank fuelTank = new ModFluidTank("FuelTank", capacity) {
        @Override
        public boolean canFillFluidType(FluidStack fluid) {
            return fluid.getFluid() == ModFluids.RP1;
        }

        @Override
        public boolean canDrain() {
            return false;
        }
    };
    private int lastFuelAmount;
    final ModFluidTank oxygenTank = new ModFluidTank("OxygenTank", capacity) {
        @Override
        public boolean canFillFluidType(FluidStack fluid) {
            return fluid.getFluid() == ModFluids.LOX;
        }

        @Override
        public boolean canDrain() {
            return false;
        }
    };
    private int lastOxygenAmount;

    {
        fuelTank.setTileEntity(this);
        oxygenTank.setTileEntity(this);
    }

    public TileEntityLaunchController() {
        super("launch_controller", 0, 0, 0,
                null, null);

        Map<FluidTank, EnumFacing[]> fluidTanks = new HashMap<>();
        fluidTanks.put(fuelTank, new EnumFacing[]{EnumFacing.UP, EnumFacing.DOWN});
        fluidTanks.put(oxygenTank, new EnumFacing[]{EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST});
        setFluidTanks(fluidTanks);
    }

    @Override
    public ItemStackHandler.IAcceptor getAcceptor() {
        return ItemStackHandler.ACCEPT_FALSE;
    }

    @Override
    public ItemStackHandler.IRemover getRemover() {
        return ItemStackHandler.REMOVE_FALSE;
    }

    @Override
    protected boolean hasChanged() {
        return super.hasChanged() || fuelTank.getFluidAmount() != lastFuelAmount || oxygenTank.getFluidAmount() != lastOxygenAmount;
    }

    @Override
    protected void updatePreviousValues() {
        super.updatePreviousValues();
        lastFuelAmount = fuelTank.getFluidAmount();
        lastOxygenAmount = oxygenTank.getFluidAmount();
    }

    @Override
    public boolean canProcess() {
        return false;
    }

    @Override
    public void finishProcessing() {}

    @Override
    protected void onUseBucket(EntityPlayer player, EnumHand hand) {
        FluidUtil.interactWithFluidHandler(player, hand, fuelTank);
        FluidUtil.interactWithFluidHandler(player, hand, oxygenTank);
    }
}