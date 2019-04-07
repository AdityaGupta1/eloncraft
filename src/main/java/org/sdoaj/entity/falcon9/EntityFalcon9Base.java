package org.sdoaj.entity.falcon9;

import net.minecraft.entity.EntityLiving;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityFalcon9Base extends EntityLiving {
    private IRocketController controller;

    public EntityFalcon9Base(World world, IRocketController controller) {
        super(world);
        this.setSize(0.5F * ModelFalcon9Base.modelScale, 127.0F / 16.0F * ModelFalcon9Base.modelScale);
        this.controller = controller;
        updatePosition();
    }

    public EntityFalcon9Base(World world) {
        this(world, null);
    }

    private void updatePosition() {
        if (controller == null) {
            this.setDead();
            return;
        }

        Vec3d pos = controller.getPos();

        if (pos == null) {
            return;
        }

        this.setPosition(pos.x, pos.y, pos.z);
    }

    @Override
    public void onUpdate() {
        updatePosition();
    }

    public void setController(IRocketController controller) {
        this.controller = controller;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source == DamageSource.OUT_OF_WORLD) {
            return super.attackEntityFrom(source, amount);
        }

        return false;
    }
}