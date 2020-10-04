package com.bumpay.travelsimplified.command.impl;

import com.bumpay.travelsimplified.trasim.dock.Dock;
import com.bumpay.travelsimplified.trasim.port.Port;
import com.bumpay.travelsimplified.trasim.port.PortWorldSavedData;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * Create Dock command
 */
public class CreateDock {
    //ExceptionMessages


    //Suggestions
    //TODO add " around the ports name
    private static final SuggestionProvider<CommandSource> SUGGEST_PORT = (source, builder) -> ISuggestionProvider.suggest(PortWorldSavedData.getPortNameListByUuid(source.getSource().asPlayer().getUniqueID(), PortWorldSavedData.get(source.getSource().asPlayer().getServerWorld())).stream(), builder);

    public static ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher) {
        return Commands.literal("dock")
                .then(
                        Commands.argument("portName", StringArgumentType.string()).suggests(SUGGEST_PORT)
                                .then(
                                        Commands.argument("pos1", BlockPosArgument.blockPos())
                                                .then(
                                                        Commands.argument("pos2", BlockPosArgument.blockPos())
                                                                .executes(source -> createDock(source.getSource(),
                                                                        StringArgumentType.getString(source, "portName"),
                                                                        BlockPosArgument.getBlockPos(source, "pos1"),
                                                                        BlockPosArgument.getBlockPos(source, "pos2"))
                                                                )
                                                )
                                )
                );
    }

    /**
     * Creates and adds a dock to a given port
     * @param source
     * @param portName Name of the port the dock should be added to
     * @param pos1 One corner of the dock
     * @param pos2 Other corner of the dock
     * @return
     * @throws CommandSyntaxException
     */
    private static int createDock(CommandSource source, String portName, BlockPos pos1, BlockPos pos2) throws CommandSyntaxException {
        PortWorldSavedData instance = PortWorldSavedData.get(source.asPlayer().getServerWorld());
        Port port = instance.getPortsOfPlayer(source.asPlayer().getUniqueID(), instance).get(portName.hashCode());
        if(!source.asPlayer().getPosition().withinDistance(port.getPos(), 50d))
            throw TraSimCommands.TOO_FAR_AWAY.create();
        if (!PortWorldSavedData.getPortNameListByUuid(source.asPlayer().getUniqueID(), instance).contains(portName))
            throw TraSimCommands.PORT_NOT_EXISTS.create();

        instance.addDockToPort(new Dock(false, false, pos1, pos2), portName, instance);


        source.sendFeedback(new TranslationTextComponent("commands.create.dock.port.corner1.corner2", portName), true);
        return 1;
    }
}


