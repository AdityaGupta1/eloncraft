package org.sdoaj.eloncraft.commands;

import com.google.common.collect.Lists;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.DimensionType;
import org.sdoaj.eloncraft.dimension.DimensionTeleporter;
import org.sdoaj.eloncraft.dimension.ModDimensions;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;

public class CommandPlanet extends CommandBase {
    @Override
    public String getName() {
        return "planet";
    }

    @Override
    public String getUsage(@Nonnull ICommandSender sender) {
        return "planet <name>";
    }

    @Override
    public List<String> getAliases() {
        return Lists.newArrayList("planet");
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) throws CommandException {
        if (args.length < 1) {
            sender.sendMessage(new TextComponentString(TextFormatting.RED + "Planet name not specified!"));
            return;
        }

        String dimensionName = args[0].toLowerCase();
        DimensionType dimension = ModDimensions.getByName(dimensionName);

        if (dimension == null) {
            sender.sendMessage(new TextComponentString(TextFormatting.RED + "Planet " + dimensionName + " does not exist!"));
            return;
        }

        if (sender instanceof EntityPlayer) {
            DimensionTeleporter.teleportToDimension((EntityPlayer) sender, dimension.getId(), 0, 100, 0);
        }
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
        return ModDimensions.getDimensionNames().stream().filter(name -> name.startsWith(args[0])).collect(Collectors.toList());
    }
}