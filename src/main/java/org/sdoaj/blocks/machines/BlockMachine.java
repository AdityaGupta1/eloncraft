package org.sdoaj.blocks.machines;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.sdoaj.blocks.tileentities.BlockContainerBase;

import java.util.Random;

public abstract class BlockMachine extends BlockContainerBase {
    public static final PropertyBool IS_ON = PropertyBool.create("on");

    public BlockMachine(String name, Material material) {
        super(name, material);
        this.setTickRandomly(true);
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        return this.getMetaFromState(state) == 1 ? 12 : 0;
    }

    @Override
    public int damageDropped(IBlockState state) {
        return 0;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        boolean isOn = meta == 1;
        return this.getDefaultState().withProperty(IS_ON, isOn);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(IS_ON) ? 1 : 0;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, IS_ON);
    }

    protected double randomCoordinateAdd(Random random) {
        return (random.nextDouble() - 0.5) * 0.75;
    }
}
