package org.sdoaj.eloncraft.entity.rocket.falcon9;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import org.sdoaj.eloncraft.Eloncraft;
import org.sdoaj.eloncraft.entity.MessageSetValueToServer;
import org.sdoaj.eloncraft.entity.ReceivesSetValueMessages;
import org.sdoaj.eloncraft.entity.TimedTask;
import org.sdoaj.eloncraft.entity.TimedTaskExecutor;
import org.sdoaj.eloncraft.entity.rocket.EntityRocketPart;
import org.sdoaj.eloncraft.util.PacketHandler;
import org.sdoaj.eloncraft.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Eloncraft.MODID)
public class EntityFalcon9DragonTop extends EntityRocketPart implements ReceivesSetValueMessages, IEntityAdditionalSpawnData {
    private final TimedTaskExecutor executor = new TimedTaskExecutor();
    private TimedTask hatchTask;

    private double hatchPosition = 0.0;

    EntityFalcon9DragonTop(World world) {
        super(world);
        this.setSize(0.5F * ModelFalcon9Stage1.modelScale, 9.0F / 16.0F * ModelFalcon9Stage1.modelScale);
    }

    private static final DataParameter<Boolean> isHatchLocked = EntityDataManager.createKey(EntityFalcon9DragonTop.class, DataSerializers.BOOLEAN);

    @Override
    protected void entityInit() {
        super.entityInit();

        dataManager.register(isHatchLocked, false);
    }

    private Vec3d previousPosition = Vec3d.ZERO;

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

            if (previousPosition.equals(Vec3d.ZERO)) {
                previousPosition = new Vec3d(this.posX, this.posY, this.posZ);
            }

            Vec3d currentPosition = new Vec3d(this.posX, this.posY, this.posZ);
            final double velocity = currentPosition.subtract(previousPosition).lengthVector() * 20;
            previousPosition = currentPosition;

            final double shake = MathHelper.clamp(velocity / 100.0, 0, 0.10);

            rider.setLocationAndAngles(rider.posX + RandomUtil.nextDouble(shake), rider.posY + RandomUtil.nextDouble(shake), rider.posZ + RandomUtil.nextDouble(shake),
                    rider.rotationYaw + (float) RandomUtil.nextDouble(shake * 20), rider.rotationPitch + (float) RandomUtil.nextDouble(shake * 20));
        }

        // TODO release only
        if (this.posY > 1000) {
            if (!getPassengers().isEmpty()) {
                EntityPlayer player = (EntityPlayer) getPassengers().get(0);
                player.sendMessage(new TextComponentString(TextFormatting.GREEN + "Thanks for playing Eloncraft! To see development updates and to report bugs, go to https://github.com/AdityaGupta1/eloncraft."));
                player.sendMessage(new TextComponentString(TextFormatting.LIGHT_PURPLE + "(Don't worry, you won't take any fall damage.)"));
                fallImmune.add(player);
            }

            this.setDead(true);
        }
    }

    // TODO release only
    private static final List<EntityPlayer> fallImmune = new ArrayList<>();

    @SubscribeEvent
    public static void preventFallDamage(LivingDamageEvent event) {
        if (!(event.getEntity() instanceof EntityPlayer)) {
            return;
        }

        if (event.getSource() != DamageSource.FALL) {
            return;
        }

        EntityPlayer player = (EntityPlayer) event.getEntity();

        if (!fallImmune.contains(player)) {
            return;
        }

        fallImmune.remove(player);
        event.setCanceled(true);
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
    protected void removePassenger(Entity passenger) {
        super.removePassenger(passenger);
        Vec3d dz = new Vec3d(0, 0, -4).rotateYaw((float) Math.toRadians(-this.rotationYaw));
        Vec3d dismountPos = new Vec3d(this.posX, this.posY + 3, this.posZ).add(dz);
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

        if (event.getEntityMounting().isDead || event.getEntityBeingMounted().isDead) {
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

        compound.setBoolean("HatchLocked", dataManager.get(isHatchLocked));
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

        dataManager.set(isHatchLocked, compound.getBoolean("HatchLocked"));
    }

    @Override
    public boolean shouldRiderSit() {
        return true;
    }

    private void moveHatchToggle() {
        hatchTask = new TimedTask(hatchPosition, 1.0 - hatchPosition, 2.0, x -> hatchPosition = x);
        executor.submit(hatchTask);
    }

    void lockHatch() {
        dataManager.set(isHatchLocked, true);
    }

    @Override
    protected boolean processInteract(EntityPlayer player, EnumHand hand) {
        if (dataManager.get(isHatchLocked)) {
            return false;
        }

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