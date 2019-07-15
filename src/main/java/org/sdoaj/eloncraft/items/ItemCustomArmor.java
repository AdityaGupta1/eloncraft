package org.sdoaj.eloncraft.items;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;

public class ItemCustomArmor extends ItemArmor {
    public ItemCustomArmor(String name, ArmorMaterial material, EntityEquipmentSlot slot) {
        super(material, 0, slot);
        ItemBasic.initItem(this, name);
    }
}
