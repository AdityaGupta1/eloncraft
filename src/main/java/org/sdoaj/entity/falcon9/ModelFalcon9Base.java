package org.sdoaj.entity.falcon9;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class ModelFalcon9Base extends ModelBase {
    private final ModelRenderer body;
    private final ModelRenderer legs;
    private final ModelRenderer grid_fins;
    private final ModelRenderer thrusters;
    private final ModelRenderer engines;

    static float modelScale = 9f;

    public ModelFalcon9Base() {
        textureWidth = 144;
        textureHeight = 144;

        body = new ModelRenderer(this);
        body.setRotationPoint(0.0F, 24.0F, 0.0F);
        body.cubeList.add(new ModelBox(body, 0, 1, -4.0F, -126.0F, -4.0F, 8, 125, 8, 0.0F, false));
        body.cubeList.add(new ModelBox(body, 52, 0, -2.0F, -126.0F, -5.0F, 4, 125, 1, 0.0F, false));
        body.cubeList.add(new ModelBox(body, 32, 0, -5.0F, -126.0F, -2.0F, 1, 125, 4, 0.0F, false));
        body.cubeList.add(new ModelBox(body, 62, 0, -2.0F, -126.0F, 4.0F, 4, 125, 1, 0.0F, false));
        body.cubeList.add(new ModelBox(body, 42, 0, 4.0F, -126.0F, -2.0F, 1, 125, 4, 0.0F, false));

        legs = new ModelRenderer(this);
        legs.setRotationPoint(0.0F, 24.0F, 0.0F);
        legs.cubeList.add(new ModelBox(legs, 72, 42, -1.5F, -19.0F, -5.5F, 3, 18, 1, 0.0F, false));
        legs.cubeList.add(new ModelBox(legs, 72, 61, -1.5F, -19.0F, 4.5F, 3, 18, 1, 0.0F, false));
        legs.cubeList.add(new ModelBox(legs, 72, 21, 4.5F, -19.0F, -1.5F, 1, 18, 3, 0.0F, false));
        legs.cubeList.add(new ModelBox(legs, 72, 0, -5.5F, -19.0F, -1.5F, 1, 18, 3, 0.0F, false));

        grid_fins = new ModelRenderer(this);
        grid_fins.setRotationPoint(0.0F, 24.0F, 0.0F);
        grid_fins.cubeList.add(new ModelBox(grid_fins, 77, 16, -2.0F, -87.0F, -10.0F, 4, 1, 5, 0.0F, false));
        grid_fins.cubeList.add(new ModelBox(grid_fins, 79, 57, -10.0F, -87.0F, -2.0F, 5, 1, 4, 0.0F, false));
        grid_fins.cubeList.add(new ModelBox(grid_fins, 75, 76, -2.0F, -87.0F, 5.0F, 4, 1, 5, 0.0F, false));
        grid_fins.cubeList.add(new ModelBox(grid_fins, 79, 38, 5.0F, -87.0F, -2.0F, 5, 1, 4, 0.0F, false));

        thrusters = new ModelRenderer(this);
        thrusters.setRotationPoint(0.0F, 24.0F, 0.0F);
        thrusters.cubeList.add(new ModelBox(thrusters, 0, 5, 3.0F, -88.0F, 3.0F, 2, 2, 2, 0.0F, false));
        thrusters.cubeList.add(new ModelBox(thrusters, 0, 1, -5.0F, -88.0F, -5.0F, 2, 2, 2, 0.0F, false));

        engines = new ModelRenderer(this);
        engines.setRotationPoint(0.0F, 24.0F, 0.0F);
        engines.cubeList.add(new ModelBox(engines, 30, 0, -0.5F, -1.0F, -0.5F, 1, 1, 1, 0.0F, false));
        engines.cubeList.add(new ModelBox(engines, 24, 4, -3.5F, -1.0F, -1.75F, 1, 1, 1, 0.0F, false));
        engines.cubeList.add(new ModelBox(engines, 24, 2, -3.5F, -1.0F, 0.75F, 1, 1, 1, 0.0F, false));
        engines.cubeList.add(new ModelBox(engines, 27, 7, -1.75F, -1.0F, 2.5F, 1, 1, 1, 0.0F, false));
        engines.cubeList.add(new ModelBox(engines, 24, 0, 0.75F, -1.0F, 2.5F, 1, 1, 1, 0.0F, false));
        engines.cubeList.add(new ModelBox(engines, 27, 5, 2.5F, -1.0F, 0.75F, 1, 1, 1, 0.0F, false));
        engines.cubeList.add(new ModelBox(engines, 24, 6, 2.5F, -1.0F, -1.75F, 1, 1, 1, 0.0F, false));
        engines.cubeList.add(new ModelBox(engines, 27, 1, 0.75F, -1.0F, -3.5F, 1, 1, 1, 0.0F, false));
        engines.cubeList.add(new ModelBox(engines, 27, 3, -1.75F, -1.0F, -3.5F, 1, 1, 1, 0.0F, false));
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        GL11.glPushMatrix();
        GL11.glTranslatef(0F, 1.5F - 1.5F * modelScale, 0F);
        GL11.glScalef(modelScale, modelScale, modelScale);

        body.render(scale);
        legs.render(scale);
        grid_fins.render(scale);
        thrusters.render(scale);
        engines.render(scale);

        GL11.glPopMatrix();
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}