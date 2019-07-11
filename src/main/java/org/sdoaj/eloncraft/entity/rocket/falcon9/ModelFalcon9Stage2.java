package org.sdoaj.eloncraft.entity.rocket.falcon9;

import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelFalcon9Stage2 extends ModelFalcon9Part {
	private final ModelRenderer engine;
	private final ModelRenderer body;

	ModelFalcon9Stage2() {
		textureWidth = 128;
		textureHeight = 128;

		engine = new ModelRenderer(this);
		engine.setRotationPoint(0.0F, 24.0F, 0.0F);
		engine.cubeList.add(new ModelBox(engine, 10, 68, -2.0F, -2.0F, -2.0F, 4, 2, 4, 0.0F, false));
		engine.cubeList.add(new ModelBox(engine, 10, 74, -1.5F, -3.0F, -1.5F, 3, 1, 3, 0.0F, false));
		engine.cubeList.add(new ModelBox(engine, 0, 97, -1.0F, -4.0F, -1.0F, 2, 1, 2, 0.0F, false));

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 24.0F, 0.0F);
		body.cubeList.add(new ModelBox(body, 0, 0, -4.0F, -32.0F, -4.0F, 8, 28, 8, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 0, 68, -2.0F, -32.0F, -5.0F, 4, 28, 1, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 10, 36, -2.0F, -32.0F, 4.0F, 4, 28, 1, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 0, 36, 4.0F, -32.0F, -2.0F, 1, 28, 4, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 32, 0, -5.0F, -32.0F, -2.0F, 1, 28, 4, 0.0F, false));
	}

	@Override
	public void renderActual(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float yaw, float pitch, float scale) {
		engine.render(scale);
		body.render(scale);
	}
}