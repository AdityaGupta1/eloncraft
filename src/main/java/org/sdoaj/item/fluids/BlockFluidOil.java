package org.sdoaj.item.fluids;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidClassic;
import org.sdoaj.item.blocks.ModBlocks;

class BlockFluidOil extends BlockFluidClassic {
    BlockFluidOil() {
        super(ModFluids.OIL, Material.WATER);
        setRegistryName("oil");
        setUnlocalizedName(getRegistryName().toString());
        ModBlocks.addBlock(this);
    }

    void render() {
        ModelLoader.setCustomStateMapper(this, new StateMap.Builder().ignore(LEVEL).build());
    }
}