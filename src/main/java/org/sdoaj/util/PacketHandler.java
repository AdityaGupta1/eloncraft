package org.sdoaj.util;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import org.sdoaj.blocks.tileentities.MessageButtonPressed;
import org.sdoaj.eloncraft.Main;

public class PacketHandler {
    private static final SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel(Main.MODID);
    private static int packetId = 0;

    static {
        network.registerMessage(MessageButtonPressed.Handler.class, MessageButtonPressed.class, packetId++, Side.SERVER);
    }

    public static void sendToServer(IMessage message) {
        network.sendToServer(message);
    }
}
