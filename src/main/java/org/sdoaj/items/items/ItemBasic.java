package org.sdoaj.items.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.sdoaj.items.ModCreativeTabs;

import java.util.ArrayList;
import java.util.List;

public class ItemBasic extends Item {
    private int burnTime = -1;

    public ItemBasic(String name) {
        setUnlocalizedName(name);
        setRegistryName(name);
        ModItems.addItem(this);
        setCreativeTab(ModCreativeTabs.ELONCRAFT);
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

    public void setBurnTime(int burnTime) {
        this.burnTime = burnTime;
    }

    @Override
    public int getItemBurnTime(ItemStack itemStack) {
        return this.burnTime;
    }
}