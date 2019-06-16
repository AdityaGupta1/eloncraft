package org.sdoaj.eloncraft.util;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.stream.Stream;

public class WorldUtil {
    public static int getRedstonePowerFromNeighbors(World world, BlockPos pos) {
        return Stream.of(EnumFacing.VALUES).mapToInt(facing -> world.getRedstonePower(pos, facing)).sum();
    }
}
