package org.sdoaj.entity.falcon9;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;
import org.sdoaj.util.MathUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ModelFalcon9Stage1 extends ModelBase {
    private final ModelRenderer body;
    private final List<ModelRenderer> legs = new ArrayList<>();
    private final List<ModelRenderer> grid_fins = new ArrayList<>();
    private final ModelRenderer thrusters;
    private final ModelRenderer engines;

    static final float modelScale = 56.9f / (127.0f / 16.0f); // first + second stages are 56.9 meters (blocks) tall in total

    public ModelFalcon9Stage1() {
        textureWidth = 144;
        textureHeight = 144;

        body = new ModelRenderer(this);
        body.setRotationPoint(0.0F, 24.0F, 0.0F);
        // middle
        body.cubeList.add(new ModelBox(body, 0, 1, -4.0F, -126.0F + 28 + 16, -4.0F, 8, 125 - 28 - 16, 8, 0.0F, false));
        // sides
        body.cubeList.add(new ModelBox(body, 52, 0, -2.0F, -126.0F + 28, -5.0F, 4, 125 - 28, 1, 0.0F, false));
        body.cubeList.add(new ModelBox(body, 32, 0, -5.0F, -126.0F + 28, -2.0F, 1, 125 - 28, 4, 0.0F, false));
        body.cubeList.add(new ModelBox(body, 62, 0, -2.0F, -126.0F + 28, 4.0F, 4, 125 - 28, 1, 0.0F, false));
        body.cubeList.add(new ModelBox(body, 42, 0, 4.0F, -126.0F + 28, -2.0F, 1, 125 - 28, 4, 0.0F, false));
        // walls
        body.cubeList.add(new ModelBox(body, 0, 101, 1.0F, -98.0F, -4.0F, 3, 16, 1, 0.0F, false));
        body.cubeList.add(new ModelBox(body, 0, 101, 1.0F, -98.0F, 3.0F, 3, 16, 1, 0.0F, false));
        body.cubeList.add(new ModelBox(body, 0, 101, 3.0F, -98.0F, -4.0F, 1, 16, 3, 0.0F, false));
        body.cubeList.add(new ModelBox(body, 0, 101, -4.0F, -98.0F, -4.0F, 1, 16, 3, 0.0F, false));
        body.cubeList.add(new ModelBox(body, 0, 101, -4.0F, -98.0F, 1.0F, 1, 16, 3, 0.0F, false));
        body.cubeList.add(new ModelBox(body, 0, 101, 3.0F, -98.0F, 1.0F, 1, 16, 3, 0.0F, false));
        body.cubeList.add(new ModelBox(body, 0, 101, -4.0F, -98.0F, 3.0F, 3, 16, 1, 0.0F, false));
        body.cubeList.add(new ModelBox(body, 0, 101, -4.0F, -98.0F, -4.0F, 3, 16, 1, 0.0F, false));

        // legs
        legs.add(createBox(0.0F, 24.0F - 1.0F, -5.0F, renderer -> new ModelBox(renderer, 72, 42, -1.5F, -19.0F + 1.0F, -5.5F + 5.0F, 3, 18, 1, 0.0F, false)));
        legs.add(createBox(0.0F, 24.0F - 1.0F, 5.0F, renderer -> new ModelBox(renderer, 72, 61, -1.5F, -19.0F + 1.0F, 4.5F - 5.0F, 3, 18, 1, 0.0F, false)));
        legs.add(createBox(5.0F, 24.0F - 1.0F, 0.0F, renderer -> new ModelBox(renderer, 72, 21, 4.5F - 5.0F, -19.0F + 1.0F, -1.5F, 1, 18, 3, 0.0F, false)));
        legs.add(createBox(-5.0F, 24.0F - 1.0F, 0.0F, renderer -> new ModelBox(renderer, 72, 0, -5.5F + 5.0F, -19.0F + 1.0F, -1.5F, 1, 18, 3, 0.0F, false)));
        // arm parts (3 each side)
        for (int i = 0; i < 3; i++) {
            legs.add(createBox(0.0F, 24.0F - 1.0F - 9.0F, -4.99F, renderer -> new ModelBox(renderer, 123, 0, -0.5F, -19.0F + 1.0F + 9.0F, -5.5F + 5.0F, 1, 9, 1, 0.0F, false)));
            legs.add(createBox(0.0F, 24.0F - 1.0F - 9.0F, 4.99F, renderer -> new ModelBox(renderer, 123, 0, -0.5F, -19.0F + 1.0F + 9.0F, 4.5F - 5.0F, 1, 9, 1, 0.0F, false)));
            legs.add(createBox(4.99F, 24.0F - 1.0F - 9.0F, 0.0F, renderer -> new ModelBox(renderer, 123, 0, 4.5F - 5.0F, -19.0F + 1.0F + 9.0F, -0.5F, 1, 9, 1, 0.0F, false)));
            legs.add(createBox(-4.99F, 24.0F - 1.0F - 9.0F, 0.0F, renderer -> new ModelBox(renderer, 123, 0, -5.5F + 5.0F, -19.0F + 1.0F + 9.0F, -0.5F, 1, 9, 1, 0.0F, false)));
        }
        setLegs(0.0);

        grid_fins.add(createBox(6.5F, -62.5F, 0.0F, renderer -> new ModelBox(renderer, 79, 38, -1.5F, 0.5F, -2.0F, 5, 1, 4, 0.0F, false)));
        grid_fins.add(createBox(-6.5F, -62.5F, 0.0F, renderer -> new ModelBox(renderer, 79, 57, -3.5F, 0.5F, -2.0F, 5, 1, 4, 0.0F, false)));
        grid_fins.add(createBox(0.0F, -62.5F, 6.5F, renderer -> new ModelBox(renderer, 75, 76, -2.0F, 0.5F, -1.5F, 4, 1, 5, 0.0F, false)));
        grid_fins.add(createBox(0.0F, -62.5F, -6.5F, renderer -> new ModelBox(renderer, 77, 16, -2.0F, 0.5F, -3.5F, 4, 1, 5, 0.0F, false)));
        setGridFins(0.0);

        thrusters = new ModelRenderer(this);
        thrusters.setRotationPoint(0.0F, 24.0F, 0.0F);
        thrusters.cubeList.add(new ModelBox(thrusters, 0, 5, 3.0F, -85.0F, 3.0F, 2, 2, 2, 0.0F, false));
        thrusters.cubeList.add(new ModelBox(thrusters, 0, 1, -5.0F, -85.0F, -5.0F, 2, 2, 2, 0.0F, false));

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
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float yaw, float pitch, float scale) {
        GL11.glPushMatrix();
        GL11.glTranslatef(0F, 1.5F - 1.5F * modelScale, 0F);
        GL11.glScalef(modelScale, modelScale, modelScale);
        GL11.glRotatef(entity.rotationYaw, 0, 1, 0);
        GL11.glRotatef(entity.rotationPitch, 1, 0, 0);

        body.render(scale);
        legs.forEach(renderer -> renderer.render(scale));
        grid_fins.forEach(renderer -> renderer.render(scale));
        thrusters.render(scale);
        engines.render(scale);

        GL11.glPopMatrix();
    }

    private void setLegSegments(double extend, double armAngleFromHorizontal, int i) {
        float dx = (float) (extend * Math.cos(armAngleFromHorizontal));
        float dy = (float) (extend * Math.sin(armAngleFromHorizontal));
        legs.get(i).rotationPointZ = -4.99F - dx;
        legs.get(i + 1).rotationPointZ = 4.99F + dx;
        legs.get(i + 2).rotationPointX = 4.99F + dx;
        legs.get(i + 3).rotationPointX = -4.99F - dx;
        for (int j = i; j < i + 4; j++) {
            legs.get(j).rotationPointY = 24.0F - 1.0F - 9.0F + dy;
        }
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
        for (int i = 0; i < 3; i ++) {
            legs.get(4 + (4 * i)).rotateAngleX = armAngle;
            legs.get(5 + (4 * i)).rotateAngleX = -armAngle;
            legs.get(6 + (4 * i)).rotateAngleZ = armAngle;
            legs.get(7 + (4 * i)).rotateAngleZ = -armAngle;
        }

        double armAngleFromHorizontal = armAngle - Math.PI / 2;

        setLegSegments(MathUtil.limit(c - 9, 0, 9), armAngleFromHorizontal, 8);
        setLegSegments(9 + MathUtil.limit(c - 18, -9, 9), armAngleFromHorizontal, 12);
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