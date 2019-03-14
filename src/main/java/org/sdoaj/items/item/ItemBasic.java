package org.sdoaj.items.item;

import net.minecraft.item.Item;

/**
 * all modded items should extend this class
 */
public class ItemBasic extends Item {
    public ItemBasic(String name) {
        setUnlocalizedName(name);
        setRegistryName(name);
        ModItems.addItem(this);
    }
}