package org.sdoaj.item.fluids;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.sdoaj.eloncraft.Main;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class ModFluids {
    private static final List<BlockFluid> blocks = new ArrayList<>();

    public static ModFluid OIL;

    public static void init() {
        OIL = new ModFluid("oil").setMaxFlowDistance(6).setDensity(930).setViscosity(8000).createBlock();
    }

    public static void addBlock(BlockFluid block) {
        blocks.add(block);
    }

    @SubscribeEvent
    public static void registerRenders(ModelRegistryEvent event) {
        blocks.forEach(BlockFluid::render);
    }
}
