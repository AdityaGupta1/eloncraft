package org.sdoaj.item.fluids;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import org.sdoaj.eloncraft.Main;

class ModFluid extends Fluid {
    private final BlockFluid block;

    ModFluid(String name) {
        super(name, new ResourceLocation(Main.MODID, "fluids/" + name + "_still"),
                new ResourceLocation(Main.MODID, "fluids/" + name + "_flow"));
        FluidRegistry.registerFluid(this);
        FluidRegistry.addBucketForFluid(this);

        block = new BlockFluid(this);
        ModFluids.addBlock(block);
    }

    public BlockFluid getFluidBlock() {
        return block;
    }

    public ModFluid setMaxFlowDistance(int distance) {
        this.block.setMaxFlowDistance(distance);
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
}