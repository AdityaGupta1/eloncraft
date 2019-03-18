package org.sdoaj.items.recipes;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static org.sdoaj.items.blocks.ModBlocks.*;
import static org.sdoaj.items.items.ModItems.*;

public class ModSmeltingRecipes {
    public static void init() {
        GameRegistry.addSmelting(ALUMINUM_ORE, new ItemStack(ALUMINUM_INGOT), 0.7f);
        GameRegistry.addSmelting(TITANIUM_ORE, new ItemStack(TITANIUM_INGOT), 0.7f);
        GameRegistry.addSmelting(LITHIUM_ORE, new ItemStack(LITHIUM_INGOT), 0.7f);
        GameRegistry.addSmelting(NICKEL_ORE, new ItemStack(NICKEL_INGOT), 0.7f);
        GameRegistry.addSmelting(CHROMIUM_ORE, new ItemStack(CHROMIUM_INGOT), 0.7f);
    }
}
