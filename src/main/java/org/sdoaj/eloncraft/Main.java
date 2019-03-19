package org.sdoaj.eloncraft;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.sdoaj.items.blocks.ModBlocks;
import org.sdoaj.items.blocks.ModOreGen;
import org.sdoaj.items.blocks.tileentities.TileEntityBase;
import org.sdoaj.items.blocks.machines.alloyfurnace.AlloyFurnaceRecipes;
import org.sdoaj.items.blocks.gui.GuiHandler;
import org.sdoaj.items.blocks.machines.metalroller.MetalRollerRecipes;
import org.sdoaj.items.items.ModItems;
import org.sdoaj.items.recipes.ModSmeltingRecipes;

@Mod(modid = Main.MODID, name = Main.NAME, version = Main.VERSION)
public class Main {
    @Mod.Instance
    public static Main INSTANCE;

    public static final String MODID = "eloncraft";
    public static final String NAME = "ElonCraft";
    public static final String VERSION = "0.1";

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        System.out.println(MODID + ": preInit");

        ModItems.init();
        ModBlocks.init();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        System.out.println(MODID + ": init");

        GameRegistry.registerWorldGenerator(new ModOreGen(), 0);

        GuiHandler.init();
        TileEntityBase.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        System.out.println(MODID + ": postInit");

        ModSmeltingRecipes.init();

        MetalRollerRecipes.init();
        AlloyFurnaceRecipes.init();
    }
}
