package org.sdoaj.eloncraft.items.tools;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import org.sdoaj.eloncraft.items.ModItems;

public class ModToolMaterials {
    public static final Item.ToolMaterial TITANIUM = EnumHelper.addToolMaterial("TITANIUM", 4, 2048, 15.0F, 4.0F, 16);

    static {
        TITANIUM.setRepairItem(new ItemStack(ModItems.TITANIUM_INGOT));
    }
}
