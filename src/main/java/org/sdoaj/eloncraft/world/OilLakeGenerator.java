package org.sdoaj.eloncraft.world;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.sdoaj.eloncraft.Eloncraft;
import org.sdoaj.eloncraft.fluids.ModFluids;

import java.util.Random;

@Mod.EventBusSubscriber(modid = Eloncraft.MODID)
public class OilLakeGenerator {
    private static final int rarity = 100;

    // mostly copied from lava lake generation in ChunkGeneratorOverworld
    @SubscribeEvent
    public static void generateOilLakes(PopulateChunkEvent event) {
        World world = event.getWorld();

        if (world.provider.getDimension() != 0) {
            return;
        }

        Random random = event.getRand();

        if (random.nextInt(rarity / 10) != 0) {
            return;
        }

        BlockPos pos = new BlockPos(event.getChunkX() * 16, 0, event.getChunkZ() * 16);

        int x = random.nextInt(16) + 8;
        int y = random.nextInt(random.nextInt(248) + 8);
        int z = random.nextInt(16) + 8;

        if (y < world.getSeaLevel() || random.nextInt(rarity / 8) == 0) {
            (new WorldGenLakes(ModFluids.getBlockFromFluid(ModFluids.OIL))).generate(world, random, pos.add(x, y, z));
        }
    }
}
