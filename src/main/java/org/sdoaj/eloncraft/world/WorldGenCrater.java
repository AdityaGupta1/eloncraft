package org.sdoaj.eloncraft.world;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.chunk.ChunkPrimer;
import org.sdoaj.eloncraft.util.RandomUtil;

import java.util.Random;

public class WorldGenCrater {
    private final double chance;
    private final int minRadius;
    private final int maxRadius;

    private IBlockState meteorBlock = null;
    private int meteorMinCraterRadius;
    private double meteorMaxRadius;
    private double meteorChance;

    public WorldGenCrater(double chance, int minRadius, int maxRadius) {
        this.chance = chance;
        this.minRadius = minRadius;
        this.maxRadius = maxRadius;
    }

    public WorldGenCrater setMeteor(IBlockState meteorBlock, int minCraterRadius, double meteorMaxRadius, double meteorChance) {
        this.meteorBlock = meteorBlock;
        this.meteorMinCraterRadius = minCraterRadius;
        this.meteorMaxRadius = meteorMaxRadius;
        this.meteorChance = meteorChance;
        return this;
    }

    public void generate(long seed, int chunkX, int chunkZ, ChunkPrimer primer) {
        for (int craterX = chunkX * 16 - maxRadius; craterX <= chunkX * 16 + 16 + maxRadius; craterX++) {
            for (int craterZ = chunkZ * 16 - maxRadius; craterZ <= chunkZ * 16 + 16 + maxRadius; craterZ++) {
                if (shouldGenerateCrater(seed, craterX, craterZ)) {
                    this.makeCraterChunk(seed, craterX, craterZ, chunkX, chunkZ, getCraterRadius(seed, craterX, craterZ), primer);
                }
            }
        }
    }

    // https://www.desmos.com/calculator/pnv2gvkxel
    private void makeCraterChunk(long seed, int craterX, int craterZ, int chunkX, int chunkZ, int radius, ChunkPrimer primer) {
        final double radiusSquared = radius * radius;

        final int craterDepth = (int) (0.1 * radius + 1.5); // 5 -> 2, 45 -> 6

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                double dx = craterX - (chunkX * 16 + x);
                double dz = craterZ - (chunkZ * 16 + z);
                final double distanceSquared = dx * dx + dz * dz;
                if (!(distanceSquared < radiusSquared)) {
                    continue;
                }

                final double distance = Math.sqrt(distanceSquared);
                int pointDepth = (int) Math.min(craterDepth, Math.pow(Math.max(radius - distance, 0.0), 0.7));

                int depth = 0;
                boolean reachedSurface = false;
                for (int y = 127; y > 0; y--) {
                    if (depth == pointDepth) {
                        if (meteorBlock == null) {
                            break;
                        }

                        if (radius < meteorMinCraterRadius || distance > meteorMaxRadius) {
                            break;
                        }

                        if (primer.getBlockState(x, y, z).getBlock() == Blocks.AIR) {
                            break;
                        }

                        if (RandomUtil.randomBooleanFromXZ(seed, x, z, meteorChance)) {
                            primer.setBlockState(x, y, z, meteorBlock);
                        }

                        break;
                    }

                    if (primer.getBlockState(x, y, z).getBlock() != Blocks.AIR) {
                        reachedSurface = true;
                    }

                    if (!reachedSurface) {
                        continue;
                    }

                    primer.setBlockState(x, y, z, Blocks.AIR.getDefaultState());
                    depth++;
                }
            }
        }
    }

    private boolean shouldGenerateCrater(long seed, int x, int z) {
        return RandomUtil.randomBooleanFromXZ(seed, x, z * 997, chance);
    }

    private int getCraterRadius(long seed, int x, int z) {
        final double value = RandomUtil.randomFromXZ(x, z);
        final double random = new Random(seed).nextDouble();

        double size;
        if (value > random) {
            size = value - random;
        } else {
            size = (value + 1.0) - random;
        }
        return (int) (minRadius + (size * (maxRadius - minRadius)));
    }
}
