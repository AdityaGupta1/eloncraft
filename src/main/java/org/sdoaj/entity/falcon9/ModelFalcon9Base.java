package org.sdoaj.entity.falcon9;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ModelFalcon9Base extends ModelBase {
    private final ModelRenderer body;
    private final List<ModelRenderer> legs = new ArrayList<>();
    private final List<ModelRenderer> grid_fins = new ArrayList<>();
    private final ModelRenderer thrusters;
    private final ModelRenderer engines;

    static float modelScale = 56.9f / (127.0f / 16.0f); // 56.9 meters (blocks) tall

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

        // legs
        legs.add(createBox(0.0F, 24.0F - 1.0F, -5.0F, renderer -> new ModelBox(renderer, 72, 42, -1.5F, -19.0F + 1.0F, -5.5F + 5.0F, 3, 18, 1, 0.0F, false)));
        legs.add(createBox(0.0F, 24.0F - 1.0F, 5.0F, renderer -> new ModelBox(renderer, 72, 61, -1.5F, -19.0F + 1.0F, 4.5F - 5.0F, 3, 18, 1, 0.0F, false)));
        legs.add(createBox(5.0F, 24.0F - 1.0F, 0.0F, renderer -> new ModelBox(renderer, 72, 21, 4.5F - 5.0F, -19.0F + 1.0F, -1.5F, 1, 18, 3, 0.0F, false)));
        legs.add(createBox(-5.0F, 24.0F - 1.0F, 0.0F, renderer -> new ModelBox(renderer, 72, 0, -5.5F + 5.0F, -19.0F + 1.0F, -1.5F, 1, 18, 3, 0.0F, false)));
        // non-telescoping arm part
        legs.add(createBox(0.0F, 24.0F - 1.0F - 9.0F, -5.0F, renderer -> new ModelBox(renderer, 123, 0, -0.5F, -19.0F + 1.0F + 9.0F, -5.5F + 5.0F, 1, 9, 1, 0.0F, false)));
        legs.add(createBox(0.0F, 24.0F - 1.0F - 9.0F, 5.0F, renderer -> new ModelBox(renderer, 123, 0, -0.5F, -19.0F + 1.0F + 9.0F, 4.5F - 5.0F, 1, 9, 1, 0.0F, false)));
        legs.add(createBox(5.0F, 24.0F - 1.0F - 9.0F, 0.0F, renderer -> new ModelBox(renderer, 123, 0, 4.5F - 5.0F, -19.0F + 1.0F + 9.0F, -0.5F, 1, 9, 1, 0.0F, false)));
        legs.add(createBox(-5.0F, 24.0F - 1.0F - 9.0F, 0.0F, renderer -> new ModelBox(renderer, 123, 0, -5.5F + 5.0F, -19.0F + 1.0F + 9.0F, -0.5F, 1, 9, 1, 0.0F, false)));
        // // telescoping arm part 1
        // legs.add(createBox(0.0F, 24.0F - 1.0F - 9.0F, -5.0F, renderer -> new ModelBox(renderer, 123, 0, -0.5F, -19.0F + 1.0F + 9.0F, -5.5F + 5.0F, 1, 9, 1, 0.0F, false)));
        // legs.add(createBox(0.0F, 24.0F - 1.0F - 9.0F, 5.0F, renderer -> new ModelBox(renderer, 123, 0, -0.5F, -19.0F + 1.0F + 9.0F, 4.5F - 5.0F, 1, 9, 1, 0.0F, false)));
        // legs.add(createBox(5.0F, 24.0F - 1.0F - 9.0F, 0.0F, renderer -> new ModelBox(renderer, 123, 0, 4.5F - 5.0F, -19.0F + 1.0F + 9.0F, -0.5F, 1, 9, 1, 0.0F, false)));
        // legs.add(createBox(-5.0F, 24.0F - 1.0F - 9.0F, 0.0F, renderer -> new ModelBox(renderer, 123, 0, -5.5F + 5.0F, -19.0F + 1.0F + 9.0F, -0.5F, 1, 9, 1, 0.0F, false)));
        // // telescoping arm part 2
        // legs.add(createBox(0.0F, 24.0F - 1.0F - 9.0F, -5.0F, renderer -> new ModelBox(renderer, 123, 0, -0.5F, -19.0F + 1.0F + 9.0F, -5.5F + 5.0F, 1, 9, 1, 0.0F, false)));
        // legs.add(createBox(0.0F, 24.0F - 1.0F - 9.0F, 5.0F, renderer -> new ModelBox(renderer, 123, 0, -0.5F, -19.0F + 1.0F + 9.0F, 4.5F - 5.0F, 1, 9, 1, 0.0F, false)));
        // legs.add(createBox(5.0F, 24.0F - 1.0F - 9.0F, 0.0F, renderer -> new ModelBox(renderer, 123, 0, 4.5F - 5.0F, -19.0F + 1.0F + 9.0F, -0.5F, 1, 9, 1, 0.0F, false)));
        // legs.add(createBox(-5.0F, 24.0F - 1.0F - 9.0F, 0.0F, renderer -> new ModelBox(renderer, 123, 0, -5.5F + 5.0F, -19.0F + 1.0F + 9.0F, -0.5F, 1, 9, 1, 0.0F, false)));
        setLegs(0.4);

        grid_fins.add(createBox(6.5F, -62.5F, 0.0F, renderer -> new ModelBox(renderer, 79, 38, -1.5F, 0.5F, -2.0F, 5, 1, 4, 0.0F, false)));
        grid_fins.add(createBox(-6.5F, -62.5F, 0.0F, renderer -> new ModelBox(renderer, 79, 57, -3.5F, 0.5F, -2.0F, 5, 1, 4, 0.0F, false)));
        grid_fins.add(createBox(0.0F, -62.5F, 6.5F, renderer -> new ModelBox(renderer, 75, 76, -2.0F, 0.5F, -1.5F, 4, 1, 5, 0.0F, false)));
        grid_fins.add(createBox(0.0F, -62.5F, -6.5F, renderer -> new ModelBox(renderer, 77, 16, -2.0F, 0.5F, -3.5F, 4, 1, 5, 0.0F, false)));
        setGridFins(0.0);

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

    private ModelRenderer createBox(float x, float y, float z, Function<ModelRenderer, ModelBox> box) {
        ModelRenderer renderer = new ModelRenderer(this);
        renderer.setRotationPoint(x, y, z);
        renderer.cubeList.add(box.apply(renderer));
        return renderer;
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        GL11.glPushMatrix();
        GL11.glTranslatef(0F, 1.5F - 1.5F * modelScale, 0F);
        GL11.glScalef(modelScale, modelScale, modelScale);

        body.render(scale);
        legs.forEach(renderer -> renderer.render(scale));
        grid_fins.forEach(renderer -> renderer.render(scale));
        thrusters.render(scale);
        engines.render(scale);

        GL11.glPopMatrix();
    }

    public void setLegs(double x) {
        x = MathHelper.clamp(x, 0.0, 1.0);
        float legAngle = (float) (Math.toRadians(113) * x);
        legs.get(0).rotateAngleX = legAngle;
        legs.get(1).rotateAngleX = -legAngle;
        legs.get(2).rotateAngleZ = legAngle;
        legs.get(3).rotateAngleZ = -legAngle;

        double c = Math.sqrt(9 * 9 + 18 * 18 - 2 * 9 * 18 * Math.cos(legAngle));
        float armAngle = (float) (Math.asin(18 * Math.sin(legAngle) / c));
        if (legAngle > Math.PI / 2) {
            armAngle = (float) (Math.PI - armAngle);
        }
        legs.get(4).rotateAngleX = armAngle;
        legs.get(5).rotateAngleX = -armAngle;
        legs.get(6).rotateAngleZ = armAngle;
        legs.get(7).rotateAngleZ = -armAngle;
        // for (int i = 0; i < 3; i ++) {
        //     legs.get(4 + (4 * i)).rotateAngleX = armAngle;
        //     legs.get(5 + (4 * i)).rotateAngleX = -armAngle;
        //     legs.get(6 + (4 * i)).rotateAngleZ = armAngle;
        //     legs.get(7 + (4 * i)).rotateAngleZ = -armAngle;
        // }
        //
        // double armAngleFromHorizontal = armAngle - Math.PI / 2;
        //
        // double maxExtend = Math.max(0, Math.min(9, c - 9));
        // float dx = (float) (maxExtend * Math.cos(armAngleFromHorizontal) / modelScale);
        // float dy = (float) (maxExtend * Math.sin(armAngleFromHorizontal) / modelScale);
        // legs.get(8).offsetZ = -dx;
        // legs.get(8).offsetY = dy;
        // legs.get(9).offsetZ = dx;
        // legs.get(9).offsetY = dy;
        // legs.get(10).offsetX = dx;
        // legs.get(10).offsetY = dy;
        // legs.get(11).offsetX = -dx;
        // legs.get(11).offsetY = dy;
        //
        // maxExtend = Math.max(0, Math.min(9, c - 18));
        // dx = (float) (maxExtend * Math.sin(armAngleFromHorizontal) * 18);
        // dy = (float) (maxExtend * Math.cos(armAngleFromHorizontal) * 18);
        // for (int i = 12; i <= 15; i++) {
        //     // legs.get(i).offsetY = -maxExtend;
        // }
    }

    public void setGridFins(double x) {
        x = MathHelper.clamp(x, 0.0, 1.0);
        float angle = (float) (Math.PI / 2 * (1 - x));
        grid_fins.get(0).rotateAngleZ = angle;
        grid_fins.get(1).rotateAngleZ = -angle;
        grid_fins.get(2).rotateAngleX = -angle;
        grid_fins.get(3).rotateAngleX = angle;
    }
}