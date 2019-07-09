package org.sdoaj.eloncraft.blocks.pipes;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.sdoaj.eloncraft.blocks.BlockBasic;
import org.sdoaj.eloncraft.blocks.BlockNotFull;
import org.sdoaj.eloncraft.fluids.BlockFluid;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class BlockPipeBase extends BlockNotFull {
    public static final float PIPE_MIN_POS = 0.25f;
    public static final float PIPE_MAX_POS = 0.75f;

    public static final List<IProperty<Boolean>> CONNECTED_PROPERTIES = Stream.of(EnumFacing.VALUES)
            .map(facing -> PropertyBool.create(facing.getName()))
            .collect(Collectors.toList());

    public static final List<AxisAlignedBB> CONNECTED_BOUNDING_BOXES = new ArrayList<>();

    static {
        for (EnumFacing facing : EnumFacing.VALUES) {
            Vec3i vector = facing.getDirectionVec();
            CONNECTED_BOUNDING_BOXES.add(new AxisAlignedBB(
                    getMinBound(vector.getX()), getMinBound(vector.getY()), getMinBound(vector.getZ()),
                    getMaxBound(vector.getX()), getMaxBound(vector.getY()), getMaxBound(vector.getZ())
            ));
        }
    }

    private static float getMinBound(int direction) {
        return direction == -1 ? 0 : PIPE_MIN_POS;
    }

    private static float getMaxBound(int direction) {
        return direction == 1 ? 1 : PIPE_MAX_POS;
    }

    public BlockPipeBase(String name, Material material) {
        super(name, material);
    }

    protected boolean isValidConnection(IBlockState ownState, IBlockState neighborState, IBlockAccess world, BlockPos ownPos, EnumFacing neighborDirection) {
        return neighborState.getBlock() instanceof BlockPipeBase;
    }

    private boolean canConnectTo(IBlockState ownState, IBlockAccess world, BlockPos ownPos, EnumFacing neighborDirection) {
        BlockPos neighborPos = ownPos.offset(neighborDirection);
        IBlockState neighborState = world.getBlockState(neighborPos);
        Block neighborBlock = neighborState.getBlock();

        boolean neighborIsValidForThis = isValidConnection(ownState, neighborState, world, ownPos, neighborDirection);
        boolean thisIsValidForNeighbor = !(neighborBlock instanceof BlockPipeBase) || ((BlockPipeBase) neighborBlock).isValidConnection(neighborState, ownState, world, neighborPos, neighborDirection.getOpposite());

        return neighborIsValidForThis && thisIsValidForNeighbor;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        for (EnumFacing facing : EnumFacing.VALUES) {
            state = state.withProperty(CONNECTED_PROPERTIES.get(facing.getIndex()), canConnectTo(state, world, pos, facing));
        }

        return state;
    }

    public boolean isConnected(IBlockState state, EnumFacing facing) {
        return state.getValue(CONNECTED_PROPERTIES.get(facing.getIndex()));
    }

    private List<AxisAlignedBB> getBoundingBoxes(IBlockState state) {
        List<AxisAlignedBB> boxes = new ArrayList<>();

        boxes.add(new AxisAlignedBB(PIPE_MIN_POS, PIPE_MIN_POS, PIPE_MIN_POS, PIPE_MAX_POS, PIPE_MAX_POS, PIPE_MAX_POS));

        for (EnumFacing facing : EnumFacing.VALUES) {
            if (isConnected(state, facing)) {
                boxes.add(CONNECTED_BOUNDING_BOXES.get(facing.getIndex()));
            }
        }

        return boxes;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entity, boolean isActualState) {
        if (!isActualState) {
            state = state.getActualState(world, pos);
        }

        getBoundingBoxes(state).forEach(box -> addCollisionBoxToList(pos, entityBox, collidingBoxes, box));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        state = state.getActualState(world, pos);
        List<AxisAlignedBB> boxes = getBoundingBoxes(state);
        AxisAlignedBB box = boxes.get(0);

        for (AxisAlignedBB addBox : boxes) {
            box = box.union(addBox);
        }

        return box;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }
}