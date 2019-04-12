package org.sdoaj.item.fluids;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import org.sdoaj.eloncraft.Main;

class ModFluid extends Fluid {
    private BlockFluid block;

    private int maxFlowDistance = 8;

    ModFluid(String name) {
        super(name, new ResourceLocation(Main.MODID, "fluids/" + name + "_still"),
                new ResourceLocation(Main.MODID, "fluids/" + name + "_flow"));
        FluidRegistry.registerFluid(this);
        FluidRegistry.addBucketForFluid(this);
    }

    public BlockFluid getFluidBlock() {
        return block;
    }

    public ModFluid setMaxFlowDistance(int distance) {
        this.maxFlowDistance = distance;
        return this;
    }

    @Override
    public ModFluid setDensity(int density) {
        super.setDensity(density);
        return this;
    }

    @Override
    public ModFluid setViscosity(int viscosity) {
        super.setViscosity(viscosity);
        return this;
    }

    @Override
    public ModFluid setLuminosity(int luminosity) {
        super.setLuminosity(luminosity);
        return this;
    }

    @Override
    public ModFluid setTemperature(int temperature) {
        super.setTemperature(temperature);
        return this;
    }

    // call this after setting fluid properties
    ModFluid createBlock() {
        block = new BlockFluid(this);
        block.setMaxFlowDistance(maxFlowDistance);
        ModFluids.addBlock(block);
        return this;
    }
}