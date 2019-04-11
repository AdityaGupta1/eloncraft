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
}