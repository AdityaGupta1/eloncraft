package org.sdoaj.fluids;

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

    public static ModFluid RP1;
    public static ModFluid LOX;

    public static void init() {
        OIL = new ModFluid("oil").setMaxFlowDistance(6).setDensity(930).setViscosity(8000).createBlock();

        RP1 = new ModFluid("rp1").setMaxFlowDistance(8).setDensity(810).setViscosity(2000).createBlock();
        LOX = new ModFluid("lox").setMaxFlowDistance(4).setDensity(1140).setViscosity(1500).setTemperature(90).createBlock();
    }

    public static void addBlock(BlockFluid block) {
        blocks.add(block);
    }

    @SubscribeEvent
    public static void registerRenders(ModelRegistryEvent event) {
        blocks.forEach(BlockFluid::render);
    }
}
