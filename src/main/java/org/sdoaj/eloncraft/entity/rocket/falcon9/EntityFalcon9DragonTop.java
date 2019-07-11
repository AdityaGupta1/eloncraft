package org.sdoaj.eloncraft.entity.rocket.falcon9;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import org.sdoaj.eloncraft.Eloncraft;
import org.sdoaj.eloncraft.entity.*;
import org.sdoaj.eloncraft.entity.rocket.EntityRocketPart;
import org.sdoaj.eloncraft.util.PacketHandler;

@Mod.EventBusSubscriber(modid = Eloncraft.MODID)
public class EntityFalcon9DragonTop extends EntityRocketPart implements ReceivesSetValueMessages, IEntityAdditionalSpawnData {
    private final TimedTaskExecutor executor = new TimedTaskExecutor();
    private TimedTask hatchTask;

    private double hatchPosition = 0.0;

    EntityFalcon9DragonTop(World world) {
        super(world);
        this.setSize(0.5F * ModelFalcon9Stage1.modelScale, 9.0F / 16.0F * ModelFalcon9Stage1.modelScale);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        executor.update();

        if (world.isRemote) {
            PacketHandler.sendToServer(new MessageSetValueToServer(this, "hatch", hatchPosition));
            if (hatchTask != null) {
                PacketHandler.sendToServer(new MessageSetValueToServer(this, "hatch target", hatchTask.getTarget()));
            }
        }

        if (hatchTask != null && hatchTask.isDone()) {
            hatchTask = null;
        }

        if (!getPassengers().isEmpty()) {
            EntityPlayer rider = (EntityPlayer) getPassengers().get(0);
            if (rider.isSneaking() && hatchPosition == 1.0) {
                rider.dismountRidingEntity();
            }
        }
    }

    @Override
    public void updatePassenger(Entity passenger) {
        double d = 0.8 / 16.0 * ModelFalcon9Stage1.modelScale;
        double dy = d * Math.cos(Math.toRadians(this.rotationPitch));
        double dh = d * Math.sin(Math.toRadians(this.rotationPitch));
        double dx = dh * -Math.sin(Math.toRadians(this.rotationYaw));
        double dz = dh * Math.cos(Math.toRadians(this.rotationYaw));
        passenger.setPosition(this.posX + dx, this.posY + dy, this.posZ + dz);
    }

    @Override
    public double getMountedYOffset() {
        return super.getMountedYOffset();
    }

    @Override
    protected void removePassenger(Entity passenger) {
        super.removePassenger(passenger);
        Vec3d dz = new Vec3d(0, 0, -4).rotateYaw((float) Math.toRadians(-this.rotationYaw));
        Vec3d dismountPos = new Vec3d(this.posX, this.posY + 7, this.posZ).add(dz);
        passenger.setPosition(dismountPos.x, dismountPos.y, dismountPos.z);
    }

    @SubscribeEvent
    public static void preventDismountWhenHatchClosed(EntityMountEvent event) {
        if (!event.isDismounting()) {
            return;
        }

        if (!(event.getEntityBeingMounted() instanceof EntityFalcon9DragonTop)) {
            return;
        }

        if (((EntityFalcon9DragonTop) event.getEntityBeingMounted()).hatchPosition != 1.0) {
            event.setCanceled(true);
        }
    }

    private void loadTaskFromData(double pos, double target) {
        hatchPosition = Double.isNaN(pos) ? 0.0 : pos;
        if (!Double.isNaN(target)) {
            hatchTask = new TimedTask(1.0 - target, hatchPosition, target, 2.0, x -> hatchPosition = x);
            executor.submit(hatchTask);
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);

        compound.setDouble("HatchPos", hatchPosition);
        compound.setDouble("HatchTarget", hatchTask == null ? Double.NaN : hatchTask.getTarget());
    }

    private double queuedHatchPosition = Double.NaN;
    private double queuedHatchTarget = Double.NaN;

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);

        queuedHatchPosition = compound.getDouble("HatchPos");
        double target = compound.getDouble("HatchTarget");
        if (!Double.isNaN(target)) {
            queuedHatchTarget = target;
        }
    }

    @Override
    public boolean shouldRiderSit() {
        return true;
    }

    private void moveHatchToggle() {
        hatchTask = new TimedTask(hatchPosition, 1.0 - hatchPosition, 2.0, x -> hatchPosition = x);
        executor.submit(hatchTask);
    }

    @Override
    protected boolean processInteract(EntityPlayer player, EnumHand hand) {
        if (hatchTask == null) {
            if (hatchPosition == 0.0) {
                moveHatchToggle();
            } else if (hatchPosition == 1.0) {
                if (player.isSneaking()) {
                    moveHatchToggle();
                } else {
                    if (getPassengers().isEmpty()) {
                        player.startRiding(this);
                    } else {
                        moveHatchToggle();
                    }
                }
            } else {
                return false;
            }
        }

        return true;
    }

    double getHatchPosition() {
        return hatchPosition;
    }

    @Override
    public void receive(String name, double value) {
        if (name.equals("hatch")) {
            hatchPosition = value;
        } else if (name.equals("hatch target") && hatchTask == null) {
            loadTaskFromData(hatchPosition, value);
        }
    }

    @Override
    public void writeSpawnData(ByteBuf buf) {
        buf.writeDouble(queuedHatchPosition);
        buf.writeDouble(queuedHatchTarget);
    }

    @Override
    public void readSpawnData(ByteBuf buf) {
        double pos = buf.readDouble();
        double target = buf.readDouble();
        loadTaskFromData(pos, target);
    }
}