package com.bumpay.travelsimplified.command.impl;

import com.bumpay.travelsimplified.trasim.port.Port;
import com.bumpay.travelsimplified.trasim.port.PortWorldSavedData;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * Create Port command
 */
public class CreatePort {

    public static ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher) {
        return Commands.literal("port")
                .then(
                        Commands.argument("name", StringArgumentType.string()).executes(source -> createPort(source.getSource(), StringArgumentType.getString(source, "name")))
                                .then(
                                        Commands.argument("pos", BlockPosArgument.blockPos()).executes(source -> createPort(source.getSource(),
                                                StringArgumentType.getString(source, "name"),
                                                BlockPosArgument.getBlockPos(source, "pos")))
                                )

                );

    }

    /**
     * Creates a port with the given name at the players current position
     *
     * @param source
     * @param name   Name of the port
     * @return
     * @throws CommandSyntaxException
     */
    public static int createPort(CommandSource source, String name) throws CommandSyntaxException {
        if(name.isEmpty())
            throw TraSimCommands.NAME_IS_EMPTY.create();
        BlockPos pos = source.asPlayer().getPosition();
        Port port = new Port(source.asPlayer().getUniqueID(), name, pos);
        PortWorldSavedData portWorldSavedData = PortWorldSavedData.get(source.asPlayer().getServerWorld());
        if (!portWorldSavedData.addPortToList(port, portWorldSavedData))
            throw TraSimCommands.NAME_EXISTS.create();

        source.sendFeedback(new TranslationTextComponent("commands.create.port.name", name, pos), true);
        return 1;
    }

    /**
     * @param source
     * @param name   Name of the port
     * @return
     * @throws CommandSyntaxException
     */
    public static int createPort(CommandSource source, String name, BlockPos pos) throws CommandSyntaxException {
        if(name.isEmpty())
            throw TraSimCommands.NAME_IS_EMPTY.create();
        if(!pos.withinDistance(source.asPlayer().getPosition(), 50d))
            throw TraSimCommands.TOO_FAR_AWAY.create();
        Port port = new Port(source.asPlayer().getUniqueID(), name, pos);
        PortWorldSavedData portWorldSavedData = PortWorldSavedData.get(source.asPlayer().getServerWorld());
        if (!portWorldSavedData.addPortToList(port, portWorldSavedData))
            throw TraSimCommands.NAME_EXISTS.create();

        source.sendFeedback(new TranslationTextComponent("commands.create.port.name.pos", name, pos), true);
        return 1;
    }
}
