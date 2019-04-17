package org.sdoaj.blocks.machines.refinery;

import net.minecraftforge.fluids.FluidStack;
import org.sdoaj.util.StackUtil;

public class RefineryRecipe {
    private FluidStack input;
    private FluidStack output;

    public RefineryRecipe(FluidStack input, FluidStack output) {
        this.input = input;
        this.output = output;
    }

    public boolean matches(FluidStack stack) {
        return StackUtil.fluidStackApplies(input, stack);
    }

    public FluidStack getInput() {
        return this.input;
    }

    public FluidStack getOutput() {
        return this.output;
    }
}