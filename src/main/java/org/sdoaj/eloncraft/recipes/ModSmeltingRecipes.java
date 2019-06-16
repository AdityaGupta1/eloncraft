package org.sdoaj.eloncraft.recipes;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.sdoaj.eloncraft.blocks.ModBlocks;
import org.sdoaj.eloncraft.items.ModItems;

public class ModSmeltingRecipes {
    public static void init() {
        addSmelting(ModBlocks.ALUMINUM_ORE, ModItems.ALUMINUM_INGOT);
        addSmelting(ModBlocks.TITANIUM_ORE, ModItems.TITANIUM_INGOT);
        addSmelting(ModBlocks.LITHIUM_ORE, ModItems.LITHIUM_INGOT);
        addSmelting(ModBlocks.NICKEL_ORE, ModItems.NICKEL_INGOT);
        addSmelting(ModBlocks.CHROMIUM_ORE, ModItems.CHROMIUM_INGOT);
        addSmelting(ModBlocks.COPPER_ORE, ModItems.COPPER_INGOT);
        addSmelting(ModBlocks.NIOBIUM_ORE, ModItems.NIOBIUM_INGOT);
        addSmelting(ModBlocks.HAFNIUM_ORE, ModItems.HAFNIUM_INGOT);
        addSmelting(ModBlocks.MAGNESIUM_ORE, ModItems.MAGNESIUM_INGOT);
        addSmelting(ModBlocks.ZINC_ORE, ModItems.ZINC_INGOT);

        addSmelting(ModItems.BROKEN_STEEL_GEAR, ModItems.STEEL_INGOT);
        addSmelting(ModItems.DAMAGED_AIRCRAFT_PLATING, ModItems.ALUMINUM_INGOT);

        addSmelting(ModItems.UNTREATED_CARBON_FIBERS, ModItems.CARBON_FIBERS);
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
