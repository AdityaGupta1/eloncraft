package org.sdoaj.items.blocks;

import net.minecraft.block.material.Material;
import org.sdoaj.items.Drops;

public class BlockOreStone extends BlockOre {
    public BlockOreStone(String name, Drops drops) {
        super(name, Material.ROCK, drops);
    }

    public BlockOreStone(String name) {
        this(name, null);
    }

    public BlockOreStone setHarvestLevel(int level) {
        setHarvestLevel("pickaxe", level);
        return this;
    }
}
