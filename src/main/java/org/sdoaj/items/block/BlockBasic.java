package org.sdoaj.items.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockBasic extends Block {
    public BlockBasic(String name, Material material) {
        super(material);
        setUnlocalizedName(name);
        setRegistryName(name);
        ModBlocks.addBlock(this);
    }
}