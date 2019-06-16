// from Actually Additions

package org.sdoaj.eloncraft.blocks.tileentities;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.management.PlayerChunkMapEntry;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public final class VanillaPacketDispatcher {
    // not client-side
    public static void dispatchTEToNearbyPlayers(TileEntity tile) {
        WorldServer world = (WorldServer) tile.getWorld();
        PlayerChunkMapEntry entry = world.getPlayerChunkMap().getEntry(tile.getPos().getX() >> 4, tile.getPos().getZ() >> 4);

        if (entry == null) {
            return;
        }

        for (EntityPlayerMP player : entry.getWatchingPlayers()) {
            player.connection.sendPacket(tile.getUpdatePacket());
        }

    }

    public static void dispatchTEToNearbyPlayers(World world, BlockPos pos) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile != null) {
            dispatchTEToNearbyPlayers(tile);
        }
    }
}