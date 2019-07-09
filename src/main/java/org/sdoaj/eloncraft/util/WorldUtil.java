// based on WorldUtil from Actually Additions

package org.sdoaj.eloncraft.util;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.stream.Stream;

public class WorldUtil {
    public static int getRedstonePowerFromNeighbors(World world, BlockPos pos) {
        return Stream.of(EnumFacing.VALUES).mapToInt(facing -> world.getRedstonePower(pos, facing)).sum();
    }

    public static void doEnergyInteraction(TileEntity from, TileEntity to, EnumFacing sideTo, int maxTransfer) {
        if (maxTransfer > 0) {
            EnumFacing opposite = sideTo == null ? null : sideTo.getOpposite();
            IEnergyStorage handlerFrom = from.getCapability(CapabilityEnergy.ENERGY, sideTo);
            IEnergyStorage handlerTo = to.getCapability(CapabilityEnergy.ENERGY, opposite);
            if (handlerFrom != null && handlerTo != null) {
                int drain = handlerFrom.extractEnergy(maxTransfer, true);
                if (drain > 0) {
                    int filled = handlerTo.receiveEnergy(drain, false);
                    handlerFrom.extractEnergy(filled, false);
                }
            }
        }
    }
}
