package org.sdoaj.eloncraft;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.sdoaj.entity.ModEntities;
import org.sdoaj.item.blocks.ModBlocks;
import org.sdoaj.item.blocks.ModOreGen;
import org.sdoaj.item.blocks.gui.GuiHandler;
import org.sdoaj.item.blocks.machines.alloyfurnace.AlloyFurnaceRecipes;
import org.sdoaj.item.blocks.machines.crusher.CrusherRecipes;
import org.sdoaj.item.blocks.machines.metalroller.MetalRollerRecipes;
import org.sdoaj.item.blocks.machines.workbench.WorkbenchRecipes;
import org.sdoaj.item.blocks.tileentities.TileEntityBase;
import org.sdoaj.item.fluids.ModFluids;
import org.sdoaj.item.items.ModItems;
import org.sdoaj.item.recipes.ModSmeltingRecipes;

@Mod(modid = Main.MODID, name = Main.NAME, version = Main.VERSION)
public class Main {
    @Mod.Instance
    public static Main INSTANCE;

    public static final String MODID = "eloncraft";
    public static final String NAME = "ElonCraft";
    public static final String VERSION = "0.1";

    static {
        FluidRegistry.enableUniversalBucket();
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        System.out.println(MODID + ": preInit");

        ModItems.init();
        ModBlocks.init();

        ModFluids.init();

        ModEntities.initModels();
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

        registerRecipes();
    }

    private void registerRecipes() {
        ModSmeltingRecipes.init();

        MetalRollerRecipes.init();
        AlloyFurnaceRecipes.init();
        CrusherRecipes.init();
        WorkbenchRecipes.init();
    }
}
