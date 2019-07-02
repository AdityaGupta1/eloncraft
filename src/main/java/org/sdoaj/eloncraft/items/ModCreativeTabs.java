package org.sdoaj.eloncraft.items;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.sdoaj.eloncraft.blocks.ModBlocks;

import java.util.Arrays;
import java.util.function.Supplier;

public class ModCreativeTabs {
    // uses a Supplier<ItemStack> because otherwise the items is not initalized when the CreativeTabs is created
    private static ModCreativeTab createTab(String name, Supplier<ItemStack> tabItem) {
        return new ModCreativeTab(name) {
            @Override
            public ItemStack getTabIconItem() {
                return tabItem.get();
            }
        };
    }

    public static final ModCreativeTab ELONCRAFT = createTab("eloncraft",
            () -> new ItemStack(ModBlocks.COMPONENTS));
    public static final ModCreativeTab SPACEX = createTab("spacex",
            () -> new ItemStack(ModItems.FALCON9));
    public static final ModCreativeTab TESLA = createTab("tesla",
            () -> new ItemStack(Items.MINECART));
    public static final ModCreativeTab BORING_COMPANY = createTab("boring_company",
            () -> new ItemStack(ModItems.FLAMETHROWER));

    public static abstract class ModCreativeTab extends CreativeTabs {
        private ModCreativeTab(String name) {
            super(name);
        }

        public void addAll(Item... items) {
            Arrays.stream(items).forEach(item -> item.setCreativeTab(this));
        }

        public void addAll(Block... blocks) {
            Arrays.stream(blocks).forEach(item -> item.setCreativeTab(this));
        }
    }
}