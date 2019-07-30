package org.sdoaj.eloncraft.dimension.moon;

import net.minecraft.init.Biomes;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.IChunkGenerator;
import org.sdoaj.eloncraft.dimension.ModDimensions;
import org.sdoaj.eloncraft.dimension.WorldProviderSpace;

public class WorldProviderMoon extends WorldProviderSpace {
    public WorldProviderMoon() {
        super(new BiomeProviderSingle(Biomes.JUNGLE));
    }

    @Override
    protected void init() {
        this.hasSkyLight = true;
        this.biomeProvider = new BiomeProviderSingle(Biomes.JUNGLE);
    }

    @Override
    public DimensionType getDimensionType() {
        return ModDimensions.MOON;
    }

    @Override
    public IChunkGenerator createChunkGenerator() {
        return new ChunkGeneratorMoon(world);
    }
}
