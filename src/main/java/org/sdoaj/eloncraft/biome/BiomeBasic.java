package org.sdoaj.eloncraft.biome;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

import java.util.Random;

public class BiomeBasic extends Biome {
    public IBlockState stoneBlock = Blocks.STONE.getDefaultState();

    public BiomeBasic(BiomeProperties properties) {
        super(properties);
        ModBiomes.addBiome(this);
    }

    @Override
    public void genTerrainBlocks(World world, Random random, ChunkPrimer primer, int x, int z, double noiseValue) {
        super.genTerrainBlocks(world, random, primer, x, z, noiseValue);

        if (stoneBlock == Blocks.STONE.getDefaultState()) {
           return;
        }

        int x1 = z & 15;
        int z1 = x & 15;

        for (int y = 0; y < 256; y++) {
            Block block = primer.getBlockState(x1, y, z1).getBlock();

            if (block == Blocks.STONE) {
                primer.setBlockState(x1, y, z1, this.stoneBlock);
            }
        }
    }
}
