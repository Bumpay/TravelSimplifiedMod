package com.bumpay.travelsimplified.command.impl;

import com.bumpay.travelsimplified.trasim.port.Port;
import com.bumpay.travelsimplified.trasim.port.PortWorldSavedData;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * Create Port command
 */
public class CreatePort {
    //ExceptionMessages
    private static final SimpleCommandExceptionType PORT_NAME_EXISTS = new SimpleCommandExceptionType(new TranslationTextComponent("commands.create.port.port_name_exists"));
    private static final SimpleCommandExceptionType TOO_FAR_AWAY = new SimpleCommandExceptionType(new TranslationTextComponent("commands.create.port.too_far_away"));

    public static ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher) {
        return Commands.literal("port")
                .then(
                        // trasim create port name
                        Commands.argument("name", StringArgumentType.string()).executes(source -> {
                            return createPort(source.getSource(), StringArgumentType.getString(source, "name"));
                        })
                                .then(
                                        // trasim create port name x
                                        Commands.argument("x", IntegerArgumentType.integer())
                                                .then(
                                                        // trasim create port x z
                                                        Commands.argument("z", IntegerArgumentType.integer()).executes(source -> {
                                                            return createPort(source.getSource(),
                                                                    StringArgumentType.getString(source, "name"),
                                                                    IntegerArgumentType.getInteger(source, "x"),
                                                                    IntegerArgumentType.getInteger(source, "z"));
                                                        })
                                                )
                                )
                );

    }

    /**
     * Creates a port with the given name at the players current position
     * @param source
     * @param name Name of the port
     * @return
     * @throws CommandSyntaxException
     */
    public static int createPort(CommandSource source, String name) throws CommandSyntaxException {
        int playerPosX = (int) source.asPlayer().getPosX();
        int playerPosZ = (int) source.asPlayer().getPosZ();
        Port port = new Port(source.asPlayer().getUniqueID(), name, playerPosX, playerPosZ);
        PortWorldSavedData portWorldSavedData = PortWorldSavedData.get(source.asPlayer().getServerWorld());
        if (!portWorldSavedData.addPortsToList(port, portWorldSavedData))
            throw PORT_NAME_EXISTS.create();

        source.sendFeedback(new TranslationTextComponent("commands.create.port.name", name, playerPosX, playerPosZ), true);
        return 1;
    }

    /**
     *
     * @param source
     * @param name Name of the port
     * @param x X-coordinate of port location
     * @param z Z-coordinate of port location
     * @return
     * @throws CommandSyntaxException
     */
    public static int createPort(CommandSource source, String name, int x, int z) throws CommandSyntaxException {
        Port port = new Port(source.asPlayer().getUniqueID(), name, x, z);
        PortWorldSavedData portWorldSavedData = PortWorldSavedData.get(source.asPlayer().getServerWorld());
        if (!portWorldSavedData.addPortsToList(port, portWorldSavedData))
            throw PORT_NAME_EXISTS.create();

        source.sendFeedback(new TranslationTextComponent("commands.create.port.name.pos", name, x, z), true);
        return 1;
    }
}
