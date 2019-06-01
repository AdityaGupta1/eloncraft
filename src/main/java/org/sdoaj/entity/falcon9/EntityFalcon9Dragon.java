package org.sdoaj.entity.falcon9;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import org.sdoaj.entity.*;
import org.sdoaj.util.PacketHandler;

public class EntityFalcon9Dragon extends EntityRocketPart implements ReceivesSetValueMessages, IEntityAdditionalSpawnData {
    private final TimedTaskExecutor executor = new TimedTaskExecutor();
    private TimedTask hatchTask;

    private double hatchPosition = 0.0;

    EntityFalcon9Dragon(World world) {
        super(world);
        this.setSize(0.5F * ModelFalcon9Stage1.modelScale, 18.0F / 16.0F * ModelFalcon9Stage1.modelScale);
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
    }

    private void loadTaskFromData(double pos, double target) {
        hatchPosition = pos;
        hatchTask = new TimedTask(1.0 - target, hatchPosition, target, 2.0, x -> hatchPosition = x);
        executor.submit(hatchTask);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);

        compound.setDouble("HatchPos", hatchPosition);
        if (hatchTask != null) {
            compound.setDouble("HatchTarget", hatchTask.getTarget());
        }
    }

    private double queuedHatchPosition;
    private double queuedHatchTarget;

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);

        queuedHatchPosition = compound.getDouble("HatchPos");
        if (compound.hasKey("HatchTarget")) {
            queuedHatchTarget = compound.getDouble("HatchTarget");
        }
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
    public boolean shouldRiderSit() {
        return true;
    }

    @Override
    protected boolean processInteract(EntityPlayer player, EnumHand hand) {
        if (player.isSneaking()) {
            return false;
        } else {
            boolean submit = true;

            if (hatchTask == null) {
                if (hatchPosition == 0.0 || hatchPosition == 1.0) {
                    hatchTask = new TimedTask(hatchPosition, 1.0 - hatchPosition, 2.0, x -> hatchPosition = x);
                } else {
                    submit = false;
                }
            } else {
                submit = false;
            }

            if (submit) {
                executor.submit(hatchTask);
            }

            return submit;
        }
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