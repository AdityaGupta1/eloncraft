package org.sdoaj.eloncraft.entity;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.sdoaj.eloncraft.Eloncraft;
import org.sdoaj.eloncraft.entity.falcon9.*;

@Mod.EventBusSubscriber(modid = Eloncraft.MODID)
public class ModEntities {
    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityEntry> event) {
        int id = 0;

        event.getRegistry().register(EntityEntryBuilder.create()
                .entity(EntityFalcon9Stage1.class)
                .id(new ResourceLocation(Eloncraft.MODID,"falcon9_stage1"), ++id)
                .name("proxy:falcon9_stage1")
                .tracker(64, 1, false)
                .build());
        event.getRegistry().register(EntityEntryBuilder.create()
                .entity(EntityFalcon9Stage2.class)
                .id(new ResourceLocation(Eloncraft.MODID,"falcon9_stage2"), ++id)
                .name("proxy:falcon9_stage2")
                .tracker(64, 1, false)
                .build());
        event.getRegistry().register(EntityEntryBuilder.create()
                .entity(EntityFalcon9Dragon.class)
                .id(new ResourceLocation(Eloncraft.MODID,"falcon9_dragon"), ++id)
                .name("proxy:falcon9_dragon")
                .tracker(64, 1, false)
                .build());
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        RenderingRegistry.registerEntityRenderingHandler(EntityFalcon9Stage1.class, RenderFalcon9Stage1::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityFalcon9Stage2.class, RenderFalcon9Stage2::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityFalcon9Dragon.class, RenderFalcon9Dragon::new);
    }
}