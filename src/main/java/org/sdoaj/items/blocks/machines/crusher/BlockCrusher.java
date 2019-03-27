// based on BlockGrinder from Actually Additions

package org.sdoaj.items.blocks.machines.crusher;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.sdoaj.eloncraft.Main;
import org.sdoaj.items.blocks.gui.GuiReference;
import org.sdoaj.items.blocks.machines.BlockMachine;

import java.util.Random;

public class BlockCrusher extends BlockMachine {
    public BlockCrusher(String name, Material material) {
        super(name, material);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityCrusher();
    }

    private double randomCoordinateAdd(Random random) {
        return (random.nextDouble() - 0.5) * 0.75;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random random) {
        if (state.getValue(IS_ON)) {
            for (int i = 0; i < 3; i++) {
                world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 0.5 + randomCoordinateAdd(random),
                        pos.getY() + 1.1, pos.getZ() + 0.5 + randomCoordinateAdd(random), 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            TileEntityCrusher tileEntity = (TileEntityCrusher) world.getTileEntity(pos);
            if (tileEntity != null) {
                player.openGui(Main.INSTANCE, GuiReference.CRUSHER, world, pos.getX(), pos.getY(), pos.getZ());
            }
            return true;
        }
        return true;
    }
}