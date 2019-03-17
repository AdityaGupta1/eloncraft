/*
 * This file ("SlotOutput.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package org.sdoaj.items.blocks.machines.gui.slot;

import net.minecraft.item.ItemStack;
import org.sdoaj.util.ItemStackHandler;

public class SlotOutput extends SlotItemHandlerUnconditioned {
    public SlotOutput(ItemStackHandler inventory, int id, int x, int y) {
        super(inventory, id, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return false;
    }
}