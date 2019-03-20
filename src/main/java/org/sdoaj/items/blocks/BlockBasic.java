package org.sdoaj.items.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import org.sdoaj.items.ModCreativeTabs;

public class BlockBasic extends Block {
    public BlockBasic(String name, Material material) {
        super(material);
        setUnlocalizedName(name);
        setRegistryName(name);
        ModBlocks.addBlock(this);
        setCreativeTab(ModCreativeTabs.ELONCRAFT);
    }
}