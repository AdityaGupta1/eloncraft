package org.sdoaj.eloncraft.blocks.machines.loxcollector;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import org.sdoaj.eloncraft.blocks.machines.BlockMachine;
import org.sdoaj.eloncraft.blocks.tileentities.ModFluidTank;
import org.sdoaj.eloncraft.blocks.tileentities.TileEntityFluidMachine;
import org.sdoaj.eloncraft.blocks.tileentities.CustomEnergyStorage;
import org.sdoaj.eloncraft.fluids.ModFluids;
import org.sdoaj.eloncraft.util.ItemStackHandler;

import java.util.HashMap;
import java.util.Map;

public class TileEntityLOXCollector extends TileEntityFluidMachine {
    final int guiTopHeight = 99;

    private final int capacity = 8000;
    final ModFluidTank tank = new ModFluidTank("Tank", capacity) {
        @Override
        public boolean canFill() {
            return false;
        }

        @Override
        public boolean canDrain() {
            return true;
        }
    };
    private int lastTankAmount;

    {
        tank.setTileEntity(this);
    }

    public static final int mbPerOperation = 10;

    public TileEntityLOXCollector() {
        super("lox_collector", 0, 1, 50,
                new CustomEnergyStorage(100000, 100000, 0), BlockMachine.IS_ON);

        Map<FluidTank, EnumFacing[]> fluidTanks = new HashMap<>();
        fluidTanks.put(tank, null);
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
        return super.hasChanged() || tank.getFluidAmount() != lastTankAmount;
    }

    @Override
    protected void updatePreviousValues() {
        super.updatePreviousValues();
        lastTankAmount = tank.getFluidAmount();
    }

    @Override
    public boolean canProcess() {
        return tank.getFluidAmount() + mbPerOperation <= tank.getCapacity();
    }

    @Override
    public void finishProcessing() {
        tank.fillInternal(new FluidStack(ModFluids.LOX, mbPerOperation), true);
    }

    @Override
    protected void onUseBucket(EntityPlayer player, EnumHand hand) {
        FluidUtil.interactWithFluidHandler(player, hand, tank);
    }

    @Override
    public boolean guiShowProgress() {
        return this.world.getBlockState(this.pos).getValue(this.IS_ON);
    }
}