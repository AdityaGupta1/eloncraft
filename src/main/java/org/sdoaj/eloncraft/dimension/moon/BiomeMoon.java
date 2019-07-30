package org.sdoaj.eloncraft.dimension.moon;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.sdoaj.eloncraft.biome.BiomeBasic;
import org.sdoaj.eloncraft.blocks.ModBlocks;

import java.util.Random;

public class BiomeMoon extends BiomeBasic {
    private static final BiomeProperties properties = new BiomeProperties("moon");

    static {
        properties.setRainDisabled();
        properties.setTemperature(1.0F);
    }

    public BiomeMoon() {
        super(properties);

        this.topBlock = ModBlocks.MOON_DIRT.getDefaultState();
        this.fillerBlock = ModBlocks.MOON_DIRT.getDefaultState();
        this.stoneBlock = ModBlocks.MOON_ROCK.getDefaultState();

        this.decorator.treesPerChunk = -999;

        this.spawnableCreatureList.clear();
    }

    public void decorate(World worldIn, Random rand, BlockPos pos) {}
}
