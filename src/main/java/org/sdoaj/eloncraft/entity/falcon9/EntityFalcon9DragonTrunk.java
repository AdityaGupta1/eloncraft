package org.sdoaj.eloncraft.entity.falcon9;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import org.sdoaj.eloncraft.Eloncraft;
import org.sdoaj.eloncraft.entity.EntityRocketPart;

@Mod.EventBusSubscriber(modid = Eloncraft.MODID)
public class EntityFalcon9DragonTrunk extends EntityRocketPart {
    EntityFalcon9DragonTrunk(World world) {
        super(world);
        this.setSize(0.5F * ModelFalcon9Stage1.modelScale, 9.0F / 16.0F * ModelFalcon9Stage1.modelScale);
    }

    @Override
    public void updatePassenger(Entity passenger) {
        double d = 9.0 / 16.0 * ModelFalcon9Stage1.modelScale;
        double dy = d * Math.cos(Math.toRadians(this.rotationPitch));
        double dh = d * Math.sin(Math.toRadians(this.rotationPitch));
        double dx = dh * -Math.sin(Math.toRadians(this.rotationYaw));
        double dz = dh * Math.cos(Math.toRadians(this.rotationYaw));
        passenger.setLocationAndAngles(this.posX + dx, this.posY + dy, this.posZ + dz, this.rotationYaw, this.rotationPitch);
    }
}