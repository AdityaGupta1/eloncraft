package org.sdoaj.eloncraft.entity.rocket.falcon9;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import org.sdoaj.eloncraft.Eloncraft;

public class RenderFalcon9DragonTop extends RenderLiving<EntityFalcon9DragonTop> {
    private ResourceLocation texture = new ResourceLocation(Eloncraft.MODID, "textures/entities/falcon9/dragon.png");

    public RenderFalcon9DragonTop(RenderManager manager) {
        super(manager, new ModelFalcon9DragonTop(), 0.0F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityFalcon9DragonTop entity) {
        return texture;
    }
}