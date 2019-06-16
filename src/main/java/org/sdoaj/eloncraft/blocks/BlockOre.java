package org.sdoaj.eloncraft.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.sdoaj.eloncraft.items.util.Drops;
import org.sdoaj.eloncraft.util.StringUtil;

public class BlockOre extends BlockBasic {
    private final Drops drops;

    BlockOre(String name, Material material, Drops drops) {
        super(name, material);
        this.drops = drops;
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        if (this.drops == null) {
            drops.add(new ItemStack(this));
            return;
        }

        drops.addAll(this.drops.getDrops(fortune));
    }

    static BlockOre newStoneOre(String material, Drops drops, int harvestLevel, float hardness, float resistance) {
        BlockOre ore = new BlockOre(material + "_ore", Material.ROCK, drops);
        ore.setHardness(hardness).setResistance(resistance).setHarvestLevel("pickaxe", harvestLevel);
        ore.setOreDictName("ore" + StringUtil.capitalizeFirstLetter(material));
        return ore;
    }

    static BlockOre newStoneOre(String material, int harvestLevel, float hardness, float resistance) {
        return newStoneOre(material, null, harvestLevel, hardness, resistance);
    }
}
