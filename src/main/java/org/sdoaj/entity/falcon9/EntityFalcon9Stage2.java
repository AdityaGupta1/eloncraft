package org.sdoaj.entity.falcon9;

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
import org.sdoaj.fluids.ModFluids;
import org.sdoaj.items.ModItems;

import java.util.Arrays;

public class EntityFalcon9Stage2 extends EntityLiving {
    EntityFalcon9Stage2(World world) {
        super(world);
        this.setSize(0.5F * ModelFalcon9Stage1.modelScale, 32.0F / 16.0F * ModelFalcon9Stage1.modelScale);
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