package org.sdoaj.entity.falcon9;

import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;

public class EntityFalcon9Base extends EntityLiving {
    public EntityFalcon9Base(World world) {
        super(world);
        this.setSize(0.5F * ModelFalcon9Base.modelScale, 127.0F / 16.0F * ModelFalcon9Base.modelScale);
    }
}