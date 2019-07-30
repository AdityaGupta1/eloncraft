package org.sdoaj.eloncraft.dimension.moon;

import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.IChunkGenerator;
import org.sdoaj.eloncraft.biome.ModBiomes;
import org.sdoaj.eloncraft.dimension.ModDimensions;
import org.sdoaj.eloncraft.dimension.WorldProviderSpace;

public class WorldProviderMoon extends WorldProviderSpace {
    public WorldProviderMoon() {
        super(new BiomeProviderSingle(ModBiomes.MOON));
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
