package org.sdoaj.entity.falcon9;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import org.sdoaj.entity.EntityRocketPart;
import org.sdoaj.entity.TimedTaskExecutor;

public class EntityFalcon9Dragon extends EntityRocketPart {
    private final TimedTaskExecutor executor = new TimedTaskExecutor();

    private double hatchPosition = 0.0;

    EntityFalcon9Dragon(World world) {
        super(world);
        this.setSize(0.5F * ModelFalcon9Stage1.modelScale, 18.0F / 16.0F * ModelFalcon9Stage1.modelScale);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        executor.update();
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

    @Override
    public boolean shouldRiderSit() {
        return true;
    }

    @Override
    protected boolean processInteract(EntityPlayer player, EnumHand hand) {
        if (player.isSneaking()) {
            return false;
        } else {
            if (hatchPosition == 0.0) {
                executor.submit(0.0, 1.0, 2.0, x -> hatchPosition = x);
            } else if (hatchPosition == 1.0) {
                executor.submit(1.0, 0.0, 2.0, x -> hatchPosition = x);
            } else {
                return false;
            }

            return true;
        }
    }

    double getHatchPosition() {
        return hatchPosition;
    }
}