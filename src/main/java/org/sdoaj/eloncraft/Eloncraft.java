package org.sdoaj.eloncraft;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sdoaj.eloncraft.blocks.machines.refinery.RefineryRecipes;
import org.sdoaj.eloncraft.proxy.IProxy;
import org.sdoaj.eloncraft.entity.ModEntities;
import org.sdoaj.eloncraft.blocks.ModBlocks;
import org.sdoaj.eloncraft.blocks.ModOreGen;
import org.sdoaj.eloncraft.blocks.gui.GuiHandler;
import org.sdoaj.eloncraft.blocks.machines.alloyfurnace.AlloyFurnaceRecipes;
import org.sdoaj.eloncraft.blocks.machines.crusher.CrusherRecipes;
import org.sdoaj.eloncraft.blocks.machines.metalroller.MetalRollerRecipes;
import org.sdoaj.eloncraft.blocks.machines.workbench.WorkbenchRecipes;
import org.sdoaj.eloncraft.blocks.tileentities.TileEntityBase;
import org.sdoaj.eloncraft.fluids.ModFluids;
import org.sdoaj.eloncraft.items.ModItems;
import org.sdoaj.eloncraft.recipes.ModSmeltingRecipes;

@Mod(modid = Eloncraft.MODID, name = Eloncraft.NAME, version = Eloncraft.VERSION)
public class Eloncraft {
    @Mod.Instance
    public static Eloncraft INSTANCE;

    @SidedProxy(
            clientSide="org.sdoaj.eloncraft.proxy.ClientProxy",
            serverSide="org.sdoaj.eloncraft.proxy.ServerProxy"
    )
    public static IProxy proxy;

    public static final String MODID = "eloncraft";
    public static final String NAME = "Eloncraft";
    public static final String VERSION = "0.1";

    static {
        FluidRegistry.enableUniversalBucket();
    }

    public static Logger LOGGER = LogManager.getLogger(NAME);

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOGGER.info("preInit");

        ModItems.init();
        ModBlocks.init();

        ModFluids.init();

        ModEntities.initModels();

        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        LOGGER.info("init");

        GameRegistry.registerWorldGenerator(new ModOreGen(), 0);

        GuiHandler.init();
        TileEntityBase.init();

        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        LOGGER.info("postInit");

        registerRecipes();

        proxy.postInit(event);
    }

    private void registerRecipes() {
        ModSmeltingRecipes.init();

        WorkbenchRecipes.init();

        MetalRollerRecipes.init();
        CrusherRecipes.init();
        AlloyFurnaceRecipes.init();
        RefineryRecipes.init();
    }
}
