package org.sdoaj.items.items;

import net.minecraft.item.Item;

public class ItemBasic extends Item {
    public ItemBasic(String name) {
        setUnlocalizedName(name);
        setRegistryName(name);
        ModItems.addItem(this);
    }
}