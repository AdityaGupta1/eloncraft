// based on BlockGrinder from Actually Additions

package org.sdoaj.items.blocks.machines.metalroller;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.sdoaj.eloncraft.Main;
import org.sdoaj.eloncraft.Reference;
import org.sdoaj.items.blocks.machines.BlockContainerBase;

import java.util.Random;

public class BlockMetalRoller extends BlockContainerBase {
    public static final PropertyBool IS_ON = PropertyBool.create("on");

    public BlockMetalRoller(String name, Material material) {
        super(name, material);
        this.setTickRandomly(true);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityMetalRoller();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random random) {
        if (state.getValue(IS_ON)) {
            for (int i = 0; i < 5; i++) {
                double xRand = random.nextDouble() / 0.75D - 0.5D;
                double zRand = random.nextDouble() / 0.75D - 0.5D;
                world.spawnParticle(EnumParticleTypes.CRIT, (double) pos.getX() + 0.4F, (double) pos.getY() + 0.8F, (double) pos.getZ() + 0.4F, xRand, 0.5D, zRand);
            }
            world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, (double) pos.getX() + 0.5F, (double) pos.getY() + 1.0F, (double) pos.getZ() + 0.5F, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            TileEntityMetalRoller tileEntity = (TileEntityMetalRoller) world.getTileEntity(pos);
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