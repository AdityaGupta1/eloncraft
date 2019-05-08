/*
 * This file ("TileEntityBase.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package org.sdoaj.blocks.tileentities;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import org.sdoaj.blocks.machines.alloyfurnace.TileEntityAlloyFurnace;
import org.sdoaj.blocks.machines.crusher.TileEntityCrusher;
import org.sdoaj.blocks.machines.loxcollector.TileEntityLOXCollector;
import org.sdoaj.blocks.machines.metalroller.TileEntityMetalRoller;
import org.sdoaj.blocks.machines.refinery.TileEntityRefinery;
import org.sdoaj.blocks.machines.workbench.TileEntityWorkbench;
import org.sdoaj.eloncraft.Main;

public abstract class TileEntityBase extends TileEntity implements ITickable {
    public final String name;
    public boolean isRedstonePowered;
    protected int ticksElapsed;
    protected TileEntity[] tilesAround = new TileEntity[6];
    protected boolean hasSavedDataOnChangeOrWorldStart;

    private static final int ticksPerUpdate = 2; // 5 = tile entity update interval

    public TileEntityBase(String name) {
        this.name = name;
    }

    public static void init() {
        register(TileEntityWorkbench.class);

        register(TileEntityMetalRoller.class);
        register(TileEntityCrusher.class);
        register(TileEntityAlloyFurnace.class);
        register(TileEntityRefinery.class);
        register(TileEntityLOXCollector.class);
    }

    private static void register(Class<? extends TileEntityBase> tileEntityClass) {
        try {
            // apparently sus but it works
            GameRegistry.registerTileEntity(tileEntityClass, new ResourceLocation(Main.MODID, tileEntityClass.newInstance().name));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public final NBTTagCompound writeToNBT(NBTTagCompound compound) {
        this.writeSyncableNBT(compound, NBTType.SAVE_TILE);
        return compound;
    }

    @Override
    public final void readFromNBT(NBTTagCompound compound) {
        this.readSyncableNBT(compound, NBTType.SAVE_TILE);
    }

    @Override
    public final SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound compound = new NBTTagCompound();
        this.writeSyncableNBT(compound, NBTType.SYNC);
        return new SPacketUpdateTileEntity(this.pos, -1, compound);
    }

    @Override
    public final void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        this.readSyncableNBT(pkt.getNbtCompound(), NBTType.SYNC);
    }

    @Override
    public final NBTTagCompound getUpdateTag() {
        NBTTagCompound compound = new NBTTagCompound();
        this.writeSyncableNBT(compound, NBTType.SYNC);
        return compound;
    }

    @Override
    public final void handleUpdateTag(NBTTagCompound compound) {
        this.readSyncableNBT(compound, NBTType.SYNC);
    }

    public final void sendUpdate() {
        if (this.world != null && !this.world.isRemote) {
            VanillaPacketDispatcher.dispatchTEToNearbyPlayers(this);
        }
    }

    public void writeSyncableNBT(NBTTagCompound compound, NBTType type) {
        if (type != NBTType.SAVE_BLOCK) {
            super.writeToNBT(compound);
        }

        if (type == NBTType.SAVE_TILE) {
            compound.setBoolean("Redstone", this.isRedstonePowered);
            compound.setInteger("TicksElapsed", this.ticksElapsed);
        }
    }

    public void readSyncableNBT(NBTTagCompound compound, NBTType type) {
        if (type != NBTType.SAVE_BLOCK) {
            super.readFromNBT(compound);
        }

        if (type == NBTType.SAVE_TILE) {
            this.isRedstonePowered = compound.getBoolean("Redstone");
            this.ticksElapsed = compound.getInteger("TicksElapsed");
        }
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return !oldState.getBlock().isAssociatedBlock(newState.getBlock());
    }

    public String getNameForTranslation() {
        return "container." + this.name + ".name";
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TextComponentTranslation(this.getNameForTranslation());
    }

    @Override
    public final void update() {
        this.updateEntity();
    }

    public int getComparatorStrength() {
        return 0;
    }

    public void updateEntity() {
        this.ticksElapsed++;

        if (!this.hasSavedDataOnChangeOrWorldStart) {
            if (this.shouldSaveDataOnChangeOrWorldStart()) {
                this.saveDataOnChangeOrWorldStart();
            }

            this.hasSavedDataOnChangeOrWorldStart = true;
        }
    }

    public void saveDataOnChangeOrWorldStart() {
        for (EnumFacing side : EnumFacing.values()) {
            BlockPos pos = this.pos.offset(side);
            if (this.world.isBlockLoaded(pos)) {
                this.tilesAround[side.ordinal()] = this.world.getTileEntity(pos);
            }
        }
    }

    public boolean shouldSaveDataOnChangeOrWorldStart() {
        return false;
    }

    public void setRedstonePowered(boolean powered) {
        this.isRedstonePowered = powered;
        this.markDirty();
    }

    public boolean canPlayerUse(EntityPlayer player) {
        return player.getDistanceSq(this.getPos().getX() + 0.5D,
                this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64
                && !this.isInvalid() && this.world.getTileEntity(this.pos) == this;
    }

    protected boolean sendUpdateWithInterval() {
        if (this.ticksElapsed % ticksPerUpdate == 0) {
            this.sendUpdate();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return this.getCapability(capability, facing) != null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            IItemHandler handler = this.getItemHandler(facing);
            if (handler != null) {
                return (T) handler;
            }
        } else if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            IFluidHandler tank = this.getFluidHandler(facing);
            if (tank != null) {
                return (T) tank;
            }
        } else if (capability == CapabilityEnergy.ENERGY) {
            IEnergyStorage storage = this.getEnergyStorage(facing);
            if (storage != null) {
                return (T) storage;
            }
        }

        return super.getCapability(capability, facing);
    }

    public IItemHandler getItemHandler(EnumFacing facing) {
        return null;
    }

    public IFluidHandler getFluidHandler(EnumFacing facing) {
        return null;
    }

    public IEnergyStorage getEnergyStorage(EnumFacing facing) {
        return null;
    }

    public enum NBTType {
        /**
         * use when normal writeToNBT/readToNBT is expected
         */
        SAVE_TILE,
        /**
         * use when data needs to be sent to the client
         */
        SYNC,
        /**
         * idk what this is
         */
        SAVE_BLOCK
    }

    protected void markChanged() {
        this.world.markBlockRangeForRenderUpdate(pos, pos);
        this.world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
        this.world.scheduleBlockUpdate(pos,this.getBlockType(),0,0);
        markDirty();
    }
}