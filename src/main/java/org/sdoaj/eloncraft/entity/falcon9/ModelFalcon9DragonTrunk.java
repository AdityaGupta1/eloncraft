package org.sdoaj.eloncraft.entity.falcon9;

import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class ModelFalcon9DragonTrunk extends ModelFalcon9Part {
	private final ModelRenderer trunk;

	ModelFalcon9DragonTrunk() {
		textureWidth = 80;
		textureHeight = 80;

		trunk = new ModelRenderer(this);
		trunk.setRotationPoint(0.0F, 24.0F, 0.0F);
		trunk.cubeList.add(new ModelBox(trunk, 0, 0, -4.0F, -9.0F, -4.0F, 8, 9, 8, 0.0F, false));
		trunk.cubeList.add(new ModelBox(trunk, 28, 17, 4.0F, -9.0F, -2.0F, 1, 9, 4, 0.0F, false));
		trunk.cubeList.add(new ModelBox(trunk, 0, 30, -2.0F, -9.0F, 4.0F, 4, 9, 1, 0.0F, false));
		trunk.cubeList.add(new ModelBox(trunk, 18, 17, -5.0F, -9.0F, -2.0F, 1, 9, 4, 0.0F, false));
		trunk.cubeList.add(new ModelBox(trunk, 10, 30, -2.0F, -9.0F, -5.0F, 4, 9, 1, 0.0F, false));
		trunk.cubeList.add(new ModelBox(trunk, 4, 51, 5.0F, -6.0F, -0.5F, 1, 6, 1, 0.0F, false));
		trunk.cubeList.add(new ModelBox(trunk, 6, 58, -0.5F, -6.0F, 5.0F, 1, 6, 1, 0.0F, false));
		trunk.cubeList.add(new ModelBox(trunk, 10, 58, -6.0F, -6.0F, -0.5F, 1, 6, 1, 0.0F, false));
		trunk.cubeList.add(new ModelBox(trunk, 0, 51, -0.5F, -6.0F, -6.0F, 1, 6, 1, 0.0F, false));
	}

	@Override
	public void renderActual(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float yaw, float pitch, float scale) {
		trunk.render(scale);
	}
}