package org.sdoaj.entity.falcon9;

import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.sdoaj.item.blocks.launch.BlockLaunchpad;

import java.util.Arrays;

public class EntityFalcon9Base extends EntityLiving {
    public EntityFalcon9Base(World world) {
        super(world);
        this.setSize(0.5F * ModelFalcon9Base.modelScale, 127.0F / 16.0F * ModelFalcon9Base.modelScale);
    }

    private BlockPos launchpad = null;

    public void setLaunchpad(BlockPos pos) {
        this.launchpad = pos;
    }

    public void removeLaunchpad() {
        launchpad = null;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);

        compound.setString("Launchpad", launchpad == null ? "null" : launchpad.getX() + "," + launchpad.getY() + "," + launchpad.getZ());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);

        if (compound.hasKey("Launchpad")) {
            String pos = compound.getString("Launchpad");

            if (pos.equals("null")) {
                launchpad = null;
            } else {
                int[] posArray = Arrays.stream(pos.split(",")).mapToInt(Integer::parseInt).toArray();
                BlockLaunchpad.addRocket(this, new BlockPos(posArray[0], posArray[1], posArray[2]));
            }
        }
    }
}