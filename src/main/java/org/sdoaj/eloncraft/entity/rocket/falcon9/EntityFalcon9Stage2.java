package org.sdoaj.eloncraft.entity.rocket.falcon9;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.sdoaj.eloncraft.entity.rocket.EntityRocketPart;

public class EntityFalcon9Stage2 extends EntityRocketPart {
    EntityFalcon9Stage2(World world) {
        super(world);
        this.setSize(0.5F * ModelFalcon9Stage1.modelScale, 32.0F / 16.0F * ModelFalcon9Stage1.modelScale);
    }

    @Override
    public void updatePassenger(Entity passenger) {
        double d = 32.0 / 16.0 * ModelFalcon9Stage1.modelScale;
        double dy = d * Math.cos(Math.toRadians(this.rotationPitch));
        double dh = d * Math.sin(Math.toRadians(this.rotationPitch));
        double dx = dh * -Math.sin(Math.toRadians(this.rotationYaw));
        double dz = dh * Math.cos(Math.toRadians(this.rotationYaw));
        passenger.setLocationAndAngles(this.posX + dx, this.posY + dy, this.posZ + dz, this.rotationYaw, this.rotationPitch);
    }
}