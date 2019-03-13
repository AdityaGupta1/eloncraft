package org.sdoaj.item;

import net.minecraft.item.Item;

/**
 * all modded items should extend this class
 */
public class ItemBasic extends Item {
    private final String name;

    public ItemBasic(String name) {
        this.name = name;
        setUnlocalizedName(name);
        setRegistryName(name);
        ModItems.addItem(this);
    }

    String getName() {
        return name;
    }
}