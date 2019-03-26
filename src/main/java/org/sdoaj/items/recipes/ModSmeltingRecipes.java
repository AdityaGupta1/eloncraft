package org.sdoaj.items.recipes;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static org.sdoaj.items.blocks.ModBlocks.*;
import static org.sdoaj.items.items.ModItems.*;

public class ModSmeltingRecipes {
    public static void init() {
        addSmelting(ALUMINUM_ORE, ALUMINUM_INGOT);
        addSmelting(TITANIUM_ORE, TITANIUM_INGOT);
        addSmelting(LITHIUM_ORE, LITHIUM_INGOT);
        addSmelting(NICKEL_ORE, NICKEL_INGOT);
        addSmelting(CHROMIUM_ORE, CHROMIUM_INGOT);
        addSmelting(COPPER_ORE, COPPER_INGOT);
        addSmelting(NIOBIUM_ORE, NIOBIUM_INGOT);
        addSmelting(HAFNIUM_ORE, HAFNIUM_INGOT);
        addSmelting(MAGNESIUM_ORE, MAGNESIUM_INGOT);
        addSmelting(ZINC_ORE, ZINC_INGOT);

        addSmelting(BROKEN_STEEL_GEAR, STEEL_INGOT);

        addSmelting(UNTREATED_CARBON_FIBERS, CARBON_FIBERS);
    }

    private static void addSmelting(Item input, Item output) {
        GameRegistry.addSmelting(input, new ItemStack(output), 0.7f);
    }

    private static void addSmelting(Block input, Block output) {
        GameRegistry.addSmelting(input, new ItemStack(output), 0.7f);
    }

    private static void addSmelting(Block input, Item output) {
        GameRegistry.addSmelting(input, new ItemStack(output), 0.7f);
    }

    private static void addSmelting(Item input, Block output) {
        GameRegistry.addSmelting(input, new ItemStack(output), 0.7f);
    }
}
