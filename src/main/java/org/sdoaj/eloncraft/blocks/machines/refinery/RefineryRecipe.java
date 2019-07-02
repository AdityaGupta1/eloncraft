package org.sdoaj.eloncraft.blocks.machines.refinery;

import net.minecraftforge.fluids.FluidStack;
import org.sdoaj.eloncraft.util.StackUtil;

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
        return this.input.copy();
    }

    public FluidStack getOutput() {
        return this.output.copy();
    }
}