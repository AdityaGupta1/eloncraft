package org.sdoaj.items.recipes;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static org.sdoaj.items.blocks.ModBlocks.*;
import static org.sdoaj.items.items.ModItems.*;

public class ModSmeltingRecipes {
    public static void init() {
        GameRegistry.addSmelting(aluminumOre, new ItemStack(aluminumIngot), 0.7f);
        GameRegistry.addSmelting(titaniumOre, new ItemStack(titaniumIngot), 0.7f);
        GameRegistry.addSmelting(lithiumOre, new ItemStack(lithiumIngot), 0.7f);
        GameRegistry.addSmelting(nickelOre, new ItemStack(nickelIngot), 0.7f);
        GameRegistry.addSmelting(chromiumOre, new ItemStack(chromiumIngot), 0.7f);
    }
}
