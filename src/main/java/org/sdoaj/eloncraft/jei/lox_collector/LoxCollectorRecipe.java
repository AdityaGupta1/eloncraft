package org.sdoaj.eloncraft.jei.lox_collector;

import net.minecraftforge.fluids.FluidStack;
import org.sdoaj.eloncraft.blocks.machines.loxcollector.TileEntityLOXCollector;
import org.sdoaj.eloncraft.fluids.ModFluids;

public class LoxCollectorRecipe {
    final FluidStack output = new FluidStack(ModFluids.LOX, TileEntityLOXCollector.mbPerOperation);
}
