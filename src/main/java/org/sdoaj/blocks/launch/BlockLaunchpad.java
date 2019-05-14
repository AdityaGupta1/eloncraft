package org.sdoaj.blocks.launch;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.sdoaj.eloncraft.Main;
import org.sdoaj.entity.falcon9.EntityFalcon9Base;
import org.sdoaj.blocks.BlockNotFull;
import org.sdoaj.blocks.ModBlocks;
import org.sdoaj.items.ModItems;

import java.util.*;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class BlockLaunchpad extends BlockNotFull {
    private static final int r = 3;

    private static final Block controllerBlock = Blocks.DIAMOND_BLOCK;

    public BlockLaunchpad(String name, Material material) {
        super(name, material, new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.25, 1.0));
    }

    private static HashMap<BlockPos, EntityFalcon9Base> rockets = new HashMap<>();

    private static Optional<BlockPos> getCenterLaunchpad(BlockPos pos) {
        for (BlockPos otherPos : rockets.keySet()) {
            if (Math.abs(pos.getX() - otherPos.getX()) <= 3 && pos.getY() == otherPos.getY() && Math.abs(pos.getZ() - otherPos.getZ()) <= 3) {
                return Optional.of(otherPos);
            }
        }

        return Optional.empty();
    }

    private void sendErrorMessage(Entity receiver, String message) {
        receiver.sendMessage(new TextComponentString(TextFormatting.RED + message));
    }

    private enum ErrorCode {
        NOT_LAUNCHPAD, OK, MISSING_CONTROLLER, TOO_MANY_CONTROLLERS, MISSING_LAUNCHPAD, OBSTRUCTED_SKY, ALREADY_USED
    }

    private ErrorCode isValid(World world, BlockPos pos) {
        if (world.getBlockState(pos) != this.getDefaultState()) {
            return ErrorCode.NOT_LAUNCHPAD;
        }

        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        List<BlockPos> possibleControllers = new ArrayList<>();
        possibleControllers.add(pos.add(3, 0, 0));
        possibleControllers.add(pos.add(-3, 0, 0));
        possibleControllers.add(pos.add(0, 0, 3));
        possibleControllers.add(pos.add(0, 0, -3));
        BlockPos controllerPos = null;
        for (BlockPos possiblePos : possibleControllers) {
            if (world.getBlockState(possiblePos).getBlock() == controllerBlock) {
                if (controllerPos != null) {
                    return ErrorCode.TOO_MANY_CONTROLLERS;
                }

                controllerPos = possiblePos;
            }
        }

        if (controllerPos == null) {
            return ErrorCode.MISSING_CONTROLLER;
        }

        if (!checkBlocks(world, new BlockPos(x - r, y, z - r), new BlockPos(x + r, y, z + r), ModBlocks.LAUNCHPAD, controllerPos)) {
            return ErrorCode.MISSING_LAUNCHPAD;
        }

        if (!checkBlocks(world, new BlockPos(x - r, y + 1, z - r), new BlockPos(x + r, world.getHeight(), z + r), Blocks.AIR)) {
            return ErrorCode.OBSTRUCTED_SKY;
        }

        for (int x1 = x - r; x1 <= x + r; x1++) {
            for (int z1 = z - r; z1 <= z + r; z1++) {
                // if a launchpad that this one would use as part of its r*r square is already part of a square, return error
                if (getCenterLaunchpad(new BlockPos(x1, y, z1)).isPresent()) {
                    return ErrorCode.ALREADY_USED;
                }
            }
        }

        return ErrorCode.OK;
    }

    private Optional<BlockPos> findNearestValidLaunchpad(World world, BlockPos pos) {
        for (int x = pos.getX() - r; x <= pos.getX() + r; x++) {
            for (int z = pos.getZ() - r; z <= pos.getZ() + r; z++) {
                BlockPos otherPos = new BlockPos(x, pos.getY(), z);
                ErrorCode error = isValid(world, otherPos);
                if (error == ErrorCode.OK  || error == ErrorCode.OBSTRUCTED_SKY) { // returns even if sky is obstructed
                    return Optional.of(otherPos);
                }
            }
        }

        return Optional.empty();
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

    private boolean checkBlocks(World world, BlockPos a, BlockPos b, Block block, BlockPos... skip) {
        List<BlockPos> skipList = skip == null ? new ArrayList<>() : Arrays.asList(skip);

        for (int x = a.getX(); x <= b.getX(); x++) {
            for (int y = a.getY(); y <= b.getY(); y++) {
                for (int z = a.getZ(); z <= b.getZ(); z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    if (skipList.contains(pos)) {
                        continue;
                    }

                    if (world.getBlockState(pos).getBlock() != block) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        Optional<BlockPos> center = getCenterLaunchpad(pos);
        // if this launchpad is already part of another launchpad's r*r square, activate that launchpad instead
        if (center.isPresent() && !center.get().equals(pos)) {
            return onBlockActivated(world, center.get(), state, player, hand, facing, hitX, hitY, hitZ);
        }

        if (world.isRemote || rockets.containsKey(pos) || player.getHeldItem(hand).getItem() != ModItems.FALCON9_BASE) {
            return false;
        }

        switch (isValid(world, pos)) {
            case MISSING_CONTROLLER:
                sendErrorMessage(player, "Missing launch controller!");
                return false;
            case TOO_MANY_CONTROLLERS:
                sendErrorMessage(player, "Too many launch controllers!");
                return false;
            case MISSING_LAUNCHPAD:
                Optional<BlockPos> nearest = findNearestValidLaunchpad(world, pos);
                if (nearest.isPresent()) {
                    return onBlockActivated(world, nearest.get(), state, player, hand, facing, hitX, hitY, hitZ);
                }

                final int d = r * 2 + 1;
                sendErrorMessage(player, "Missing launchpad(s)! Required platform size: " + d + "x" + d + "!");
                return false;
            case OBSTRUCTED_SKY:
                sendErrorMessage(player, "Space above launchpad is obstructed!");
                return false;
            case ALREADY_USED:
                sendErrorMessage(player, "Launchpad already has a rocket!");
                return false;
            default:
                break;
        }

        if (!player.isCreative()) {
            player.getHeldItem(hand).shrink(1);
        }

        EntityFalcon9Base rocket = new EntityFalcon9Base(world);
        rocket.setLaunchpad(pos);
        rockets.put(pos, rocket);
        world.spawnEntity(rocket);

        return true;
    }

    public static void addRocket(EntityFalcon9Base rocket, BlockPos pos) {
        rockets.put(pos, rocket);
    }

    public static void removeRocket(BlockPos pos) {
        rockets.remove(pos);
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return super.canPlaceBlockAt(world, pos) && world.isBlockFullCube(pos.add(0, -1, 0));
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block neighbor, BlockPos neighborPos) {
        super.onNeighborChange(world, pos, neighborPos);

        // break this block if it loses support underneath
        if (pos.add(0, -1, 0).equals(neighborPos) && world.getBlockState(neighborPos).getBlock() == Blocks.AIR) {
            world.destroyBlock(pos, true);
        }
    }

    @SubscribeEvent
    public static void onBroken(BlockEvent.BreakEvent event) {
        Block block = event.getState().getBlock();
        if (block == ModBlocks.LAUNCHPAD || block == controllerBlock) {
            getCenterLaunchpad(event.getPos()).ifPresent(centerPos -> rockets.remove(centerPos).setDead());
        }
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face) {
        return face == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
    }
}
