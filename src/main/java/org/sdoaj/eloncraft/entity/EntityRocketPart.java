package org.sdoaj.eloncraft.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public abstract class EntityRocketPart extends EntityLiving {
    public EntityRocketPart(World world) {
        super(world);
        this.setNoGravity(true);
        this.ignoreFrustumCheck = true;
    }

    @Override
    public boolean canBeLeashedTo(EntityPlayer player) {
        return false;
    }

    private List<EntityRocketPart> getRocket() {
        List<EntityRocketPart> parts = new ArrayList<>();

        Entity entity = getLowestRidingEntity();

        // assumes each entity has only one direct passenger
        while(true) {
            if (entity instanceof EntityRocketPart) {
                parts.add((EntityRocketPart) entity);
            }

            if (entity.getPassengers().isEmpty()) {
                break;
            } else {
                entity = entity.getPassengers().get(0);
            }
        }

        return parts;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        this.renderYawOffset = 0;
        this.rotationYawHead = 0;
        this.cameraPitch = 0;
    }

    private void damageEntity(DamageSource source, float amount, boolean initialDamage) {
        if (initialDamage) {
            getRocket().forEach(part -> part.damageEntity(source, amount, false));
        } else {
            super.damageEntity(source, amount);
        }
    }

    @Override
    protected void damageEntity(DamageSource source, float amount) {
        damageEntity(source, amount, true);
    }

    private void setDead(boolean initialDead) {
        if (initialDead) {
            getRocket().forEach(part -> part.setDead(false));
        } else {
            super.setDead();
        }
    }

    @Override
    public void setDead() {
        setDead(true);
    }

    @Override
    public void onDeath(DamageSource source) {
        setDead();
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }
}
