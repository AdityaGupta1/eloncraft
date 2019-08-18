package org.sdoaj.eloncraft.recipes;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
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

        addSmelting(ModItems.BROKEN_STEEL_GEAR, ModItems.STEEL_NUGGET);
        addSmelting(ModItems.DAMAGED_AIRCRAFT_PLATING, ModItems.ALUMINUM_INGOT);

        addSmelting(ModItems.UNTREATED_CARBON_FIBERS, ModItems.CARBON_FIBERS);

        addSmelting(ModItems.IRON_DUST, Items.IRON_INGOT);
        addSmelting(ModItems.GOLD_DUST, Items.GOLD_INGOT);
        addSmelting(ModItems.ALUMINUM_DUST, ModItems.ALUMINUM_INGOT);
        addSmelting(ModItems.TITANIUM_DUST, ModItems.TITANIUM_INGOT);
        addSmelting(ModItems.LITHIUM_DUST, ModItems.LITHIUM_INGOT);
        addSmelting(ModItems.NICKEL_DUST, ModItems.NICKEL_INGOT);
        addSmelting(ModItems.CHROMIUM_DUST, ModItems.CHROMIUM_INGOT);
        addSmelting(ModItems.COPPER_DUST, ModItems.COPPER_INGOT);
        addSmelting(ModItems.NIOBIUM_DUST, ModItems.NIOBIUM_INGOT);
        addSmelting(ModItems.HAFNIUM_DUST, ModItems.HAFNIUM_INGOT);
        addSmelting(ModItems.MAGNESIUM_DUST, ModItems.MAGNESIUM_INGOT);
        addSmelting(ModItems.ZINC_DUST, ModItems.ZINC_INGOT);

        addSmelting(ModBlocks.ABYSSAL_ORE, ModItems.ABYSSAL_INGOT);
        addSmelting(ModBlocks.PALLASITE_ORE, new ItemStack(ModItems.REFINED_PALLASITE, 2));

        addSmelting(ModItems.ABYSSAL_DUST, ModItems.ABYSSAL_INGOT);
    }

    private static void addSmelting(Item input, Item output) {
        GameRegistry.addSmelting(input, new ItemStack(output), 0.7f);
    }

    private static void addSmelting(Item input, Block output) {
        GameRegistry.addSmelting(input, new ItemStack(output), 0.7f);
    }

    private static void addSmelting(Item input, ItemStack output) {
        GameRegistry.addSmelting(input, output.copy(), 0.7f);
    }

    private static void addSmelting(Block input, Item output) {
        GameRegistry.addSmelting(input, new ItemStack(output), 0.7f);
    }

    private static void addSmelting(Block input, Block output) {
        GameRegistry.addSmelting(input, new ItemStack(output), 0.7f);
    }

    private static void addSmelting(Block input, ItemStack output) {
        GameRegistry.addSmelting(input, output.copy(), 0.7f);
    }
}
