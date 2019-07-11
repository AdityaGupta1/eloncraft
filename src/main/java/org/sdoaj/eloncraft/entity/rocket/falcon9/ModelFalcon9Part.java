package org.sdoaj.eloncraft.entity.rocket.falcon9;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

import java.util.function.Function;

abstract class ModelFalcon9Part extends ModelBase {
    static final float modelScale = 56.9f / (127.0f / 16.0f); // first + second stages are 56.9 meters (blocks) tall in total

    private final Function<Float, Float> yawFunction;
    private final Function<Float, Float> pitchFunction;

    ModelFalcon9Part() {
        this.yawFunction = null;
        this.pitchFunction = null;
    }

    ModelFalcon9Part(Function<Float, Float> yawFunction, Function<Float, Float> pitchFunction) {
       this.yawFunction = yawFunction;
       this.pitchFunction = pitchFunction;
    }

    // sus
    private static final double x = 10.75;

    @Override
    public final void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float yaw, float pitch, float scale) {
        GL11.glPushMatrix();
        GL11.glTranslatef(0F, 1.5F - 1.5F * modelScale, 0F);

        // very sus way to make sure the models and hitboxes are in roughly the same place
        float yawActual = entity.rotationYaw;
        float pitchActual = entity.rotationPitch;
        double theta = 90 - pitchActual;
        double dy = x - x * Math.sin(Math.toRadians(theta));
        double dh = x * Math.cos(Math.toRadians(theta));
        double dx = dh * -Math.sin(Math.toRadians(yawActual));
        double dz = dh * -Math.cos(Math.toRadians(yawActual));
        GL11.glTranslated(dx, dy, dz);

        GL11.glScalef(modelScale, modelScale, modelScale);

        if (yawFunction != null) {
            yawActual = yawFunction.apply(yawActual);
        }
        GL11.glRotatef(yawActual, 0, 1, 0);

        if (pitchFunction != null) {
            pitchActual = pitchFunction.apply(pitchActual);
        }
        GL11.glRotatef(pitchActual, 1, 0, 0);

        renderActual(entity, limbSwing, limbSwingAmount, ageInTicks, yaw, pitch, scale);

        GL11.glPopMatrix();
    }

    protected abstract void renderActual(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float yaw, float pitch, float scale);
}
