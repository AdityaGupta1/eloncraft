package org.sdoaj.eloncraft.util;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import org.sdoaj.eloncraft.blocks.tileentities.MessageButtonPressed;
import org.sdoaj.eloncraft.Eloncraft;
import org.sdoaj.eloncraft.entity.MessageSetValueToServer;

public class PacketHandler {
    private static final SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel(Eloncraft.MODID);
    private static int packetId = 0;

    static {
        network.registerMessage(MessageButtonPressed.Handler.class, MessageButtonPressed.class, packetId++, Side.SERVER);
        network.registerMessage(MessageSetValueToServer.Handler.class, MessageSetValueToServer.class, packetId++, Side.SERVER);
    }

    public static void sendToServer(IMessage message) {
        network.sendToServer(message);
    }
}
