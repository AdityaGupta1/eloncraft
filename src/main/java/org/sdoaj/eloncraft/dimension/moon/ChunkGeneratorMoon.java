package org.sdoaj.eloncraft.dimension.moon;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEntitySpawner;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.MapGenBase;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.event.terraingen.TerrainGen;
import org.sdoaj.eloncraft.blocks.ModBlocks;
import org.sdoaj.eloncraft.dimension.TerrainGeneratorNormal;
import org.sdoaj.eloncraft.world.CaveGenerator;
import org.sdoaj.eloncraft.world.WorldGenCrater;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class ChunkGeneratorMoon implements IChunkGenerator {
    private final World world;
    private Random random;
    private Biome[] biomes;

    private final MapGenBase caveGen = TerrainGen.getModdedMapGen(new CaveGenerator(ModBlocks.MOON_DIRT, ModBlocks.MOON_ROCK),
            InitMapGenEvent.EventType.CAVE);
    private final TerrainGeneratorNormal terrainGen = new TerrainGeneratorNormal();

    private final WorldGenCrater craterGen = new WorldGenCrater(0.00002, 5, 45)
            .setMeteor(ModBlocks.PALLASITE_ORE.getDefaultState(), 25, 2.0, 0.2);

    public ChunkGeneratorMoon(World world) {
        this.world = world;
        this.random = new Random((world.getSeed() + 516) * 314);
        terrainGen.setup(world, random);
    }

    @Override
    public Chunk generateChunk(int x, int z) {
        ChunkPrimer primer = new ChunkPrimer();

        this.biomes = this.world.getBiomeProvider().getBiomesForGeneration(this.biomes, x * 4 - 2, z * 4 - 2, 10, 10);
        terrainGen.setBiomes(biomes);
        terrainGen.generate(x, z, primer);

        this.biomes = this.world.getBiomeProvider().getBiomes(this.biomes, x * 16, z * 16, 16, 16);
        terrainGen.replaceBiomeBlocks(x, z, primer, this, biomes);

        this.caveGen.generate(this.world, x, z, primer);

        this.craterGen.generate(this.world.getSeed(), x, z, primer);

        Chunk chunk = new Chunk(this.world, primer, x, z);

        byte[] biomeArray = chunk.getBiomeArray();
        for (int i = 0; i < biomeArray.length; ++i) {
            biomeArray[i] = (byte) Biome.getIdForBiome(this.biomes[i]);
        }

        chunk.generateSkylightMap();
        return chunk;
    }

    @Override
    public void populate(int x, int z) {
        int i = x * 16;
        int j = z * 16;
        BlockPos pos = new BlockPos(i, 0, j);
        Biome biome = this.world.getBiome(pos.add(16, 0, 16));

        biome.decorate(this.world, this.random, pos);

        WorldEntitySpawner.performWorldGenSpawning(this.world, biome, i + 8, j + 8, 16, 16, this.random);
    }

    @Override
    public boolean generateStructures(Chunk chunkIn, int x, int z) {
        return false;
    }

    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
        Biome biome = this.world.getBiome(pos);
        return biome.getSpawnableList(creatureType);
    }

    @Nullable
    @Override
    public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored) {
        return null;
    }

    @Override
    public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) {
        return false;
    }

    @Override
    public void recreateStructures(Chunk chunkIn, int x, int z) {}
}
