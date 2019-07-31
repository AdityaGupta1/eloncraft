package org.sdoaj.eloncraft.dimension.moon;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.sdoaj.eloncraft.biome.BiomeBasic;
import org.sdoaj.eloncraft.blocks.ModBlocks;

import java.util.List;
import java.util.Random;

public class BiomeMoon extends BiomeBasic {
    public BiomeMoon() {
        super("moon", properties -> properties.setRainDisabled().setTemperature(1.0F));

        this.topBlock = ModBlocks.MOON_DIRT.getDefaultState();
        this.fillerBlock = ModBlocks.MOON_DIRT.getDefaultState();
        this.stoneBlock = ModBlocks.MOON_ROCK.getDefaultState();

        this.decorator.treesPerChunk = -999;

        this.spawnableCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableMonsterList.clear();
        this.modSpawnableLists.values().forEach(List::clear);
    }

    public void decorate(World worldIn, Random rand, BlockPos pos) {}
}
