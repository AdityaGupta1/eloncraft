/*
 * This file ("GuiHandler.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package org.sdoaj.blocks.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.sdoaj.eloncraft.Main;
import org.sdoaj.blocks.machines.crusher.ContainerCrusher;
import org.sdoaj.blocks.machines.crusher.GuiCrusher;
import org.sdoaj.blocks.machines.workbench.ContainerWorkbench;
import org.sdoaj.blocks.machines.workbench.GuiWorkbench;
import org.sdoaj.blocks.tileentities.TileEntityBase;
import org.sdoaj.blocks.machines.alloyfurnace.ContainerAlloyFurnace;
import org.sdoaj.blocks.machines.alloyfurnace.GuiAlloyFurnace;
import org.sdoaj.blocks.machines.metalroller.ContainerMetalRoller;
import org.sdoaj.blocks.machines.metalroller.GuiMetalRoller;

public class GuiHandler implements IGuiHandler {
    public static void init() {
        NetworkRegistry.INSTANCE.registerGuiHandler(Main.INSTANCE, new GuiHandler());
    }

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        TileEntityBase tileEntity = (TileEntityBase) world.getTileEntity(new BlockPos(x, y, z));

        switch (id) {
            case GuiReference.WORKBENCH:
                return new ContainerWorkbench(player.inventory, tileEntity);
            case GuiReference.METAL_ROLLER:
                return new ContainerMetalRoller(player.inventory, tileEntity);
            case GuiReference.CRUSHER:
                return new ContainerCrusher(player.inventory, tileEntity);
            case GuiReference.ALLOY_FURNACE:
                return new ContainerAlloyFurnace(player.inventory, tileEntity);
            default:
                return null;
        }
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        TileEntityBase tileEntity = (TileEntityBase) world.getTileEntity(new BlockPos(x, y, z));

        switch (id) {
            case GuiReference.WORKBENCH:
                return new GuiWorkbench(player.inventory, tileEntity);
            case GuiReference.METAL_ROLLER:
                return new GuiMetalRoller(player.inventory, tileEntity);
            case GuiReference.CRUSHER:
                return new GuiCrusher(player.inventory, tileEntity);
            case GuiReference.ALLOY_FURNACE:
                return new GuiAlloyFurnace(player.inventory, tileEntity);
            default:
                return null;
        }
    }
}