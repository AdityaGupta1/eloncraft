package org.sdoaj.eloncraft.biome;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import org.sdoaj.eloncraft.Eloncraft;
import org.sdoaj.eloncraft.dimension.moon.BiomeMoon;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Eloncraft.MODID)
public class ModBiomes {
    private static final List<Biome> biomes = new ArrayList<>();

    public static final Biome MOON = new BiomeMoon();

    @SubscribeEvent
    public static void registerBiomes(RegistryEvent.Register<Biome> event) {
        IForgeRegistry<Biome> registry = event.getRegistry();
    }

    public static void addBiome(Biome biome) {
        biomes.add(biome);
    }
}
