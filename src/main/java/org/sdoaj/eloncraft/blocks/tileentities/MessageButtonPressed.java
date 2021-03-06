package org.sdoaj.eloncraft.blocks.tileentities;

import io.netty.buffer.ByteBuf;
import net.minecraft.inventory.Container;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageButtonPressed implements IMessage {
    private byte id;

    public MessageButtonPressed() {}

    public MessageButtonPressed(int id) {
        this.id = (byte) id;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.id = buf.readByte();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeByte(id);
    }

    private int getId() {
        return (int) id;
    }

    public static class Handler implements IMessageHandler<MessageButtonPressed, IMessage> {
        @Override
        public IMessage onMessage(MessageButtonPressed message, MessageContext context) {
            System.out.println("message handler");
            
            FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask(() -> {
                Container container = context.getServerHandler().player.openContainer;

                if (container instanceof ContainerBase) {
                    ((ContainerBase) container).getTileEntity().onButtonPressed(message.getId());
                }
            });

            return null;
        }
    }
}
