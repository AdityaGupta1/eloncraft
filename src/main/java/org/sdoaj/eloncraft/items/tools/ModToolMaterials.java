package org.sdoaj.eloncraft.items.tools;

import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import org.sdoaj.eloncraft.Eloncraft;
import org.sdoaj.eloncraft.items.ModItems;

public class ModToolMaterials {
    public static final Item.ToolMaterial TITANIUM = EnumHelper.addToolMaterial("TITANIUM", 4, 2048, 15.0F, 4.0F, 16);
    public static final ItemArmor.ArmorMaterial TITANIUM_ARMOR = EnumHelper.addArmorMaterial("TITANIUM", Eloncraft.MODID + ":titanium", 48, new int[]{4, 8, 10, 4}, 16, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2.5F);

    static {
        TITANIUM.setRepairItem(new ItemStack(ModItems.TITANIUM_INGOT));
        TITANIUM_ARMOR.setRepairItem(new ItemStack(ModItems.TITANIUM_INGOT));
    }
}
