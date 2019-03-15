package org.sdoaj.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import org.sdoaj.items.blocks.ModBlocks;
import org.sdoaj.items.items.ModItems;

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
            () -> new ItemStack(ModBlocks.components));
    public static final CreativeTabs TESLA = createTab("tesla",
            () -> new ItemStack(Items.MINECART));
    public static final CreativeTabs SPACEX = createTab("spacex",
            () -> new ItemStack(Items.REDSTONE));
    public static final CreativeTabs BORING_COMPANY = createTab("boring_company",
            () -> new ItemStack(ModItems.flamethrower));
}
