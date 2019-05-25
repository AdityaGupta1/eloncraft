package org.sdoaj.entity.falcon9;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import org.sdoaj.eloncraft.Main;

public class RenderFalcon9Stage2 extends RenderLiving<EntityFalcon9Stage2> {
    private ResourceLocation texture = new ResourceLocation(Main.MODID, "textures/entities/falcon9/stage2.png");

    public RenderFalcon9Stage2(RenderManager manager) {
        super(manager, new ModelFalcon9Stage2(), 0.0F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityFalcon9Stage2 entity) {
        return texture;
    }
}