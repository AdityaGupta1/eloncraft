package org.sdoaj.eloncraft.items.tools;

import net.minecraft.item.ItemPickaxe;
import org.sdoaj.eloncraft.items.ItemBasic;

public class ItemCustomPickaxe extends ItemPickaxe {
    public ItemCustomPickaxe(String name, ToolMaterial material) {
        super(material);
        ItemBasic.initItem(this, name);
    }
}
