// based on BlockGrinder from Actually Additions

package org.sdoaj.blocks.machines.workbench;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.sdoaj.blocks.gui.GuiReference;
import org.sdoaj.blocks.BlockContainerBase;
import org.sdoaj.eloncraft.Main;

public class BlockWorkbench extends BlockContainerBase {
    public BlockWorkbench(String name, Material material) {
        super(name, material);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityWorkbench();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (world.isRemote) {
            return true;
        }

        TileEntityWorkbench tileEntity = (TileEntityWorkbench) world.getTileEntity(pos);

        if (tileEntity == null) {
            throw new RuntimeException("Missing tile entity!");
        }

        player.openGui(Main.INSTANCE, GuiReference.WORKBENCH, world, pos.getX(), pos.getY(), pos.getZ());

        return true;
    }
}