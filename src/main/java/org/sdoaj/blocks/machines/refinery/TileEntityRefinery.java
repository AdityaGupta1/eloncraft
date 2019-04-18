package org.sdoaj.blocks.machines.refinery;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import org.sdoaj.blocks.machines.BlockMachine;
import org.sdoaj.blocks.machines.ModFluidTank;
import org.sdoaj.blocks.machines.TileEntityFluidMachine;
import org.sdoaj.blocks.tileentities.CustomEnergyStorage;
import org.sdoaj.util.ItemStackHandler;

import java.util.HashMap;
import java.util.Map;

public class TileEntityRefinery extends TileEntityFluidMachine {
    final int guiTopHeight = 79;

    private final int capacity = 8000;
    final ModFluidTank inputTank = new ModFluidTank("InputTank", capacity) {
        @Override
        public boolean canFillFluidType(FluidStack stack) {
            return stack != null && RefineryRecipes.getRecipeFromInput(stack) != null;
        }

        @Override
        public boolean canDrain() {
            return false;
        }
    };
    private int lastInputAmount;
    final ModFluidTank outputTank = new ModFluidTank("OutputTank", capacity) {
        @Override
        public boolean canFill() {
            return false;
        }

        @Override
        public boolean canDrain() {
            return true;
        }
    };
    private int lastOutputAmount;

    {
        inputTank.setTileEntity(this);
        outputTank.setTileEntity(this);
    }

    public TileEntityRefinery() {
        super("refinery", 0, 1, 10,
                new CustomEnergyStorage(100000, 100000, 0), BlockMachine.IS_ON);

        Map<FluidTank, EnumFacing[]> fluidTanks = new HashMap<>();
        fluidTanks.put(inputTank, new EnumFacing[]{EnumFacing.UP, EnumFacing.DOWN});
        fluidTanks.put(outputTank, new EnumFacing[]{EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST});
        setFluidTanks(fluidTanks);
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type) {
        super.writeSyncableNBT(compound, type);
        inputTank.writeToNBT(compound);
        outputTank.writeToNBT(compound);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type) {
        super.readSyncableNBT(compound, type);
        inputTank.readFromNBT(compound);
        outputTank.readFromNBT(compound);
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
        return super.hasChanged() || inputTank.getFluidAmount() != lastInputAmount || outputTank.getFluidAmount() != lastOutputAmount;
    }

    @Override
    protected void updatePreviousValues() {
        super.updatePreviousValues();
        lastInputAmount = inputTank.getFluidAmount();
        lastOutputAmount = outputTank.getFluidAmount();
    }

    @Override
    public boolean canProcess() {
        if (inputTank.getFluidAmount() == 0) {
            return false;
        }

        RefineryRecipe recipe = RefineryRecipes.getRecipeFromInput(inputTank.getFluid());

        if (recipe == null) {
            return false;
        }

        return outputTank.getFluidAmount() + recipe.getOutput().amount <= outputTank.getCapacity();
    }

    @Override
    public void finishProcessing() {
        RefineryRecipe recipe = RefineryRecipes.getRecipeFromInput(inputTank.getFluid());

        if (recipe == null) {
            return;
        }

        outputTank.fillInternal(recipe.getOutput(), true);
        inputTank.drainInternal(recipe.getInput(), true);
    }

    @Override
    protected void onUseBucket(EntityPlayer player, EnumHand hand) {
        if (!FluidUtil.interactWithFluidHandler(player, hand, inputTank)) {
            FluidUtil.interactWithFluidHandler(player, hand, outputTank);
        }
    }
}