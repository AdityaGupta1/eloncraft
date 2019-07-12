package org.sdoaj.eloncraft.fluids;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.sdoaj.eloncraft.blocks.ModBlocks;

public class BlockFluid extends BlockFluidClassic {
    BlockFluid(ModFluid fluid) {
        super(fluid, Material.WATER);
        setUnlocalizedName(fluid.getName());
        setRegistryName(fluid.getName());
        ModBlocks.addBlock(this);
    }

    @SideOnly(Side.CLIENT)
    void render() {
        ModelLoader.setCustomStateMapper(this, new StateMap.Builder().ignore(LEVEL).build());
    }

    void setMaxFlowDistance(int distance) {
        this.quantaPerBlock = distance;
    }
}