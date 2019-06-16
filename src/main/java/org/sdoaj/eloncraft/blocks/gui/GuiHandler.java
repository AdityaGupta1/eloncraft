/*
 * This file ("GuiHandler.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package org.sdoaj.eloncraft.blocks.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.sdoaj.eloncraft.Eloncraft;
import org.sdoaj.eloncraft.blocks.launch.controller.ContainerLaunchController;
import org.sdoaj.eloncraft.blocks.launch.controller.GuiLaunchController;
import org.sdoaj.eloncraft.blocks.machines.loxcollector.ContainerLOXCollector;
import org.sdoaj.eloncraft.blocks.machines.loxcollector.GuiLOXCollector;
import org.sdoaj.eloncraft.blocks.machines.refinery.ContainerRefinery;
import org.sdoaj.eloncraft.blocks.machines.refinery.GuiRefinery;
import org.sdoaj.eloncraft.blocks.machines.crusher.ContainerCrusher;
import org.sdoaj.eloncraft.blocks.machines.crusher.GuiCrusher;
import org.sdoaj.eloncraft.blocks.machines.workbench.ContainerWorkbench;
import org.sdoaj.eloncraft.blocks.machines.workbench.GuiWorkbench;
import org.sdoaj.eloncraft.blocks.tileentities.TileEntityBase;
import org.sdoaj.eloncraft.blocks.machines.alloyfurnace.ContainerAlloyFurnace;
import org.sdoaj.eloncraft.blocks.machines.alloyfurnace.GuiAlloyFurnace;
import org.sdoaj.eloncraft.blocks.machines.metalroller.ContainerMetalRoller;
import org.sdoaj.eloncraft.blocks.machines.metalroller.GuiMetalRoller;

public class GuiHandler implements IGuiHandler {
    public static void init() {
        NetworkRegistry.INSTANCE.registerGuiHandler(Eloncraft.INSTANCE, new GuiHandler());
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
            case GuiReference.REFINERY:
                return new ContainerRefinery(player.inventory, tileEntity);
            case GuiReference.LOX_COLLECTOR:
                return new ContainerLOXCollector(player.inventory, tileEntity);
            case GuiReference.LAUNCH_CONTROLLER:
                return new ContainerLaunchController(player.inventory, tileEntity);
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
            case GuiReference.REFINERY:
                return new GuiRefinery(player.inventory, tileEntity);
            case GuiReference.LOX_COLLECTOR:
                return new GuiLOXCollector(player.inventory, tileEntity);
            case GuiReference.LAUNCH_CONTROLLER:
                return new GuiLaunchController(player.inventory, tileEntity);
            default:
                return null;
        }
    }
}