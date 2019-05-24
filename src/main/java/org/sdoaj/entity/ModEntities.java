package org.sdoaj.entity;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.sdoaj.eloncraft.Main;
import org.sdoaj.entity.falcon9.EntityFalcon9Stage1;
import org.sdoaj.entity.falcon9.EntityFalcon9Stage2;
import org.sdoaj.entity.falcon9.RenderFalcon9Stage1;
import org.sdoaj.entity.falcon9.RenderFalcon9Stage2;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class ModEntities {
    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityEntry> event) {
        int id = 0;

        event.getRegistry().register(EntityEntryBuilder.create()
                .entity(EntityFalcon9Stage1.class)
                .id(new ResourceLocation(Main.MODID,"falcon9_stage1"), ++id)
                .name("eloncraft:falcon9_stage1")
                .tracker(64, 1, false)
                .build());
        event.getRegistry().register(EntityEntryBuilder.create()
                .entity(EntityFalcon9Stage2.class)
                .id(new ResourceLocation(Main.MODID,"falcon9_stage2"), ++id)
                .name("eloncraft:falcon9_stage2")
                .tracker(64, 1, false)
                .build());
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        RenderingRegistry.registerEntityRenderingHandler(EntityFalcon9Stage1.class, RenderFalcon9Stage1::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityFalcon9Stage2.class, RenderFalcon9Stage2::new);
    }
}