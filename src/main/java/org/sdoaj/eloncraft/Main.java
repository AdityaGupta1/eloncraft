package org.sdoaj.eloncraft;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.sdoaj.item.ModItems;

@Mod(modid = Main.MODID, name = Main.NAME, version = Main.VERSION)
public class Main {
    public static final String MODID = "eloncraft";
    public static final String NAME = "ElonCraft";
    public static final String VERSION = "0.1";

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        System.out.println(MODID + ": preInit");
        ModItems.init();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        System.out.println(MODID + ": init");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        System.out.println(MODID + ": postInit");
    }
}
