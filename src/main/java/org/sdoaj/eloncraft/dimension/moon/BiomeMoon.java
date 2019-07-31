package org.sdoaj.eloncraft.dimension.moon;

import org.sdoaj.eloncraft.biome.BiomeBasic;
import org.sdoaj.eloncraft.blocks.ModBlocks;
import org.sdoaj.eloncraft.world.WorldGenCrater;

import java.util.List;

public class BiomeMoon extends BiomeBasic {
    public BiomeMoon() {
        super("moon", properties -> properties.setRainDisabled().setTemperature(1.0F));

        this.topBlock = ModBlocks.MOON_DIRT.getDefaultState();
        this.fillerBlock = ModBlocks.MOON_DIRT.getDefaultState();
        this.stoneBlock = ModBlocks.MOON_ROCK.getDefaultState();

        this.spawnableCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableMonsterList.clear();
        this.modSpawnableLists.values().forEach(List::clear);

        this.addWorldGenerator(new WorldGenCrater(0.1));
    }
}
