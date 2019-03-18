// based on BlockGrinder from Actually Additions

package org.sdoaj.items.blocks.machines.alloyfurnace;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.sdoaj.eloncraft.Main;
import org.sdoaj.eloncraft.Reference;
import org.sdoaj.items.blocks.machines.BlockContainerBase;

import java.util.Random;
import java.util.stream.Stream;

public class BlockAlloyFurnace extends BlockContainerBase {
    public static final PropertyBool IS_ON = PropertyBool.create("on");

    public BlockAlloyFurnace(String name, Material material) {
        super(name, material);
        this.setTickRandomly(true);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityAlloyFurnace();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random random) {
        if (state.getValue(IS_ON)) {
            double d0 = (double)pos.getX() + 0.5D;
            double d1 = (double)pos.getY() + random.nextDouble() * 6.0D / 16.0D;
            double d2 = (double)pos.getZ() + 0.5D;
            double d3 = 0.52D;
            double d4 = random.nextDouble() * 0.6D - 0.3D;

            if (random.nextDouble() < 0.1D) {
                world.playSound((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
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
        if (!world.isRemote) {
            TileEntityAlloyFurnace tileEntity = (TileEntityAlloyFurnace) world.getTileEntity(pos);
            if (tileEntity != null) {
                player.openGui(Main.INSTANCE, Reference.GUI_ALLOY_FURNACE, world, pos.getX(), pos.getY(), pos.getZ());
            }
            return true;
        }
        return true;
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
}