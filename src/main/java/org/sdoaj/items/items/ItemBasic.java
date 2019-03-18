package org.sdoaj.items.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import org.sdoaj.items.ModCreativeTabs;

public class ItemBasic extends Item {
    public ItemBasic(String name) {
        this(name, ModCreativeTabs.ELONCRAFT);
    }

    public ItemBasic(String name, CreativeTabs tab) {
        setUnlocalizedName(name);
        setRegistryName(name);
        ModItems.addItem(this);
        setCreativeTab(tab);
    }
}