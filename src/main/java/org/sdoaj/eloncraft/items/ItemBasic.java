package org.sdoaj.eloncraft.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.sdoaj.eloncraft.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class ItemBasic extends Item {
    private int burnTime = -1;

    ItemBasic(String name) {
        initItem(this, name);
    }

    public static void initItem(Item item, String name) {
        item.setUnlocalizedName(name);
        item.setRegistryName(name);
        ModItems.addItem(item);
        item.setCreativeTab(ModCreativeTabs.ELONCRAFT);
    }

    private final List<String> lore = new ArrayList<>();

    void addLore(String lore) {
        this.lore.add(lore);
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
        tooltip.addAll(lore);
    }

    private boolean glows = false;

    void setGlows() {
        glows = true;
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return glows;
    }

    void setBurnTime(int burnTime) {
        this.burnTime = burnTime;
    }

    @Override
    public int getItemBurnTime(ItemStack itemStack) {
        return this.burnTime;
    }

    ItemBasic setOreDictName(String name) {
        ModItems.setOreDictName(this, name);
        return this;
    }

    private static ItemBasic newOfType(String material, String type) {
        return new ItemBasic(material + "_" + type).setOreDictName(type + StringUtil.capitalizeFirstLetter(material));
    }

    static ItemBasic newIngot(String material) {
        return newOfType(material, "ingot");
    }

    static ItemBasic newNugget(String material) {
        return newOfType(material, "nugget");
    }

    static ItemBasic newPlate(String material) {
        return newOfType(material, "plate");
    }
}