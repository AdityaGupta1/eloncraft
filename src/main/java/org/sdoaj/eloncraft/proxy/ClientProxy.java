package org.sdoaj.eloncraft.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.sdoaj.eloncraft.Eloncraft;

public class ClientProxy implements IProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        Eloncraft.LOGGER.info("client preInit");
    }

    @Override
    public void init(FMLInitializationEvent event) {
        Eloncraft.LOGGER.info("client init");
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        Eloncraft.LOGGER.info("client postInit");
    }

    @Override
    public void serverStarting(FMLServerStartingEvent event) {
        // doesn't get called on client side
    }
}