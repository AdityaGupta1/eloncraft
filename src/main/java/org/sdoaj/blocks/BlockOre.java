package org.sdoaj.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.sdoaj.items.util.Drops;

public class BlockOre extends BlockBasic {
    private final Drops drops;

    public BlockOre(String name, Material material, Drops drops) {
        super(name, material);
        this.drops = drops;
    }

    public BlockOre(String name, Material material) {
        this(name, material, null);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        if (this.drops == null) {
            drops.add(new ItemStack(this));
            return;
        }

        drops.addAll(this.drops.getDrops(fortune));
    }
}
