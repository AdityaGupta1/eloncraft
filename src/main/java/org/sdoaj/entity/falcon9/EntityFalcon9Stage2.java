package org.sdoaj.entity.falcon9;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import org.sdoaj.blocks.launch.BlockLaunchpad;
import org.sdoaj.blocks.machines.ModFluidTank;
import org.sdoaj.entity.EntityRocketPart;
import org.sdoaj.fluids.ModFluids;
import org.sdoaj.items.ModItems;

import java.util.Arrays;

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

    @Override
    public void setDead() {
        getPassengers().forEach(Entity::setDead);

        super.setDead();
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