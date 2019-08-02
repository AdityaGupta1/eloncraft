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
        for (int cx = chunkX - 2; cx <= chunkX + 2; cx++) {
            for (int cz = chunkZ - 2; cz <= chunkZ + 2; cz++) {
                for (int x = 0; x < 16; x++) {
                    for (int z = 0; z < 16; z++) {
                        if (this.randomFromXZ(cx * 16 + x, (cz * 16 + z) * 1000) < chance) {
                            final int size = getCraterRadius(seed, cx * 16 + x, cz * 16 + z);
                            this.makeCrater(cx * 16 + x, cz * 16 + z, chunkX * 16, chunkZ * 16, size, primer);
                        }
                    }
                }
            }
        }
    }

    public void makeCrater(int craterX, int craterZ, int chunkX, int chunkZ, int size, ChunkPrimer primer) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                double xDev = craterX - (chunkX + x);
                double zDev = craterZ - (chunkZ + z);
                if (xDev * xDev + zDev * zDev < size * size) {
                    xDev /= size;
                    zDev /= size;
                    final double sqrtY = xDev * xDev + zDev * zDev;
                    double yDev = sqrtY * sqrtY * 6;
                    yDev = 5 - yDev;
                    int helper = 0;
                    for (int y = 127; y > 0; y--) {
                        if (Blocks.AIR != primer.getBlockState(x, y, z).getBlock() && helper <= yDev) {
                            primer.setBlockState(x, y, z, Blocks.AIR.getDefaultState());
                            helper++;
                        }

                        if (helper > yDev) {
                            break;
                        }
                    }
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
        final double value = randomFromXZ(x, z);

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

    /*public void generate(World world, int chunkX, int chunkZ, ChunkPrimer primer) {
        long seed = world.getSeed();

        List<BlockPos> craters = new ArrayList<>();
        int minX = chunkX * 16 - maxRadius;
        int maxX = chunkX * 16 + 16 + maxRadius;
        int minZ = chunkZ * 16 - maxRadius;
        int maxZ = chunkZ * 16 + 16 + maxRadius;

        for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
                if (shouldGenerateCrater(seed, x, z)) {
                    makeCrater(seed, x, z, chunkX, chunkZ, primer);
                }
            }
        }
    }

    private void makeCrater(long seed, int craterX, int craterZ, int chunkX, int chunkZ, ChunkPrimer primer) {
        int radius = getCraterRadius(seed, craterX, craterZ);

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z <= 16; z++) {
                int dx = craterX - (chunkX * 16 + x);
                int dz = craterZ - (chunkZ * 16 + z);

                if (dx * dx + dz * dz < radius * radius) {
                    primer.setBlockState(x, 63, z, Blocks.DIAMOND_BLOCK.getDefaultState());
                }

                // primer.setBlockState(x & 15, craterPos.getY(), z & 15, Blocks.DIAMOND_BLOCK.getDefaultState());

                // if (distanceSquared < radiusSquared) {
                //     {
                //         int dy = (int) (distanceSquared / radiusSquared) + 1;
                //         int y = craterPos.getY();
                //         while (dy > 0) {
                //             primer.setBlockState(craterPos.getZ() & 15, y, craterPos.getX() & 15, Blocks.AIR.getDefaultState());
                //             y--;
                //             dy--;
                //         }
                //     }
                //
                //     for (int y = craterPos.getY(); y < 256; y++) {
                //         primer.setBlockState(craterPos.getZ() & 15, y, craterPos.getX() & 15, Blocks.AIR.getDefaultState());
                //     }
                // }
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
        if (true) {
            return x == 100 && z == 0;
        }

        final double value = randomFromXZ(x, z);

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

    private BlockPos findValidPos(ChunkPrimer primer, int x, int z) {
        BlockPos validPos = new BlockPos(x, 0, z);

        for (int y = 0; y < 256; y++) {
            BlockPos possiblePos = new BlockPos(validPos.getX(), y, validPos.getZ());
            if (primer.getBlockState(possiblePos.getX(), possiblePos.getY(), possiblePos.getZ()).getBlock() != Blocks.AIR) {
                validPos = possiblePos;
            }
        }

        return validPos;
    }*/
}
