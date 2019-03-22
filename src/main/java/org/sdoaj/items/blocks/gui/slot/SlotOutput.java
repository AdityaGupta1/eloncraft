/*
 * This file ("SlotOutput.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package org.sdoaj.items.blocks.gui.slot;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.sdoaj.util.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.function.BiConsumer;

public class SlotOutput extends SlotItemHandlerUnconditioned {
    private final BiConsumer<EntityPlayer, ItemStack> onSlotChanged;

    public SlotOutput(ItemStackHandler inventory, int id, int x, int y, BiConsumer<EntityPlayer, ItemStack> onSlotChanged) {
        super(inventory, id, x, y);
        this.onSlotChanged = onSlotChanged;
    }

    public SlotOutput(ItemStackHandler inventory, int id, int x, int y) {
        this(inventory, id, x, y, (player, stack) -> {});
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return false;
    }

    @Override
    public ItemStack onTake(EntityPlayer player, ItemStack stack) {
        onSlotChanged.accept(player, stack);
        return super.onTake(player, stack);
    }
}