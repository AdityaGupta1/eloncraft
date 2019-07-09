package org.sdoaj.eloncraft.blocks.pipes;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;

public class BlockPipeEnergy extends BlockPipeBase {
	public BlockPipeEnergy(String name, Material material) {
		super(name, material);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, CONNECTED_PROPERTIES.toArray(new IProperty[CONNECTED_PROPERTIES.size()]));
	}

	@SuppressWarnings("deprecation")
	@Override
	public IBlockState getStateFromMeta(final int meta) {
		return getDefaultState();
	}

	@Override
	public int getMetaFromState(final IBlockState state) {
		return 0;
	}
}