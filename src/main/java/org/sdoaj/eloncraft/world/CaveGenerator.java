package org.sdoaj.eloncraft.world;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.gen.MapGenCaves;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CaveGenerator extends MapGenCaves {
    private final List<Block> replaceableBlocks;

    public CaveGenerator(Block... replaceableBlocks) {
        this.replaceableBlocks = Arrays.stream(replaceableBlocks).collect(Collectors.toList());
    }

    @Override
    protected boolean canReplaceBlock(IBlockState state, IBlockState up) {
        return replaceableBlocks.contains(state.getBlock());
    }
}
