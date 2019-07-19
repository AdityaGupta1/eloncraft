package org.sdoaj.eloncraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.sdoaj.eloncraft.items.ModCreativeTabs;
import org.sdoaj.eloncraft.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class BlockBasic extends Block {
    private int burnTime = -1;

    private boolean isBeaconBase = false;

    public BlockBasic(String name, Material material) {
        super(material);
        setUnlocalizedName(name);
        setRegistryName(name);
        ModBlocks.addBlock(this);
        setCreativeTab(ModCreativeTabs.ELONCRAFT);
    }

    private final List<String> lore = new ArrayList<>();

    public void addLore(String lore) {
        this.lore.add(lore);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
        tooltip.addAll(lore);
    }

    public void setBurnTime(int burnTime) {
        this.burnTime = burnTime;
    }

    ItemBlockBasic getItemBlock() {
        return new ItemBlockBasic(this);
    }

    public static class ItemBlockBasic extends ItemBlock {
        private final BlockBasic block;

        private ItemBlockBasic(BlockBasic block) {
            super(block);
            this.block = block;
        }

        @Override
        public int getItemBurnTime(ItemStack itemStack) {
            return block.burnTime;
        }
    }

    BlockBasic setOreDictName(String name) {
        ModBlocks.setOreDictName(this, name);
        return this;
    }

    static BlockBasic newMetalBlock(String material, int harvestLevel, float hardness, float resistance) {
        BlockBasic block = new BlockBasic(material + "_block", Material.IRON);
        block.setHardness(hardness).setResistance(resistance).setHarvestLevel("pickaxe", harvestLevel);
        block.setOreDictName("block" + StringUtil.capitalizeFirstLetter(material));
        block.setBeaconBase();
        return block;
    }

    public void setBeaconBase() {
        this.isBeaconBase = true;
        addLore("Can be used as a beacon base");
    }

    @Override
    public boolean isBeaconBase(IBlockAccess world, BlockPos pos, BlockPos beacon) {
        return this.isBeaconBase;
    }

    public void setSound(SoundType sound) {
        this.setSoundType(sound);
    }
}