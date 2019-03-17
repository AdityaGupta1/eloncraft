// based on TileEntityGrinder from Actually Additions

package org.sdoaj.items.blocks.machines.metalroller;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.sdoaj.items.blocks.machines.TileEntityInventoryBase;
import org.sdoaj.util.ItemStackHandler;
import org.sdoaj.util.StackUtil;
import org.sdoaj.util.Util;

public class TileEntityMetalRoller extends TileEntityInventoryBase {
    public static final int SLOT_INPUT_1 = 0;
    public static final int SLOT_OUTPUT_1 = 1;
    public int processTime;
    private int lastProcess;
    private boolean lastProcessed;

    public TileEntityMetalRoller(int slots, String name) {
        super(slots, name);
    }

    public TileEntityMetalRoller() {
        this(3, "metal_roller");
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
            boolean crushed = false;
            boolean canProcess = this.canProcess(SLOT_INPUT_1, SLOT_OUTPUT_1);
            boolean shouldPlaySound = false;

            if (canProcess) {
                if (this.processTime % 20 == 0) {
                    shouldPlaySound = true;
                }

                this.processTime++;
                if (this.processTime >= this.getMaxProcessTime()) {
                    this.finishProcessing(SLOT_INPUT_1, SLOT_OUTPUT_1);
                    this.processTime = 0;
                }

                crushed = true;
            } else {
                this.processTime = 0;
            }

            IBlockState currentState = this.world.getBlockState(this.pos);
            boolean current = currentState.getValue(BlockMetalRoller.IS_ON);
            boolean changeTo = current;
            if (lastProcessed != crushed) changeTo = crushed;
            if (this.isRedstonePowered) changeTo = true;
            if (!crushed && !this.isRedstonePowered) changeTo = false;

            if (changeTo != current) {
                world.setBlockState(this.pos, currentState.withProperty(BlockMetalRoller.IS_ON, changeTo));
            }

            this.lastProcessed = crushed;

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
        return (slot, stack, automation) -> !automation || slot == SLOT_INPUT_1 && MetalRollerRecipes.getRecipeFromInput(stack) != null;
    }

    @Override
    public ItemStackHandler.IRemover getRemover() {
        return (slot, automation) -> !automation || slot == SLOT_OUTPUT_1;
    }

    public boolean canProcess(int input, int output) {
        if (StackUtil.isValid(this.inv.getStackInSlot(input))) {
            MetalRollerRecipe recipe = MetalRollerRecipes.getRecipeFromInput(this.inv.getStackInSlot(input));
            if (recipe == null) {
                return false;
            }

            ItemStack outputStack = recipe.getOutput();
            if (StackUtil.isValid(outputStack)) {
                if (outputStack.getItemDamage() == Util.wildcard) {
                    outputStack.setItemDamage(0);
                }
                return (!StackUtil.isValid(this.inv.getStackInSlot(output))
                        || this.inv.getStackInSlot(output).isItemEqual(outputStack)
                        && this.inv.getStackInSlot(output).getCount() <= this.inv.getStackInSlot(output).getMaxStackSize() - outputStack.getCount());
            }
        }

        return false;
    }

    private int getMaxProcessTime() {
        return 100;
    }

    public void finishProcessing(int input, int output) {
        MetalRollerRecipe recipe = MetalRollerRecipes.getRecipeFromInput(this.inv.getStackInSlot(input));
        if (recipe == null) {
            return;
        }

        ItemStack outputStack = recipe.getOutput();
        if (StackUtil.isValid(outputStack)) {
            if (outputStack.getItemDamage() == Util.wildcard) {
                outputStack.setItemDamage(0);
            }

            if (!StackUtil.isValid(this.inv.getStackInSlot(output))) {
                this.inv.setStackInSlot(output, outputStack.copy());
            } else if (this.inv.getStackInSlot(output).getItem() == outputStack.getItem()) {
                this.inv.setStackInSlot(output, StackUtil.grow(this.inv.getStackInSlot(output), outputStack.getCount()));
            }
        }

        this.inv.getStackInSlot(input).shrink(1);
    }

    public int getTimeToScale(int i) {
        return this.processTime * i / this.getMaxProcessTime();
    }
}