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

public class EntityFalcon9Stage1 extends EntityLiving {
    private final RocketController controller;

    public EntityFalcon9Stage1(World world) {
        super(world);

        EntityFalcon9Stage2 stage2 = new EntityFalcon9Stage2(world);
        this.controller = new RocketController(this, stage2);
        stage2.setController(this.controller);
        world.spawnEntity(stage2);

        this.setSize(0.5F * ModelFalcon9Stage1.modelScale, 98.0F / 16.0F * ModelFalcon9Stage1.modelScale);
        this.setNoGravity(true);
    }

    private final int capacity = 16000;
    public final ModFluidTank fuelTank = new ModFluidTank("FuelTank", capacity) {
        @Override
        public boolean canFillFluidType(FluidStack fluid) {
            return fluid.getFluid() == ModFluids.RP1;
        }

        @Override
        public boolean canDrain() {
            return false;
        }
    };
    public final ModFluidTank oxygenTank = new ModFluidTank("OxygenTank", capacity) {
        @Override
        public boolean canFillFluidType(FluidStack fluid) {
            return fluid.getFluid() == ModFluids.LOX;
        }

        @Override
        public boolean canDrain() {
            return false;
        }
    };

    private BlockPos launchpad = null;

    public void setLaunchpad(BlockPos pos) {
        launchpad = pos;
        controller.setPosition(new Vec3d(launchpad).addVector(0.5, 0.25, 0.5), 0f, 0f);
        controller.setPosition(new Vec3d(launchpad).addVector(0.5, 5 - 2.125, 0.5), 0f, 0f);
    }

    public void removeLaunchpad() {
        launchpad = null;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        Position pos = controller.getStage1Pos();
        if (pos != null) {
            setLocationAndAngles(pos.x, pos.y, pos.z, pos.yaw, pos.pitch);
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);

        compound.setString("Launchpad", launchpad == null ? "null" : launchpad.getX() + "," + launchpad.getY() + "," + launchpad.getZ());

        fuelTank.writeToNBT(compound);
        oxygenTank.writeToNBT(compound);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);

        if (compound.hasKey("Launchpad")) {
            String pos = compound.getString("Launchpad");

            if (pos.equals("null")) {
                removeLaunchpad();
            } else {
                int[] posArray = Arrays.stream(pos.split(",")).mapToInt(Integer::parseInt).toArray();
                setLaunchpad(new BlockPos(posArray[0], posArray[1], posArray[2]));
                BlockLaunchpad.addRocket(this, launchpad);
            }
        }

        fuelTank.readFromNBT(compound);
        oxygenTank.readFromNBT(compound);
    }

    @Override
    public void onDeath(DamageSource source) {
        setDead(); // skip death animation - immediately disappear and drop item
    }

    @Override
    public void setDead() {
        if (!world.isRemote) {
            this.dropItem(ModItems.FALCON9_BASE, 1);
        }

        BlockLaunchpad.removeRocket(launchpad);

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