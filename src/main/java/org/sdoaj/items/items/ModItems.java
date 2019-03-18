package org.sdoaj.items.items;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import org.sdoaj.eloncraft.Main;
import org.sdoaj.items.ModCreativeTabs;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class ModItems {
    private static final List<ItemBasic> items = new ArrayList<>();

    public static Item ALUMINUM_INGOT;
    public static Item TITANIUM_INGOT;
    public static Item LITHIUM_INGOT;
    public static Item NICKEL_INGOT;
    public static Item CHROMIUM_INGOT;

    public static Item IRON_PLATE;
    public static Item GOLD_PLATE;
    public static Item ALUMINUM_PLATE;
    public static Item TITANIUM_PLATE;
    public static Item LITHIUM_PLATE;
    public static Item NICKEL_PLATE;
    public static Item CHROMIUM_PLATE;

    public static Item INCONEL_BARS;

    public static Item FLAMETHROWER;

    public static void init() {
        ALUMINUM_INGOT = new ItemBasic("aluminum_ingot");
        TITANIUM_INGOT = new ItemBasic("titanium_ingot");
        LITHIUM_INGOT = new ItemBasic("lithium_ingot");
        NICKEL_INGOT = new ItemBasic("nickel_ingot");
        CHROMIUM_INGOT = new ItemBasic("chromium_ingot");

        IRON_PLATE = new ItemBasic("iron_plate");
        GOLD_PLATE = new ItemBasic("gold_plate");
        ALUMINUM_PLATE = new ItemBasic("aluminum_plate");
        TITANIUM_PLATE = new ItemBasic("titanium_plate");
        LITHIUM_PLATE = new ItemBasic("lithium_plate");
        NICKEL_PLATE = new ItemBasic("nickel_plate");
        CHROMIUM_PLATE = new ItemBasic("chromium_plate");

        INCONEL_BARS = new ItemBasic("inconel_bars");

        FLAMETHROWER = new ItemRangedWeapon("flamethrower", world -> {
                    EntityFallingBlock fire = new EntityFallingBlock(world, 0, 0, 0, Blocks.FIRE.getDefaultState());
                    fire.fallTime = 1;
                    return fire;
                }, 5, 5.0, 1.0, null).setMaxStackSize(1)
        .setCreativeTab(ModCreativeTabs.BORING_COMPANY);
    }

    static void addItem(ItemBasic item) {
        items.add(item);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        items.forEach(registry::register);
    }

    private static void registerRender(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

    @SubscribeEvent
    public static void registerRenders(ModelRegistryEvent event) {
        items.forEach(ModItems::registerRender);
    }
}
