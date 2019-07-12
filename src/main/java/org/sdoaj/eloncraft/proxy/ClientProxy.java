package org.sdoaj.eloncraft.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.sdoaj.eloncraft.Eloncraft;
import org.sdoaj.eloncraft.blocks.machines.alloyfurnace.AlloyFurnaceRecipes;
import org.sdoaj.eloncraft.blocks.machines.crusher.CrusherRecipes;
import org.sdoaj.eloncraft.blocks.machines.generator.GeneratorRecipes;
import org.sdoaj.eloncraft.blocks.machines.metalroller.MetalRollerRecipes;
import org.sdoaj.eloncraft.blocks.machines.refinery.RefineryRecipes;
import org.sdoaj.eloncraft.blocks.machines.workbench.WorkbenchRecipes;
import org.sdoaj.eloncraft.blocks.tileentities.TileEntityBase;
import org.sdoaj.eloncraft.entity.ModEntities;
import org.sdoaj.eloncraft.fluids.ModFluids;
import org.sdoaj.eloncraft.recipes.ModSmeltingRecipes;

public class ClientProxy implements IProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        Eloncraft.LOGGER.info("client preInit");

        ModEntities.initModels();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        Eloncraft.LOGGER.info("client init");
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        Eloncraft.LOGGER.info("client postInit");

        registerRecipes();
    }

    @Override
    public void serverStarting(FMLServerStartingEvent event) {
        // doesn't get called on client side
    }

    private void registerRecipes() {
        ModSmeltingRecipes.init();

        WorkbenchRecipes.init();

        MetalRollerRecipes.init();
        CrusherRecipes.init();
        AlloyFurnaceRecipes.init();
        RefineryRecipes.init();

        GeneratorRecipes.init();
    }
}