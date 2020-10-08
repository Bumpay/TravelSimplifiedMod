package com.bumpay.travelsimplified.command.impl;

import com.bumpay.travelsimplified.trasim.port.Port;
import com.bumpay.travelsimplified.trasim.port.PortWorldSavedData;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.util.math.BlockPos;

public class EditDock {

    private static final SuggestionProvider<CommandSource> SUGGEST_PORT = (source, builder) -> ISuggestionProvider.suggest( PortWorldSavedData.getPortNameListByUuid(source.getSource().asPlayer().getUniqueID(), PortWorldSavedData.get(source.getSource().asPlayer().getServerWorld())), builder);

    public static ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher) {
        return Commands.literal("dock")
                .then(
                        Commands.argument("Port Name", StringArgumentType.string()).suggests(SUGGEST_PORT)
                                .then(
                                        Commands.argument("Dock ID", IntegerArgumentType.integer())
                                                .then(
                                                        Commands.argument("Docking Position", BlockPosArgument.blockPos())
                                                                .executes(source -> editDock(source.getSource(),
                                                                        StringArgumentType.getString(source, "Port Name"),
                                                                        IntegerArgumentType.getInteger(source, "Dock ID"),
                                                                        BlockPosArgument.getBlockPos(source, "Docking Position"))))
                                                )

                                );
    }

    private static int editDock(CommandSource source, String portName, int dockId, BlockPos posIn) throws CommandSyntaxException {
        PortWorldSavedData instance = PortWorldSavedData.get(source.asPlayer().getServerWorld());
        Port port = PortWorldSavedData.getPortsOfPlayer(source.asPlayer().getUniqueID(), instance).get(portName.hashCode());

        port.getDocks().get(dockId).setDockingPos(posIn);

        return 1;
    }
}
