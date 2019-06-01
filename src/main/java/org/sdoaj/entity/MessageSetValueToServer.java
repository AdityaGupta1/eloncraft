package org.sdoaj.entity;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.nio.charset.StandardCharsets;

public class MessageSetValueToServer implements IMessage {
    private int entityId;
    private String name;
    private double value;

    public MessageSetValueToServer() {}

    public <T extends Entity & ReceivesSetValueMessages> MessageSetValueToServer(T entity, String name, double value) {
        this.entityId = entity.getEntityId();
        this.name = name;
        this.value = value;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.entityId = buf.readInt();
        byte[] nameBytes = new byte[buf.readInt()];
        buf.readBytes(nameBytes);
        this.name = new String(nameBytes, StandardCharsets.UTF_8);
        this.value = buf.readDouble();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(entityId);
        byte[] nameBytes = name.getBytes(StandardCharsets.UTF_8);
        buf.writeInt(nameBytes.length);
        buf.writeBytes(nameBytes);
        buf.writeDouble(value);
    }

    public static class Handler implements IMessageHandler<MessageSetValueToServer, IMessage> {
        @Override
        public IMessage onMessage(MessageSetValueToServer message, MessageContext context) {
            Entity entity = context.getServerHandler().player.world.getEntityByID(message.entityId);
            ((ReceivesSetValueMessages) entity).receive(message.name, message.value);
            return null;
        }
    }
}
