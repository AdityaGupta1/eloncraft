package org.sdoaj.eloncraft.entity.rocket.falcon9;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import org.sdoaj.eloncraft.blocks.launch.BlockLaunchpad;
import org.sdoaj.eloncraft.blocks.tileentities.ModFluidTank;
import org.sdoaj.eloncraft.entity.rocket.EntityRocketPart;
import org.sdoaj.eloncraft.fluids.ModFluids;
import org.sdoaj.eloncraft.items.ModItems;

import java.util.Arrays;
import java.util.List;

enum LaunchState {
    LAUNCHPAD, AWAITING_PLAYER, AWAITING_HATCH, COUNTDOWN, LIFTOFF
}

public class EntityFalcon9Stage1 extends EntityRocketPart {
    private LaunchState currentState;
    private LaunchState desiredState;
    private EntityPlayer player;

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
            this.setAcceleration(0, 7.5, 0);
            this.removeLaunchpad();
            sendMessageToPlayer(TextFormatting.GREEN + "And we have liftoff.");
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
            if (getPartOfType(EntityFalcon9DragonTop.class).getHatchPosition() == 0.0) {
                setState(LaunchState.COUNTDOWN);
            }
        }

        if (currentState == LaunchState.COUNTDOWN) {
            countdown--;

            if (countdown % 20 == 0) {
                int seconds = countdown / 20;
                if (seconds <= 5 && seconds > 0) {
                    player.sendMessage(new TextComponentString(TextFormatting.GOLD + "" + seconds + "."));
                }
            }

            if (countdown == 0) {
                setState(LaunchState.LIFTOFF);
            }
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