// based on BlockGrinder from Actually Additions

package org.sdoaj.eloncraft.blocks.machines.alloyfurnace;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.sdoaj.eloncraft.Eloncraft;
import org.sdoaj.eloncraft.blocks.gui.GuiReference;
import org.sdoaj.eloncraft.blocks.machines.BlockMachine;

import java.util.Random;
import java.util.stream.Stream;

public class BlockAlloyFurnace extends BlockMachine {
    public BlockAlloyFurnace(String name, Material material) {
        super(name, material);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityAlloyFurnace();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random random) {
        if (state.getValue(IS_ON)) {
            double d0 = (double) pos.getX() + 0.5D;
            double d1 = (double) pos.getY() + random.nextDouble() * 6.0D / 16.0D;
            double d2 = (double) pos.getZ() + 0.5D;
            double d3 = 0.52D;
            double d4 = random.nextDouble() * 0.6D - 0.3D;

            if (random.nextDouble() < 0.1D) {
                world.playSound((double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            }

            Stream.of(EnumParticleTypes.SMOKE_NORMAL, EnumParticleTypes.FLAME).forEach(particle -> {
                world.spawnParticle(particle, d0 - d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                world.spawnParticle(particle, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                world.spawnParticle(particle, d0 + d4, d1, d2 - d3, 0.0D, 0.0D, 0.0D);
                world.spawnParticle(particle, d0 + d4, d1, d2 + d3, 0.0D, 0.0D, 0.0D);
            });

        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (world.isRemote) {
            return true;
        }

        TileEntityAlloyFurnace tileEntity = (TileEntityAlloyFurnace) world.getTileEntity(pos);

        if (tileEntity == null) {
            throw new RuntimeException("Missing tile entity!");
        }

        player.openGui(Eloncraft.INSTANCE, GuiReference.ALLOY_FURNACE, world, pos.getX(), pos.getY(), pos.getZ());

        return true;
    }
}