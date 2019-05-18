// based on BlockGrinder from Actually Additions

package org.sdoaj.blocks.launch.controller;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import org.sdoaj.blocks.gui.GuiReference;
import org.sdoaj.blocks.machines.BlockMachine;
import org.sdoaj.eloncraft.Main;
import org.sdoaj.util.StackUtil;

public class BlockLaunchController extends BlockMachine {
    public BlockLaunchController(String name, Material material) {
        super(name, material);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityLaunchController();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (world.isRemote) {
            return true;
        }

        TileEntityLaunchController tileEntity = (TileEntityLaunchController) world.getTileEntity(pos);

        if (tileEntity == null) {
            throw new RuntimeException("Missing tile entity!");
        }

        ItemStack heldItem = player.getHeldItem(hand);

        if (StackUtil.isValid(heldItem) && FluidUtil.getFluidHandler(heldItem) != null) {
            tileEntity.onUseBucket(player, hand);
        } else {
            player.openGui(Main.INSTANCE, GuiReference.LAUNCH_CONTROLLER, world, pos.getX(), pos.getY(), pos.getZ());
        }

        return true;
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        return 0;
    }
}