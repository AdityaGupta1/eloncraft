package org.sdoaj.item.fluids;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import org.sdoaj.eloncraft.Main;

class FluidOil extends Fluid {
    FluidOil() {
        super("oil", new ResourceLocation(Main.MODID, "fluids/oil_still"),
                new ResourceLocation(Main.MODID, "fluids/oil_flow"));
        FluidRegistry.registerFluid(this);
        FluidRegistry.addBucketForFluid(this);
    }
}