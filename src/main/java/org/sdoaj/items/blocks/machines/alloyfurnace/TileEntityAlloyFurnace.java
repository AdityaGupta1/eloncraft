// based on TileEntityGrinder from Actually Additions

package org.sdoaj.items.blocks.machines.alloyfurnace;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.sdoaj.items.blocks.machines.TileEntityInventoryBase;
import org.sdoaj.util.ItemStackHandler;
import org.sdoaj.util.StackUtil;
import org.sdoaj.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TileEntityAlloyFurnace extends TileEntityInventoryBase {
    public static final int SLOT_INPUT_1 = 0;
    public static final int INPUT_SLOTS = 10;
    public static final int SLOT_OUTPUT = 10;
    public int processTime;
    private int lastProcess;
    private boolean lastProcessed;

    final int guiTopHeight = 79;

    public TileEntityAlloyFurnace() {
        super(11, "alloy_furnace");
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type) {
        if (type != NBTType.SAVE_BLOCK) {
            compound.setInteger("FirstProcessTime", this.processTime);
        }

        super.writeSyncableNBT(compound, type);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type) {
        if (type != NBTType.SAVE_BLOCK) {
            this.processTime = compound.getInteger("FirstProcessTime");
        }

        super.readSyncableNBT(compound, type);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!this.world.isRemote) {
            boolean processed = false;
            boolean canProcess = this.canProcess();
            boolean shouldPlaySound = false;

            if (canProcess) {
                if (this.processTime % 20 == 0) {
                    shouldPlaySound = true;
                }

                this.processTime++;
                if (this.processTime >= this.getMaxProcessTime()) {
                    this.finishProcessing();
                    this.processTime = 0;
                }

                processed = true;
            } else {
                this.processTime = 0;
            }

            IBlockState currentState = this.world.getBlockState(this.pos);
            boolean current = currentState.getValue(BlockAlloyFurnace.IS_ON);
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
                world.setBlockState(this.pos, currentState.withProperty(BlockAlloyFurnace.IS_ON, changeTo));
            }

            this.lastProcessed = processed;

            if ((this.lastProcess != this.processTime) && this.sendUpdateWithInterval()) {
                this.lastProcess = this.processTime;
            }

            // TODO sound
            // if (shouldPlaySound) {
            //     this.world.playSound(null, this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), SoundHandler.crusher, SoundCategory.BLOCKS, 0.025F, 1.0F);
            // }
        }
    }

    @Override
    public ItemStackHandler.IAcceptor getAcceptor() {
        return (slot, stack, automation) -> !automation || (slot >= SLOT_INPUT_1 && slot < SLOT_INPUT_1 + INPUT_SLOTS);
    }

    @Override
    public ItemStackHandler.IRemover getRemover() {
        return (slot, automation) -> !automation || slot == SLOT_OUTPUT;
    }

    private List<ItemStack> getInputStacks() {
        return IntStream.range(SLOT_INPUT_1, SLOT_INPUT_1 + INPUT_SLOTS).mapToObj(this.inventory::getStackInSlot).collect(Collectors.toList());
    }

    public boolean canProcess() {
        boolean valid = true;

        for (ItemStack stack : getInputStacks()) {
            if (!StackUtil.isValid(stack)) {
                valid = false;
            }
        }

        if (valid) {
            AlloyFurnaceRecipe recipe = AlloyFurnaceRecipes.getRecipeFromInput(getInputStacks());
            if (recipe == null) {
                return false;
            }

            ItemStack outputStack = recipe.getOutput();
            if (StackUtil.isValid(outputStack)) {
                if (outputStack.getItemDamage() == Util.wildcard) {
                    outputStack.setItemDamage(0);
                }
                return (!StackUtil.isValid(this.inventory.getStackInSlot(SLOT_OUTPUT))
                        || this.inventory.getStackInSlot(SLOT_OUTPUT).isItemEqual(outputStack)
                        && this.inventory.getStackInSlot(SLOT_OUTPUT).getCount() <= this.inventory.getStackInSlot(SLOT_OUTPUT).getMaxStackSize() - outputStack.getCount());
            }
        }

        return false;
    }

    private int getMaxProcessTime() {
        return 600;
    }

    public void finishProcessing() {
        AlloyFurnaceRecipe recipe = AlloyFurnaceRecipes.getRecipeFromInput(getInputStacks());
        if (recipe == null) {
            return;
        }

        ItemStack outputStack = recipe.getOutput();
        if (StackUtil.isValid(outputStack)) {
            if (outputStack.getItemDamage() == Util.wildcard) {
                outputStack.setItemDamage(0);
            }

            if (!StackUtil.isValid(this.inventory.getStackInSlot(SLOT_OUTPUT))) {
                this.inventory.setStackInSlot(SLOT_OUTPUT, outputStack.copy());
            } else if (this.inventory.getStackInSlot(SLOT_OUTPUT).getItem() == outputStack.getItem()) {
                this.inventory.setStackInSlot(SLOT_OUTPUT, StackUtil.grow(this.inventory.getStackInSlot(SLOT_OUTPUT), outputStack.getCount()));
            }
        }

        getInputStacks().forEach(stack -> stack.shrink(1));
    }

    public int getTimeToScale(int i) {
        return this.processTime * i / this.getMaxProcessTime();
    }
}