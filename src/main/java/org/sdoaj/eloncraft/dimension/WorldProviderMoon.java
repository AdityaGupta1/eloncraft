package org.sdoaj.eloncraft.dimension;

import net.minecraft.init.Biomes;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.IChunkGenerator;

public class WorldProviderMoon extends WorldProvider {
    public WorldProviderMoon() {
        this.biomeProvider = new BiomeProviderSingle(Biomes.JUNGLE);
    }

    @Override
    public DimensionType getDimensionType() {
        return ModDimensions.MOON;
    }

    @Override
    public String getSaveFolder() {
        return "MOON";
    }

    @Override
    public IChunkGenerator createChunkGenerator() {
        return new ChunkGeneratorMoon(world);
    }
}
