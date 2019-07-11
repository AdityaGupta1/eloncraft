package org.sdoaj.eloncraft.entity.rocket;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class EntityRocketPart extends EntityLiving {
    private Vec3d acceleration = new Vec3d(0, 0, 0); // m/s^2
    private Vec3d velocity = new Vec3d(0, 0, 0); // m/s

    public EntityRocketPart(World world) {
        super(world);
        this.setNoGravity(true);
        this.ignoreFrustumCheck = true;
    }

    protected void setAcceleration(Vec3d acceleration) {
        this.acceleration = acceleration;
    }

    protected void setAcceleration(double x, double y, double z) {
        setAcceleration(new Vec3d(x, y, z));
    }

    @Override
    public boolean canBeLeashedTo(EntityPlayer player) {
        return false;
    }

    protected List<EntityRocketPart> getRocket() {
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

    protected <T extends EntityRocketPart> T getPartOfType(Class<T> type) {
        for (EntityRocketPart part : getRocket()) {
            if (part.getClass().equals(type)) {
                return (T) part;
            }
        }

        return null;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        this.renderYawOffset = 0;
        this.rotationYawHead = 0;
        this.cameraPitch = 0;

        this.velocity = this.velocity.add(this.acceleration.scale(0.05));
        Vec3d dx = this.velocity.scale(0.05);
        this.setPosition(this.posX + dx.x, this.posY + dx.y, this.posZ + dx.z);
        getRocket().forEach(part -> {
            List<Entity> passengers = part.getPassengers();
            if (!passengers.isEmpty()) {
                part.updatePassenger(passengers.get(0));
            }
        });
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
            setDead();
        }
    }

    @Override
    public void onDeath(DamageSource source) {
        setDead(true);
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return new SoundEvent(new ResourceLocation("block.anvil.land"));
    }

    @Override
    protected SoundEvent getDeathSound() {
        return getHurtSound(null);
    }
}
