package org.sdoaj.entity.falcon9;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import org.sdoaj.eloncraft.Main;

public class RenderFalcon9Base extends RenderLiving<EntityFalcon9Base> {
    private ResourceLocation texture = new ResourceLocation(Main.MODID, "textures/entities/falcon9/falcon9_base.png");

    public RenderFalcon9Base(RenderManager manager) {
        super(manager, new ModelFalcon9Base(), 0.4f * ModelFalcon9Base.modelScale);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityFalcon9Base entity) {
        return texture;
    }
}