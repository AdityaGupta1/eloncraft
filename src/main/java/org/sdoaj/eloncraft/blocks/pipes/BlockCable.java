package org.sdoaj.eloncraft.blocks.pipes;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;

import java.util.Random;

public class BlockCable extends BlockPipeBase implements ITileEntityProvider {
	public BlockCable(String name, Material material) {
		super(name, material);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityCable();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, CONNECTED_PROPERTIES.toArray(new IProperty[0]));
	}

	@Override
	public IBlockState getStateFromMeta(final int meta) {
		return getDefaultState();
	}

	@Override
	public int getMetaFromState(final IBlockState state) {
		return 0;
	}

	@Override
	protected boolean isValidConnection(IBlockState ownState, IBlockState neighborState, IBlockAccess world, BlockPos ownPos, EnumFacing neighborDirection) {
		BlockPos neighborPos = ownPos.offset(neighborDirection);
		TileEntity tile = world.getTileEntity(neighborPos);

		if (tile == null) {
			return false;
		}

		return tile.getCapability(CapabilityEnergy.ENERGY, neighborDirection.getOpposite()) != null;
	}
}