package org.sdoaj.eloncraft.blocks.launch.controller;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import org.sdoaj.eloncraft.blocks.launch.BlockLaunchpad;
import org.sdoaj.eloncraft.blocks.machines.BlockMachine;
import org.sdoaj.eloncraft.blocks.tileentities.ModFluidTank;
import org.sdoaj.eloncraft.blocks.tileentities.TileEntityFluidMachine;
import org.sdoaj.eloncraft.blocks.tileentities.CustomEnergyStorage;
import org.sdoaj.eloncraft.entity.falcon9.EntityFalcon9Stage1;
import org.sdoaj.eloncraft.fluids.ModFluids;
import org.sdoaj.eloncraft.util.ItemStackHandler;

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
    private int lastRocketFuelAmount;
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
    private int lastRocketOxygenAmount;

    private boolean hadRocket = false;

    {
        fuelTank.setTileEntity(this);
        oxygenTank.setTileEntity(this);
    }

    EntityFalcon9Stage1 rocket = null;

    private boolean isLoading = false;

    public static final int mbPerOperation = 20;

    public TileEntityLaunchController() {
        super("launch_controller", 0, 1, 20,
                new CustomEnergyStorage(100000, 100000, 0), BlockMachine.IS_ON);

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
        if (rocket != null) {
            if (rocket.fuelTank.getFluidAmount() != lastRocketFuelAmount || rocket.oxygenTank.getFluidAmount() != lastRocketOxygenAmount) {
                return true;
            }
        } else {
            if (hadRocket) {
                return true;
            }
        }

        return super.hasChanged() || fuelTank.getFluidAmount() != lastFuelAmount || oxygenTank.getFluidAmount() != lastOxygenAmount;
    }

    @Override
    protected void updatePreviousValues() {
        super.updatePreviousValues();
        lastFuelAmount = fuelTank.getFluidAmount();
        lastOxygenAmount = oxygenTank.getFluidAmount();

        if (rocket != null) {
            lastRocketFuelAmount = rocket.fuelTank.getFluidAmount();
            lastRocketOxygenAmount = rocket.oxygenTank.getFluidAmount();
        } else {
            lastRocketFuelAmount = 0;
            lastRocketOxygenAmount = 0;
        }

        hadRocket = rocket != null;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();

        rocket = BlockLaunchpad.getNearbyRocket(this.pos).orElse(null);
    }

    @Override
    public boolean canProcess() {
        return isLoading && canProcessWithoutLoading();
    }

    public boolean canProcessWithoutLoading() {
        if (rocket == null) {
            return false;
        }

        boolean canProcess = false;

        if (!fuelTank.isEmpty()) {
            if (!rocket.fuelTank.isFull()) {
                canProcess = true;
            }
        }

        if (!oxygenTank.isEmpty()) {
            if (!rocket.oxygenTank.isFull()) {
                canProcess = true;
            }
        }

        return canProcess;
    }

    @Override
    public void finishProcessing() {
        int maxFuelTransfer = Math.min(Math.min(fuelTank.getFluidAmount(), rocket.fuelTank.getRemainingSpace()), mbPerOperation);
        int maxOxygenTransfer = Math.min(Math.min(oxygenTank.getFluidAmount(), rocket.oxygenTank.getRemainingSpace()), mbPerOperation);

        fuelTank.drainInternal(maxFuelTransfer, true);
        rocket.fuelTank.fillInternal(new FluidStack(ModFluids.RP1, maxFuelTransfer), true);
        oxygenTank.drainInternal(maxOxygenTransfer, true);
        rocket.oxygenTank.fillInternal(new FluidStack(ModFluids.LOX, maxOxygenTransfer), true);

        if (!canProcess()) {
            isLoading = false;
        }
    }

    @Override
    protected void onUseBucket(EntityPlayer player, EnumHand hand) {
        FluidUtil.interactWithFluidHandler(player, hand, fuelTank);
        FluidUtil.interactWithFluidHandler(player, hand, oxygenTank);
    }

    @Override
    protected void onButtonPressed(int id) {
        if (id == 0) {
            if (!isLoading) {
                if (canProcessWithoutLoading() && hasEnergyForTick()) {
                    isLoading = true;
                }
            } else {
                isLoading = false;
            }
        }
    }
}