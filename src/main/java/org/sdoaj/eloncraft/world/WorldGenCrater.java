package org.sdoaj.eloncraft.world;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenCrater extends WorldGenerator {
    private final double chance;

    public WorldGenCrater(double chance) {
        this.chance = chance;
    }

    @Override
    public boolean generate(World world, Random random, BlockPos pos) {
        if (random.nextDouble() < chance) {
            world.setBlockState(findValidPos(world, pos), Blocks.DIAMOND_BLOCK.getDefaultState());
            return true;
        }

        return false;
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
