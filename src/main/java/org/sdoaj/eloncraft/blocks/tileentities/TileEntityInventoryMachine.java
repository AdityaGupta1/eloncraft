package org.sdoaj.eloncraft.blocks.tileentities;

import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.IEnergyStorage;
import org.sdoaj.eloncraft.blocks.tileentities.CustomEnergyStorage;
import org.sdoaj.eloncraft.blocks.tileentities.TileEntityInventoryBase;

public abstract class TileEntityInventoryMachine extends TileEntityInventoryBase {
    private final int maxProcessTime;
    private final int energyPerOperation;
    private int processTime;
    private int lastProcess;
    private boolean lastProcessed;

    private final boolean hasEnergyStorage;
    private final CustomEnergyStorage energyStorage;
    private int lastEnergy;

    protected final PropertyBool IS_ON;

    public TileEntityInventoryMachine(String name, int slots, int maxProcessTime, int energyPerOperation,
                                      CustomEnergyStorage energyStorage, PropertyBool on) {
        super(name, slots);
        this.maxProcessTime = maxProcessTime;
        this.energyPerOperation = energyPerOperation;

        this.hasEnergyStorage = energyStorage != null;
        this.energyStorage = energyStorage;

        this.IS_ON = on;
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type) {
        if (type != NBTType.SAVE_BLOCK) {
            compound.setInteger("ProcessTime", this.processTime);
        }

        if (hasEnergyStorage) {
            this.energyStorage.writeToNBT(compound);
        }
        super.writeSyncableNBT(compound, type);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type) {
        if (type != NBTType.SAVE_BLOCK) {
            this.processTime = compound.getInteger("ProcessTime");
        }

        if (hasEnergyStorage) {
            this.energyStorage.readFromNBT(compound);
        }
        super.readSyncableNBT(compound, type);
    }

    private boolean hasChanged = false;

    protected boolean hasChanged() {
        if (hasChanged) {
           hasChanged = false;
           return true;
        }

        return this.lastProcess != this.processTime || (hasEnergyStorage && (this.lastEnergy != this.energyStorage.getEnergyStored()));
    }

    protected void setChanged() {
        hasChanged = true;
    }

    protected void updatePreviousValues() {
        this.lastProcess = this.processTime;
        if (hasEnergyStorage) {
            this.lastEnergy = this.energyStorage.getEnergyStored();
        }
    }

    @Override
    public void updateEntity() {
        super.updateEntity();

        if (this.world.isRemote) {
            return;
        }

        boolean processed = false;
        boolean canProcess = this.canProcess();

        if (canProcess) {
            if (!hasEnergyStorage || this.energyStorage.getEnergyStored() >= getEnergyPerTick()) {
                this.processTime++;
                if (this.processTime >= this.maxProcessTime) {
                    this.finishProcessing();
                    this.processTime = 0;
                }
                if (hasEnergyStorage) {
                    this.energyStorage.extractEnergyInternal(getEnergyPerTick(), false);
                }
            }

            if (hasEnergyStorage) {
                processed = this.energyStorage.getEnergyStored() >= getEnergyPerTick();
            }
        } else {
            this.processTime = 0;
        }

        if (IS_ON != null) {
            IBlockState currentState = this.world.getBlockState(this.pos);
            boolean current = currentState.getValue(IS_ON);
            boolean changeTo = current;
            if (lastProcessed != processed) {
                changeTo = processed;
            }
            if (this.isRedstonePowered) {
                changeTo = true;
            }
            if (!processed && !this.isRedstonePowered) {
                changeTo = false;
            }

            if (changeTo != current) {
                world.setBlockState(this.pos, currentState.withProperty(IS_ON, changeTo));
            }
        }

        this.lastProcessed = processed;

        if (this.hasChanged() && this.sendUpdateWithInterval()) {
            this.updatePreviousValues();
            this.markChanged();
        }
    }

    public abstract boolean canProcess();

    public abstract void finishProcessing();

    public void reset() {
        this.processTime = 0;
        this.lastProcess = 0;
    }

    public boolean guiShowProgress() {
        return processTime > 0;
    }

    private int getEnergyPerTick() {
        return energyPerOperation / maxProcessTime;
    }

    public boolean hasEnergyForTick() {
        return energyStorage.getEnergyStored() > getEnergyPerTick();
    }

    @Override
    public IEnergyStorage getEnergyStorage(EnumFacing facing) {
        return energyStorage;
    }

    public CustomEnergyStorage getCustomEnergyStorage() {
        return energyStorage;
    }

    public int getTimeScaled(int i) {
        return this.processTime * i / this.maxProcessTime;
    }
}
