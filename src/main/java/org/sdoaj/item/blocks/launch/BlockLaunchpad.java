package org.sdoaj.item.blocks.launch;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import org.sdoaj.entity.falcon9.EntityFalcon9Base;
import org.sdoaj.entity.falcon9.IRocketController;
import org.sdoaj.item.blocks.BlockNotFull;
import org.sdoaj.item.blocks.ModBlocks;
import org.sdoaj.item.items.ModItems;

public class BlockLaunchpad extends BlockNotFull implements IRocketController {
    public BlockLaunchpad(String name, Material material) {
        super(name, material, new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.25, 1.0));
    }

    private EntityFalcon9Base rocket = null;
    private Vec3d rocketPos = null;

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (rocket != null) {
            return false;
        }

        if (player.getHeldItem(hand).getItem() != ModItems.FALCON9_BASE) {
            return false;
        }

        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        if (!world.isRemote) {
            return false;
        }

        if (!checkBlocks(world, new BlockPos(x - 3, y - 1, z - 3), new BlockPos(x + 3, y - 1, z + 3), Blocks.OBSIDIAN)) {
            player.sendMessage(new TextComponentString(TextFormatting.RED + "Missing obsidian!"));
            return false;
        }

        if (!checkBlocks(world, new BlockPos(x - 3, y, z - 3), new BlockPos(x + 3, y, z + 3), ModBlocks.LAUNCHPAD)) {
            player.sendMessage(new TextComponentString(TextFormatting.RED + "Missing launchpad!"));
            return false;
        }

        if (!checkBlocks(world, new BlockPos(x - 3, y + 1, z - 3), new BlockPos(x + 3, y + 1, z + 3), Blocks.AIR)) {
            player.sendMessage(new TextComponentString(TextFormatting.RED + "Space above launchpad is obstructed!"));
            return false;
        }

        if (!player.isCreative()) {
            player.getHeldItem(hand).shrink(1);
        }

        rocketPos = new Vec3d(pos).addVector(0.5, 0.25, 0.5);
        rocket = new EntityFalcon9Base(world, this);
        world.spawnEntity(this.rocket);

        return true;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        if (rocket != null) {
            rocket.setDead();
            rocket = null;
        }
    }

    private boolean checkBlocks(World world, BlockPos a, BlockPos b, Block block) {
        for (int x = a.getX(); x <= b.getX(); x++) {
            for (int y = a.getY(); y <= b.getY(); y++) {
                for (int z = a.getZ(); z <= b.getZ(); z++) {
                    if (world.getBlockState(new BlockPos(x, y, z)).getBlock() != block) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    @Override
    public Vec3d getPos() {
        return rocketPos;
    }
}
