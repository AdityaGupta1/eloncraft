/*
 * This file ("SlotOutput.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package org.sdoaj.blocks.gui.slot;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.sdoaj.util.ItemStackHandler;

import java.util.function.BiConsumer;

public class SlotOutput extends SlotItemHandlerUnconditioned {
    private final BiConsumer<EntityPlayer, ItemStack> onItemTake;

    public SlotOutput(ItemStackHandler inventory, int id, int x, int y,
                      BiConsumer<EntityPlayer, ItemStack> onItemTake) {
        super(inventory, id, x, y);
        this.onItemTake = onItemTake;
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
        onItemTake.accept(player, stack);
        return super.onTake(player, stack);
    }
}