package org.sdoaj.item.fluids;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.sdoaj.eloncraft.Main;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class ModFluids {
    public static FluidOil OIL;
    public static BlockFluidOil BLOCK_OIL;

    public static void init() {
        OIL = new FluidOil();
        BLOCK_OIL = new BlockFluidOil();
    }

    @SubscribeEvent
    public static void registerRenders(ModelRegistryEvent event) {
        BLOCK_OIL.render();
    }
}
