package org.sdoaj.eloncraft.blocks.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class ModFluidTank extends FluidTank {
    private final String name;

    public ModFluidTank(String name, int capacity) {
        super(capacity);
        this.name = name;
    }

    public void empty() {
        if (this.fluid != null) {
            this.fluid.amount = 0;
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setString(name, this.getFluidAmount() == 0 ? "empty" : this.getFluid().getFluid().getName() + ":" + this.getFluidAmount());
        return compound;
    }

    @Override
    public FluidTank readFromNBT(NBTTagCompound compound) {
        if (!compound.getString(name).equals("empty")) {
            String[] data = compound.getString(name).split(":");
            this.fluid = new FluidStack(FluidRegistry.getFluid(data[0]), Integer.valueOf(data[1]));
        } else {
            empty();
        }

        return this;
    }

    public boolean isEmpty() {
        return this.getFluidAmount() == 0;
    }

    public boolean isFull() {
        return this.getFluidAmount() == this.capacity;
    }

    public int getRemainingSpace() {
        return this.getCapacity() - this.getFluidAmount();
    }
}