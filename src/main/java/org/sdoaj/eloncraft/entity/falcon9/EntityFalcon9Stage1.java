package org.sdoaj.eloncraft.entity.falcon9;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import org.sdoaj.eloncraft.blocks.launch.BlockLaunchpad;
import org.sdoaj.eloncraft.blocks.machines.ModFluidTank;
import org.sdoaj.eloncraft.entity.EntityRocketPart;
import org.sdoaj.eloncraft.fluids.ModFluids;
import org.sdoaj.eloncraft.items.ModItems;

import java.util.Arrays;

public class EntityFalcon9Stage1 extends EntityRocketPart {
    public EntityFalcon9Stage1(World world) {
        super(world);
        this.setSize(0.5F * ModelFalcon9Stage1.modelScale, 98.0F / 16.0F * ModelFalcon9Stage1.modelScale);
    }

    private boolean hasCreatedOtherParts = false;

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
    private Vec3d launchpadTopPos = null;
    private float launchpadRotation = Float.NaN;

    public void setLaunchpad(BlockPos pos, float rotation) {
        launchpad = pos;
        launchpadTopPos = new Vec3d(launchpad).addVector(0.5, 0.25, 0.5);
        launchpadRotation = rotation;
        useLaunchpadTopPos();
    }

    private void useLaunchpadTopPos() {
        if (launchpadTopPos != null) {
            setLocationAndAngles(launchpadTopPos.x, launchpadTopPos.y, launchpadTopPos.z, launchpadRotation, 0f);
        }
    }

    private void removeLaunchpad() {
        launchpad = null;
        launchpadTopPos = null;
        launchpadRotation = Float.NaN;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        useLaunchpadTopPos();

        if (!hasCreatedOtherParts && !world.isRemote) {
            EntityFalcon9Stage2 stage2 = new EntityFalcon9Stage2(this.world);
            updatePassenger(stage2);
            world.spawnEntity(stage2);
            stage2.startRiding(this, true);

            EntityFalcon9Dragon dragon = new EntityFalcon9Dragon(this.world);
            stage2.updatePassenger(dragon);
            world.spawnEntity(dragon);
            dragon.startRiding(stage2, true);

            hasCreatedOtherParts = true;
        }
    }

    @Override
    public void updatePassenger(Entity passenger) {
        double d = 58.9 / 2.0 + (28.0 / 16.0 * ModelFalcon9Stage1.modelScale) + 0.125;
        double dy = d * Math.cos(Math.toRadians(this.rotationPitch));
        double dh = d * Math.sin(Math.toRadians(this.rotationPitch));
        double dx = dh * -Math.sin(Math.toRadians(this.rotationYaw));
        double dz = dh * Math.cos(Math.toRadians(this.rotationYaw));
        passenger.setLocationAndAngles(this.posX + dx, this.posY + dy, this.posZ + dz, this.rotationYaw, this.rotationPitch);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);

        compound.setString("Launchpad", launchpad == null ? "null" : launchpad.getX() + "," + launchpad.getY() + "," + launchpad.getZ() + "," + launchpadRotation);

        compound.setBoolean("HasCreatedOtherParts", hasCreatedOtherParts);

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
                String[] split = pos.split(",");
                int[] posArray = Arrays.stream(split).limit(3).mapToInt(Integer::parseInt).toArray();
                setLaunchpad(new BlockPos(posArray[0], posArray[1], posArray[2]), Float.parseFloat(split[3]));
                BlockLaunchpad.addRocket(this, launchpad);
            }
        }

        if (compound.hasKey("HasCreatedOtherParts")) {
            hasCreatedOtherParts = compound.getBoolean("HasCreatedOtherParts");
        } else {
            hasCreatedOtherParts = false;
        }

        fuelTank.readFromNBT(compound);
        oxygenTank.readFromNBT(compound);
    }

    @Override
    public void setDead() {
        if (!world.isRemote) {
            this.dropItem(ModItems.FALCON9, 1);
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
}