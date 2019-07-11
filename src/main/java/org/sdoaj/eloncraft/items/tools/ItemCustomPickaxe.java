package org.sdoaj.eloncraft.items.tools;

import org.sdoaj.eloncraft.items.ItemBasic;

public class ItemCustomPickaxe extends net.minecraft.item.ItemPickaxe {
    public ItemCustomPickaxe(String name, ToolMaterial material) {
        super(material);
        ItemBasic.initItem(this, name);
    }
}
