package org.sdoaj.eloncraft.entity.falcon9;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import org.sdoaj.eloncraft.Eloncraft;

public class RenderFalcon9DragonTrunk extends RenderLiving<EntityFalcon9DragonTrunk> {
    private ResourceLocation texture = new ResourceLocation(Eloncraft.MODID, "textures/entities/falcon9/dragon.png");

    public RenderFalcon9DragonTrunk(RenderManager manager) {
        super(manager, new ModelFalcon9DragonTrunk(), 0.0F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityFalcon9DragonTrunk entity) {
        return texture;
    }
}