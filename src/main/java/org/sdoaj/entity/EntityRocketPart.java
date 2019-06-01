package org.sdoaj.entity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public abstract class EntityRocketPart extends EntityLiving {
    public EntityRocketPart(World world) {
        super(world);
        this.setNoGravity(true);
    }

    @Override
    public boolean canBeLeashedTo(EntityPlayer player) {
        return false;
    }

    @Override
    public void onDeath(DamageSource source) {
        setDead(); // skip death animation - immediately disappear and drop item
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }
}
