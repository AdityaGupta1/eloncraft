package org.sdoaj.eloncraft.world;

import net.minecraft.init.Blocks;
import net.minecraft.world.chunk.ChunkPrimer;

import java.util.Random;

public class WorldGenCrater {
    private final double chance;
    private final int minRadius;
    private final int maxRadius;

    public WorldGenCrater(double chance, int minRadius, int maxRadius) {
        this.chance = chance;
        this.minRadius = minRadius;
        this.maxRadius = maxRadius;
    }

    public void generate(long seed, int chunkX, int chunkZ, ChunkPrimer primer) {
        for (int craterX = chunkX * 16 - maxRadius; craterX <= chunkX * 16 + 16 + maxRadius; craterX++) {
            for (int craterZ = chunkZ * 16 - maxRadius; craterZ <= chunkZ * 16 + 16 + maxRadius; craterZ++) {
                if (shouldGenerateCrater(seed, craterX, craterZ)) {
                    this.makeCraterChunk(craterX, craterZ, chunkX, chunkZ, getCraterRadius(seed, craterX, craterZ), primer);
                }
            }
        }
    }

    // https://www.desmos.com/calculator/pnv2gvkxel
    private void makeCraterChunk(int craterX, int craterZ, int chunkX, int chunkZ, int radius, ChunkPrimer primer) {
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

    // https://stackoverflow.com/questions/7213469/
    private double randomFromXZ(int x, int z) {
        int n = x + z * 57;
        n = n << 13 ^ n;
        double value = 1.0 - (n * (n * n * 15731 + 789221) + 1376312589 & 0x7fffffff) / 1073741824.0;
        return (value + 1.0) / 2.0;
    }

    private boolean shouldGenerateCrater(long seed, int x, int z) {
        final double value = randomFromXZ(x, z * 997);

        final double random = new Random(seed).nextDouble();
        double min = random - chance / 2;
        double max = random + chance / 2;

        double add = 0.0;
        if (min < 0.0) {
            add = -min;
        } else if (max > 1.0) {
            add = -(max - 1.0);
        }
        min += add;
        max += add;

        return value > min && value < max;
    }

    private int getCraterRadius(long seed, int x, int z) {
        final double value = randomFromXZ(x, z);
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
