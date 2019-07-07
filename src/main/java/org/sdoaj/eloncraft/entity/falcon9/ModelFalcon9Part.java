package org.sdoaj.eloncraft.entity.falcon9;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

import java.util.function.Function;

class ModelFalcon9Part extends ModelBase {
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

    @Override
    public final void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float yaw, float pitch, float scale) {
        GL11.glPushMatrix();
        GL11.glTranslatef(0F, 1.5F - 1.5F * modelScale, 0F);
        GL11.glScalef(modelScale, modelScale, modelScale);

        float yawActual = entity.rotationYaw + yaw;
        if (yawFunction != null) {
            yawActual = yawFunction.apply(yawActual);
        }
        GL11.glRotatef(yawActual, 0, 1, 0);
        float pitchActual = entity.rotationPitch;
        if (pitchFunction != null) {
            pitchActual = pitchFunction.apply(pitchActual);
        }
        GL11.glRotatef(pitchActual, 1, 0, 0);

        renderActual(entity, limbSwing, limbSwingAmount, ageInTicks, yaw, pitch, scale);

        GL11.glPopMatrix();
    }

    protected void renderActual(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float yaw, float pitch, float scale) {}
}
