package org.sdoaj.eloncraft.dimension;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.chunk.Chunk;

import javax.annotation.Nullable;

public abstract class WorldProviderSpace extends WorldProvider {
    public WorldProviderSpace(BiomeProvider biomeProvider) {
        this.biomeProvider = biomeProvider;
    }

    @Override
    protected void init() {
        this.hasSkyLight = true;
    }

    public Vec3d getSkyColor() {
        return new Vec3d(0, 0, 0);
    }

    @Override
    public final Vec3d getSkyColor(Entity cameraEntity, float partialTicks) {
        return getSkyColor();
    }

    @Nullable
    @Override
    public float[] calcSunriseSunsetColors(float celestialAngle, float partialTicks) {
        return null;
    }

    public Vec3d getFogColor() {
        return new Vec3d(0, 0, 0);
    }

    @Override
    public final Vec3d getFogColor(float p_76562_1_, float p_76562_2_) {
        return getFogColor();
    }

    @Override
    public boolean canDoRainSnowIce(Chunk chunk) {
        return false;
    }

    @Nullable
    @Override
    public String getSaveFolder() {
        return "DIM" + this.getDimension();
    }

    // returns true on client side for sky rendering
    @Override
    public boolean isSurfaceWorld() {
        return this.world != null && this.world.isRemote;
    }

    // setting this to true means beds do not explode
    @Override
    public boolean canRespawnHere() {
        return true;
    }

    @Override
    public int getRespawnDimension(EntityPlayerMP player) {
        return 0;
    }

    @Override
    public final boolean isNether() {
        return false;
    }
}
