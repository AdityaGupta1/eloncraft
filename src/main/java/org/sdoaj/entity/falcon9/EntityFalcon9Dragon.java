package org.sdoaj.entity.falcon9;

import net.minecraft.entity.EntityLiving;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityFalcon9Dragon extends EntityLiving {
    EntityFalcon9Dragon(World world) {
        super(world);
        this.setSize(0.5F * ModelFalcon9Stage1.modelScale, 18.0F / 16.0F * ModelFalcon9Stage1.modelScale);
        this.setNoGravity(true);
    }

    @Override
    public void onDeath(DamageSource source) {
        setDead(); // skip death animation - immediately disappear and drop item
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return new SoundEvent(new ResourceLocation("block.anvil.land"));
    }

    @Override
    protected SoundEvent getDeathSound() {
        return getHurtSound(null);
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }
}