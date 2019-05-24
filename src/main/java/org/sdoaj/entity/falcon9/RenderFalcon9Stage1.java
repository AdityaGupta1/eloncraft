package org.sdoaj.entity.falcon9;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import org.sdoaj.eloncraft.Main;

public class RenderFalcon9Stage1 extends RenderLiving<EntityFalcon9Stage1> {
    private ResourceLocation texture = new ResourceLocation(Main.MODID, "textures/entities/falcon9/falcon9_stage1.png");

    public RenderFalcon9Stage1(RenderManager manager) {
        super(manager, new ModelFalcon9Stage1(), 0.4f * ModelFalcon9Stage1.modelScale);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityFalcon9Stage1 entity) {
        return texture;
    }
}