package org.sdoaj.eloncraft.world;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenCrater extends WorldGenerator {
    private final double chance;
    private final int minSize;
    private final int maxSize;

    public WorldGenCrater(double chance, int minSize, int maxSize) {
        this.chance = chance;
        this.minSize = minSize;
        this.maxSize = maxSize;
    }

    @Override
    public boolean generate(World world, Random random, BlockPos pos) {
        if (shouldGenerateCrater(world.getSeed(), pos.getX(), pos.getZ())) {
            world.setBlockState(findValidPos(world, pos), Blocks.DIAMOND_BLOCK.getDefaultState());
            return true;
        }

        return false;
    }

    private double randomFromXZ(int x, int z) {
        int n = x + z * 57;
        n = n << 13 ^ n;
        double value = 1.0 - (n * (n * n * 15731 + 789221) + 1376312589 & 0x7fffffff) / 1073741824.0;
        return (value + 1.0) / 2.0;
    }

    // https://stackoverflow.com/questions/7213469/
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

    private int getCraterSize(long seed, int x, int z) {
        final double value = randomFromXZ(x, z);
        final double random = new Random(seed).nextDouble();

        double size;
        if (value > random) {
            size = value - random;
        } else {
            size = (value + 1.0) - random;
        }
        return (int) (minSize + (size * (maxSize - minSize)));
    }

    private BlockPos findValidPos(World world, BlockPos pos) {
        BlockPos validPos = new BlockPos(pos.getX() + 8, 0, pos.getZ() + 8);

        for (int y = 0; y < 256; y++) {
            BlockPos possiblePos = new BlockPos(validPos.getX(), y, validPos.getZ());
            if (!world.isAirBlock(possiblePos)) {
                validPos = possiblePos;
            }
        }

        return validPos;
    }
}
