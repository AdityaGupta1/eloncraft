// based on TileEntityGrinder from Actually Additions

package org.sdoaj.eloncraft.blocks.machines.generator;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.sdoaj.eloncraft.blocks.machines.BlockMachine;
import org.sdoaj.eloncraft.blocks.tileentities.CustomEnergyStorage;
import org.sdoaj.eloncraft.blocks.tileentities.ISharingEnergyProvider;
import org.sdoaj.eloncraft.blocks.tileentities.TileEntityInventoryMachine;
import org.sdoaj.eloncraft.util.ItemStackHandler;
import org.sdoaj.eloncraft.util.StackUtil;

public class TileEntityGenerator extends TileEntityInventoryMachine implements ISharingEnergyProvider {
    public static final int SLOT_INPUT = 0;

    private int lastFuelLeft = 0;
    private int fuelLeft = 0;
    private int initialFuel = 0;
    private int energyPerFuel = 0;

    final int guiTopHeight = 98;

    public TileEntityGenerator() {
        super("generator", 1, 1, 0,
                new CustomEnergyStorage(100000, 0, 1000), BlockMachine.IS_ON);
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type) {
        super.writeSyncableNBT(compound, type);

        compound.setInteger("FuelLeft", fuelLeft);
        compound.setInteger("InitialFuel", initialFuel);
        compound.setInteger("EnergyPerFuel", energyPerFuel);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type) {
        super.readSyncableNBT(compound, type);

        fuelLeft = compound.getInteger("FuelLeft");
        initialFuel = compound.getInteger("InitialFuel");
        energyPerFuel = compound.getInteger("EnergyPerFuel");
    }

    @Override
    public ItemStackHandler.IAcceptor getAcceptor() {
        return (slot, stack, automation) -> !automation || slot == SLOT_INPUT && GeneratorRecipes.getRecipeFromInput(stack) != null;
    }

    @Override
    public ItemStackHandler.IRemover getRemover() {
        return (slot, automation) -> !automation || slot == SLOT_INPUT;
    }

    @Override
    public boolean canProcess() {
        return fuelLeft > 0 && energyPerFuel <= energyStorage.getMaxEnergyStored() - energyStorage.getEnergyStored();
    }

    @Override
    public void finishProcessing() {
        this.energyStorage.addEnergyRaw(energyPerFuel);
        fuelLeft--;

        if (fuelLeft > 0) {
            return;
        }

        if (!addFuelIfHasRecipe()) {
            initialFuel = 0;
            energyPerFuel = 0;
        }
    }

    @Override
    protected boolean hasChanged() {
        return super.hasChanged() || fuelLeft != lastFuelLeft;
    }

    @Override
    protected void updatePreviousValues() {
        super.updatePreviousValues();

        lastFuelLeft = fuelLeft;
    }

    boolean addFuelIfHasRecipe() {
        if (fuelLeft > 0) {
            return false;
        }

        ItemStack input = this.inventory.getStackInSlot(SLOT_INPUT);

        if (StackUtil.isValid(input)) {
            GeneratorRecipe recipe = GeneratorRecipes.getRecipeFromInput(input);

            if (recipe != null) {
                fuelLeft = initialFuel = recipe.getFuel();
                energyPerFuel = recipe.getEnergyPerFuel();
                input.shrink(1);
                return true;
            }
        }

        return false;
    }

    int getFuelUsedScaled(int i) {
        if (initialFuel == 0) {
            return 0;
        }

        return (initialFuel - fuelLeft) * i / initialFuel;
    }

    @Override
    protected boolean overrideOn() {
        return fuelLeft > 0 && energyPerFuel > energyStorage.getMaxEnergyStored() - energyStorage.getEnergyStored();
    }

    @Override
    public boolean guiShowProgress() {
        return fuelLeft > 0;
    }

    @Override
    public int getEnergyToSplitShare() {
        return this.energyStorage.getEnergyStored();
    }
}