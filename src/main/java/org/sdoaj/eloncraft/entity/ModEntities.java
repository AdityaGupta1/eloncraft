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
import org.sdoaj.eloncraft.entity.rocket.falcon9.*;

@Mod.EventBusSubscriber(modid = Eloncraft.MODID)
public class ModEntities {
    @SubscribeEvent
    // @SideOnly(Side.CLIENT)
    public static void registerEntities(RegistryEvent.Register<EntityEntry> event) {
        int id = 0;

        event.getRegistry().register(EntityEntryBuilder.create()
                .entity(EntityFalcon9Stage1.class)
                .id(new ResourceLocation(Eloncraft.MODID, "falcon9_stage1"), ++id)
                .name("eloncraft:falcon9_stage1")
                .tracker(1024, 3, true)
                .build());
        event.getRegistry().register(EntityEntryBuilder.create()
                .entity(EntityFalcon9Stage2.class)
                .id(new ResourceLocation(Eloncraft.MODID, "falcon9_stage2"), ++id)
                .name("eloncraft:falcon9_stage2")
                .tracker(1024, 3, true)
                .build());
        event.getRegistry().register(EntityEntryBuilder.create()
                .entity(EntityFalcon9DragonTrunk.class)
                .id(new ResourceLocation(Eloncraft.MODID, "falcon9_dragon_trunk"), ++id)
                .name("eloncraft:falcon9_dragon_trunk")
                .tracker(1024, 3, true)
                .build());
        event.getRegistry().register(EntityEntryBuilder.create()
                .entity(EntityFalcon9DragonTop.class)
                .id(new ResourceLocation(Eloncraft.MODID, "falcon9_dragon_top"), ++id)
                .name("eloncraft:falcon9_dragon_top")
                .tracker(1024, 3, true)
                .build());
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        RenderingRegistry.registerEntityRenderingHandler(EntityFalcon9Stage1.class, RenderFalcon9Stage1::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityFalcon9Stage2.class, RenderFalcon9Stage2::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityFalcon9DragonTrunk.class, RenderFalcon9DragonTrunk::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityFalcon9DragonTop.class, RenderFalcon9DragonTop::new);
    }
}