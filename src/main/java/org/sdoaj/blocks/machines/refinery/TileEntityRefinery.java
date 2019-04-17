package org.sdoaj.blocks.machines.refinery;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import org.sdoaj.blocks.machines.BlockMachine;
import org.sdoaj.blocks.machines.TileEntityFluidMachine;
import org.sdoaj.blocks.tileentities.CustomEnergyStorage;
import org.sdoaj.util.ItemStackHandler;

import java.util.HashMap;
import java.util.Map;

public class TileEntityRefinery extends TileEntityFluidMachine {
    final int guiTopHeight = 79;

    private final int capacity = 8000;
    final FluidTank inputTank = new FluidTank(capacity) {
        @Override
        public boolean canFillFluidType(FluidStack stack) {
            return stack != null && RefineryRecipes.getRecipeFromInput(stack) != null;
        }

        @Override
        public boolean canDrain() {
            return false;
        }
    };
    final FluidTank outputTank = new FluidTank(capacity) {
        @Override
        public boolean canFill() {
            return false;
        }

        @Override
        public boolean canDrain() {
            return true;
        }
    };

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
    public ItemStackHandler.IAcceptor getAcceptor() {
        return ItemStackHandler.ACCEPT_FALSE;
    }

    @Override
    public ItemStackHandler.IRemover getRemover() {
        return ItemStackHandler.REMOVE_FALSE;
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
}