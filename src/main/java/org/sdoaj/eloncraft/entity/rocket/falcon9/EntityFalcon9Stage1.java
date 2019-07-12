package org.sdoaj.eloncraft.entity.rocket.falcon9;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleCloud;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import org.sdoaj.eloncraft.Eloncraft;
import org.sdoaj.eloncraft.blocks.launch.BlockLaunchpad;
import org.sdoaj.eloncraft.blocks.tileentities.ModFluidTank;
import org.sdoaj.eloncraft.entity.MovingSoundAny;
import org.sdoaj.eloncraft.entity.rocket.EntityRocketPart;
import org.sdoaj.eloncraft.fluids.ModFluids;
import org.sdoaj.eloncraft.items.ModItems;

import java.util.Arrays;
import java.util.List;

public class EntityFalcon9Stage1 extends EntityRocketPart {
    private LaunchState currentState;
    private LaunchState desiredState;
    private EntityPlayer player;

    private enum LaunchState {
        LAUNCHPAD, AWAITING_PLAYER, AWAITING_HATCH, COUNTDOWN, LIFTOFF
    }

    private void setState(LaunchState state) {
        this.desiredState = state;
    }

    LaunchState getState() {
        return currentState;
    }

    public EntityFalcon9Stage1(World world) {
        super(world);
        this.setSize(0.5F * ModelFalcon9Stage1.modelScale, 98.0F / 16.0F * ModelFalcon9Stage1.modelScale);
    }

    private static final DataParameter<Integer> particleState = EntityDataManager.createKey(EntityFalcon9DragonTop.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> playSound = EntityDataManager.createKey(EntityFalcon9DragonTop.class, DataSerializers.VARINT);

    @Override
    protected void entityInit() {
        super.entityInit();

        this.dataManager.register(particleState, 0);
        this.dataManager.register(playSound, 0);
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
        setState(LaunchState.LAUNCHPAD);
    }

    public BlockPos getLaunchpad() {
        return launchpad;
    }

    private void useLaunchpadTopPos() {
        if (launchpadTopPos != null) {
            setLocationAndAngles(launchpadTopPos.x, launchpadTopPos.y, launchpadTopPos.z, launchpadRotation, 0f);
        }
    }

    private void removeLaunchpad() {
        BlockLaunchpad.removeRocket(launchpad);
        launchpad = null;
        launchpadTopPos = null;
        launchpadRotation = Float.NaN;
    }

    private void sendMessageToPlayer(String message) {
        if (player != null) {
            player.sendMessage(new TextComponentString(message));
        }
    }

    private static final int countdownSeconds = 10;
    private int countdown;

    private static final double yAcceleration = 5.0;

    private SoundEvent liftoffSound = new SoundEvent(new ResourceLocation(Eloncraft.MODID, "liftoff"));

    private void handleStateChange(LaunchState currentState, LaunchState desiredState) {
        if (desiredState == currentState) {
            return;
        }

        if (desiredState == LaunchState.AWAITING_PLAYER) {
            sendMessageToPlayer(TextFormatting.GOLD + "Enter the Dragon capsule and close the hatch to start the countdown.");
        }

        if (desiredState == LaunchState.AWAITING_HATCH) {
            sendMessageToPlayer(TextFormatting.GOLD + "Close the hatch to start the countdown. Once you close the hatch, there's no turning back.");
        }

        if (desiredState == LaunchState.COUNTDOWN) {
            this.countdown = countdownSeconds * 20;
            getPartOfType(EntityFalcon9DragonTop.class).lockHatch();
            sendMessageToPlayer(TextFormatting.GOLD + "T minus " + countdownSeconds + " seconds until launch.");
        }

        if (desiredState == LaunchState.LIFTOFF) {
            this.setAcceleration(0, yAcceleration, 0);
            this.removeLaunchpad();
            sendMessageToPlayer(TextFormatting.GREEN + "Liftoff.");
        }
    }

    private void handleCurrentState(LaunchState currentState) {
        if (currentState != LaunchState.LIFTOFF) {
            useLaunchpadTopPos();
        }

        if (currentState == LaunchState.AWAITING_PLAYER) {
            List<Entity> passengers = getPartOfType(EntityFalcon9DragonTop.class).getPassengers();

            if (!passengers.isEmpty()) {
                this.player = (EntityPlayer) passengers.get(0);
                setState(LaunchState.AWAITING_HATCH);
            }
        }

        if (currentState == LaunchState.AWAITING_HATCH) {
            EntityFalcon9DragonTop dragon = getPartOfType(EntityFalcon9DragonTop.class);

            if (dragon.getHatchPosition() == 0.0) {
                setState(LaunchState.COUNTDOWN);
            }

            if (dragon.getPassengers().isEmpty()) {
                setState(LaunchState.AWAITING_PLAYER);
            }
        }

        if (currentState == LaunchState.COUNTDOWN) {
            countdown--;

            if (countdown % 20 == 0) {
                int seconds = countdown / 20;
                if (seconds <= 5 && seconds > 0) {
                    sendMessageToPlayer(TextFormatting.GOLD + "" + seconds + ".");
                }

                if (seconds == 3) {
                    dataManager.set(playSound, player != null ? 2 : 1);

                    this.dataManager.set(particleState, 1);
                }
            }

            if (countdown == 0) {
                setState(LaunchState.LIFTOFF);
            }
        }

        if (currentState == LaunchState.LIFTOFF) {
            this.dataManager.set(particleState, 2);
        }
    }

    // how to cheese the particle system, epic style

    private static class ParticleSmoke extends ParticleCloud {
        private ParticleSmoke(World world, double x, double y, double z, double motionX, double motionY, double motionZ, double ageMultiplier) {
            super(world, x, y, z, motionX, motionY, motionZ);
            this.particleMaxAge *= ageMultiplier;
        }
    }

    private static final double smokeAgeScale = 4.0;

    private void generateSmoke() {
        for (int i = 0; i < 50; i++) {
            double theta = Math.random() * 2 * Math.PI;
            double r = Math.random() * 1.0;

            double motionX = r * - Math.sin(theta);
            double motionY = rand.nextGaussian() * 0.2 / smokeAgeScale;
            double motionZ = r * Math.cos(theta);

            Minecraft.getMinecraft().effectRenderer.addEffect(
                    new ParticleSmoke(world, this.posX, this.posY, this.posZ,
                            motionX, motionY, motionZ, smokeAgeScale));
        }
    }

    private static class ParticleFlame extends net.minecraft.client.particle.ParticleFlame {
        private ParticleFlame(World world, double x, double y, double z, double motionX, double motionY, double motionZ) {
            super(world, x, y, z, motionX, motionY, motionZ);
        }
    }

    private void generateFire() {
        for (int i = 0; i < 100; i++) {
            double theta = Math.random() * 2 * Math.PI;
            double r = Math.random() * 1.5;

            double dx = r * -Math.sin(theta);
            double dz = r * Math.cos(theta);

            double motionY = -Math.max((2.0 - r) + (rand.nextGaussian() * 0.4), 0);
            motionY *= 0.5;

            // using this instead of "world.spawnParticle()" makes sure the particles render even past the normal range of 32 blocks
            Minecraft.getMinecraft().effectRenderer.addEffect(
                    new ParticleFlame(world, this.posX + dx, this.posY, this.posZ + dz,
                            0, motionY, 0));
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (currentState != desiredState) {
            handleStateChange(currentState, desiredState);
            currentState = desiredState;
        }

        handleCurrentState(currentState);

        if (world.isRemote) {
            switch (dataManager.get(particleState)) {
                case 1:
                    generateSmoke();
                case 2:
                    generateFire();
                    break;
            }

            if (dataManager.get(playSound) != 0) {
                Minecraft.getMinecraft().getSoundHandler().playSound(new MovingSoundAny(
                        this, liftoffSound, SoundCategory.PLAYERS, 0.5));

                if (dataManager.get(playSound) == 2) {
                    Minecraft.getMinecraft().getSoundHandler().playSound(new MovingSoundAny(
                            player, liftoffSound, SoundCategory.PLAYERS, 1.0, entity -> !entity.isRiding()));
                }

                dataManager.set(playSound, 0);
            }
        }

        if (!hasCreatedOtherParts && !world.isRemote) {
            EntityFalcon9Stage2 stage2 = new EntityFalcon9Stage2(this.world);
            updatePassenger(stage2);
            world.spawnEntity(stage2);
            stage2.startRiding(this, true);

            EntityFalcon9DragonTrunk dragonTrunk = new EntityFalcon9DragonTrunk(this.world);
            stage2.updatePassenger(dragonTrunk);
            world.spawnEntity(dragonTrunk);
            dragonTrunk.startRiding(stage2, true);

            EntityFalcon9DragonTop dragonTop = new EntityFalcon9DragonTop(this.world);
            stage2.updatePassenger(dragonTop);
            world.spawnEntity(dragonTop);
            dragonTop.startRiding(dragonTrunk, true);

            hasCreatedOtherParts = true;
        }
    }

    public void launch(EntityPlayer player) {
        this.player = player;
        setState(LaunchState.AWAITING_PLAYER);
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

        compound.setString("CurrentState", currentState == null ? "null" : currentState.name());

        compound.setInteger("ParticleState", dataManager.get(particleState));

        compound.setInteger("PlaySound", dataManager.get(playSound));

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

        if (compound.hasKey("CurrentState")) {
            String state = compound.getString("CurrentState");
            if (state.equals("null")) {
                setState(LaunchState.LAUNCHPAD);
            } else {
                setState(LaunchState.valueOf(compound.getString("CurrentState")));
            }
        } else {
            setState(LaunchState.LAUNCHPAD);
        }

        dataManager.set(particleState, compound.getInteger("ParticleState"));

        dataManager.set(playSound, compound.getInteger("PlaySound"));

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
}