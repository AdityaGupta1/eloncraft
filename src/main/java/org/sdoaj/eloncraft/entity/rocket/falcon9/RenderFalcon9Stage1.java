package org.sdoaj.eloncraft.entity.rocket.falcon9;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import org.sdoaj.eloncraft.Eloncraft;

public class RenderFalcon9Stage1 extends RenderLiving<EntityFalcon9Stage1> {
    private ResourceLocation texture = new ResourceLocation(Eloncraft.MODID, "textures/entities/falcon9/stage1.png");

    public RenderFalcon9Stage1(RenderManager manager) {
        super(manager, new ModelFalcon9Stage1(), 0.4F * ModelFalcon9Stage1.modelScale);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityFalcon9Stage1 entity) {
        return texture;
    }
}