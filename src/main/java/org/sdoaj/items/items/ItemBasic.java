package org.sdoaj.items.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.sdoaj.items.ModCreativeTabs;

import java.util.ArrayList;
import java.util.List;

public class ItemBasic extends Item {
    public ItemBasic(String name) {
        this(name, ModCreativeTabs.ELONCRAFT);
    }

    public ItemBasic(String name, CreativeTabs tab) {
        setUnlocalizedName(name);
        setRegistryName(name);
        ModItems.addItem(this);
        setCreativeTab(tab);
    }

    private final List<String> lore = new ArrayList<>();

    public void addLore(String lore) {
        this.lore.add(lore);
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
        tooltip.addAll(lore);
    }

    private boolean glows = false;

    public void setGlows() {
        glows = true;
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return glows;
    }
}