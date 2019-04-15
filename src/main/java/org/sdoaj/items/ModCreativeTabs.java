package org.sdoaj.items;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.sdoaj.blocks.ModBlocks;

import java.util.function.Supplier;

public class ModCreativeTabs {
    // uses a Supplier<ItemStack> because otherwise the items is not initalized when the CreativeTabs is created
    private static CreativeTabs createTab(String name, Supplier<ItemStack> tabItem) {
        return new CreativeTabs(name) {
            @Override
            public ItemStack getTabIconItem() {
                return tabItem.get();
            }
        };
    }

    public static final CreativeTabs ELONCRAFT = createTab("eloncraft",
            () -> new ItemStack(ModBlocks.COMPONENTS));
    public static final CreativeTabs SPACEX = createTab("spacex",
            () -> new ItemStack(Items.REDSTONE));
    public static final CreativeTabs TESLA = createTab("tesla",
            () -> new ItemStack(Items.MINECART));
    public static final CreativeTabs BORING_COMPANY = createTab("boring_company",
            () -> new ItemStack(ModItems.FLAMETHROWER));

    public static void addAll(CreativeTabs tab, Item... items) {
        for (Item item : items) {
            item.setCreativeTab(tab);
        }
    }

    public static void addAll(CreativeTabs tab, Block... blocks) {
        for (Block block : blocks) {
            block.setCreativeTab(tab);
        }
    }
}
