package org.sdoaj.eloncraft.blocks.launch;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.sdoaj.eloncraft.blocks.BlockNotFull;
import org.sdoaj.eloncraft.blocks.ModBlocks;
import org.sdoaj.eloncraft.Eloncraft;
import org.sdoaj.eloncraft.entity.rocket.falcon9.EntityFalcon9Stage1;
import org.sdoaj.eloncraft.fluids.ModFluids;
import org.sdoaj.eloncraft.items.ModItems;

import java.util.*;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = Eloncraft.MODID)
public class BlockLaunchpad extends BlockNotFull {
    private static final int r = 3;

    private static final Block controllerBlock = ModBlocks.LAUNCH_CONTROLLER;

    public BlockLaunchpad(String name, Material material) {
        super(name, material, new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.25, 1.0));
    }

    // <launchpad pos, rocket>
    private static HashMap<BlockPos, EntityFalcon9Stage1> rockets = new HashMap<>();

    public static Optional<BlockPos> getNearbyRocketLaunchpadPos(BlockPos pos) {
        for (BlockPos otherPos : rockets.keySet()) {
            if (Math.abs(pos.getX() - otherPos.getX()) <= 3 && pos.getY() == otherPos.getY() && Math.abs(pos.getZ() - otherPos.getZ()) <= 3) {
                return Optional.of(otherPos);
            }
        }

        return Optional.empty();
    }

    public static Optional<EntityFalcon9Stage1> getNearbyRocket(BlockPos pos) {
        return getNearbyRocketLaunchpadPos(pos).map(rockets::get);
    }

    private enum ErrorCode {
        OK(),
        NOT_LAUNCHPAD(),
        NO_SQUARES("No valid 7x7 squares found!"),
        IN_USE("No nearby 7x7 squares not already in use!"),
        CONTROLLER_NUMBER("Either not enough or too many launch controllers (should have only one)!"),
        CONTROLLER_PLACEMENT("Controller not placed correctly! Make sure it is at the center of an edge."),
        SKY_OBSTRUCTED("Sky is obstructed!");

        private final String message;

        ErrorCode(String message) {
            this.message = message;
        }

        ErrorCode() {
            this(null);
        }
    }

    private class Error {
        private final ErrorCode code;
        private final BlockPos pos;

        Error(BlockPos pos) {
            this.code = ErrorCode.OK;
            this.pos = pos;
        }

        Error(ErrorCode code) {
            this.code = code;
            this.pos = null;
        }

        public boolean isError() {
            return code != ErrorCode.OK;
        }

        public ErrorCode getCode() {
            return code;
        }

        public BlockPos getPos() {
            return pos;
        }

        public void sendMessage(Entity recipient) {
            recipient.sendMessage(new TextComponentString(TextFormatting.RED + this.code.message));
        }
    }

    private Error findValidPos(World world, BlockPos pos) {
        if (world.getBlockState(pos) != this.getDefaultState()) {
            return new Error(ErrorCode.NOT_LAUNCHPAD);
        }

        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        List<BlockPos> validCenters = new ArrayList<>();

        for (int x1 = x - r; x1 <= x + r; x1++) {
            for (int z1 = z - r; z1 <= z + r; z1++) {
                validCenters.add(new BlockPos(x1, y, z1));
            }
        }

        validCenters.removeIf(center -> !checkBlocks(world, center, ModBlocks.LAUNCHPAD, controllerBlock));

        if (validCenters.isEmpty()) {
            return new Error(ErrorCode.NO_SQUARES);
        }

        validCenters.removeIf(center -> getBlockPosAround(world, center).stream().anyMatch(block ->
                getNearbyRocketLaunchpadPos(block).isPresent()));

        if (validCenters.isEmpty()) {
            return new Error(ErrorCode.IN_USE);
        }

        validCenters.removeIf(center -> getBlocksAround(world, center).stream().filter(block -> block == controllerBlock)
                .count() != 1);

        if (validCenters.isEmpty()) {
            return new Error(ErrorCode.CONTROLLER_NUMBER);
        }

        validCenters.removeIf(center -> {
            BlockPos[] validControllers = {
                    center.add(r, 0, 0),
                    center.add(-r, 0, 0),
                    center.add(0, 0, r),
                    center.add(0, 0, -r)
            };

            for (BlockPos controllerPos : validControllers) {
                // there should only be one controller in the square, so if it's in the right position, the entire square is valid
                if (world.getBlockState(controllerPos).getBlock() == controllerBlock) {
                    return false; // false = don't remove
                }
            }

            return true; // true = remove
        });

        if (validCenters.isEmpty()) {
            return new Error(ErrorCode.CONTROLLER_PLACEMENT);
        }

        validCenters.removeIf(center -> !isSkyClear(world, center));

        if (validCenters.isEmpty()) {
            return new Error(ErrorCode.SKY_OBSTRUCTED);
        }

        return new Error(validCenters.get(0));
    }

    public static boolean isSkyClear(World world, BlockPos pos) {
        return getBlocksBetween(world, pos.add(-r, 1, -r),
                new BlockPos(pos.getX() + r, 256, pos.getZ() + r)).stream()
                .allMatch(block -> block == Blocks.AIR);
    }

    private static List<BlockPos> getBlockPosBetween(BlockPos a, BlockPos b) {
        List<BlockPos> blocks = new ArrayList<>();

        for (int x = a.getX(); x <= b.getX(); x++) {
            for (int y = a.getY(); y <= b.getY(); y++) {
                for (int z = a.getZ(); z <= b.getZ(); z++) {
                    blocks.add(new BlockPos(x, y, z));
                }
            }
        }

        return blocks;
    }

    private static List<Block> getBlocksBetween(World world, BlockPos a, BlockPos b) {
        return getBlockPosBetween(a, b).stream().map(pos -> world.getBlockState(pos).getBlock())
                .collect(Collectors.toList());
    }

    private static List<BlockPos> getBlockPosAround(World world, BlockPos center) {
        return getBlockPosBetween(center.add(-r, 0, -r), center.add(r, 0, r));
    }

    private static List<Block> getBlocksAround(World world, BlockPos center) {
        return getBlocksBetween(world, center.add(-r, 0, -r), center.add(r, 0, r));
    }

    private static boolean checkBlocks(World world, BlockPos a, BlockPos b, Block... validBlocks) {
        return Arrays.asList(validBlocks).containsAll(getBlocksBetween(world, a, b));
    }

    private static boolean checkBlocks(World world, BlockPos center, Block... validBlocks) {
        return Arrays.asList(validBlocks).containsAll(getBlocksAround(world, center));
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        Optional<BlockPos> center = getNearbyRocketLaunchpadPos(pos);
        // if this launchpad is already part of another launchpad's r*r square, activate that launchpad instead
        if (center.isPresent() && !center.get().equals(pos)) {
            return onBlockActivated(world, center.get(), state, player, hand, facing, hitX, hitY, hitZ);
        }

        Item heldItem = player.getHeldItem(hand).getItem();

        if (world.isRemote || (heldItem != ModItems.FALCON9 && heldItem != ModItems.FALCON9_FUELED)) {
            return false;
        }

        Error error = findValidPos(world, pos);

        if (error.isError()) {
            error.sendMessage(player);
            return false;
        }

        if (!player.isCreative()) {
            player.getHeldItem(hand).shrink(1);
        }

        BlockPos centerPos = error.getPos();
        EntityFalcon9Stage1 rocket = new EntityFalcon9Stage1(world);
        rocket.setLaunchpad(centerPos, player.getHorizontalFacing().getHorizontalAngle());
        rockets.put(centerPos, rocket);

        if (heldItem == ModItems.FALCON9_FUELED) {
            rocket.fuelTank.fill(new FluidStack(ModFluids.RP1, EntityFalcon9Stage1.tankCapacity), true);
            rocket.oxygenTank.fill(new FluidStack(ModFluids.LOX, EntityFalcon9Stage1.tankCapacity), true);
        }

        world.spawnEntity(rocket);

        return true;
    }

    public static void addRocket(EntityFalcon9Stage1 rocket, BlockPos pos) {
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
            getNearbyRocketLaunchpadPos(event.getPos()).ifPresent(centerPos -> rockets.remove(centerPos).setDead());
        }
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face) {
        return face == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
    }
}
