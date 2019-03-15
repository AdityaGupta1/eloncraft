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

    public static Item aluminumIngot;
    public static Item titaniumIngot;
    public static Item lithiumIngot;
    public static Item nickelIngot;
    public static Item chromiumIngot;

    public static Item flamethrower;

    public static void init() {
        aluminumIngot = new ItemBasic("aluminum_ingot");
        aluminumIngot.setCreativeTab(ModCreativeTabs.ELONCRAFT);
        titaniumIngot = new ItemBasic("titanium_ingot");
        titaniumIngot.setCreativeTab(ModCreativeTabs.ELONCRAFT);
        lithiumIngot = new ItemBasic("lithium_ingot");
        lithiumIngot.setCreativeTab(ModCreativeTabs.ELONCRAFT);
        nickelIngot = new ItemBasic("nickel_ingot");
        nickelIngot.setCreativeTab(ModCreativeTabs.ELONCRAFT);
        chromiumIngot = new ItemBasic("chromium_ingot");
        chromiumIngot.setCreativeTab(ModCreativeTabs.ELONCRAFT);

        flamethrower = new ItemRangedWeapon("flamethrower", world -> {
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
