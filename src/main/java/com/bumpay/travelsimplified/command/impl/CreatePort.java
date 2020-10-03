package com.bumpay.travelsimplified.command.impl;

import com.bumpay.travelsimplified.trasim.port.Port;
import com.bumpay.travelsimplified.trasim.port.PortWorldSavedData;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.TranslationTextComponent;

public class CreatePort {

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(
            //createPort
            Commands.literal("createPort")
                .executes(source -> {
                    System.out.println("Called createPort with no arguments!");
                    return createPort(source.getSource());
                })
                .then(
            //name
            Commands.argument("name", StringArgumentType.string())
                .executes(source -> {
                    System.out.println("Port name is " + StringArgumentType.getString(source, "name"));
                    return createPort(source.getSource(), StringArgumentType.getString(source, "name"));
                })
                .then(
            //xCoordinate
            Commands.argument("xCoordinate", IntegerArgumentType.integer())
                .then(
            //zCoordinate
            Commands.argument("zCoordinate", IntegerArgumentType.integer())
                .executes(source -> {
                    System.out.println("Port name is " + StringArgumentType.getString(source, "name") + " with x-coordinate " + IntegerArgumentType.getInteger(source, "xCoordinate") + " and z-coordinate " + IntegerArgumentType.getInteger(source, "zCoordinate"));
                    return createPort(source.getSource(), StringArgumentType.getString(source, "name"), IntegerArgumentType.getInteger(source, "xCoordinate"), IntegerArgumentType.getInteger(source, "zCoordinate"));
                })
                )
                )
                )
        );

    }

    /**
     * Tells the user how to use the /createPort command
     * @param source
     * @return Success-value
     */
    private static int createPort(CommandSource source){
        source.sendFeedback(new TranslationTextComponent("commands.createPort"), true);
        return 1;
    }

    /**
     * Creates a port with a given name
     * @param source
     * @param name Name of the port
     * @return Success-value
     * @throws CommandSyntaxException
     */
    private static int createPort(CommandSource source, String name) throws CommandSyntaxException {
        int playerPosX = (int)source.asPlayer().getPosX();
        int playerPosZ = (int)source.asPlayer().getPosZ();
        Port port = new Port(source.asPlayer(), name, playerPosX, playerPosZ);
        PortWorldSavedData portWorldSavedData = PortWorldSavedData.get(source.asPlayer().getServerWorld());
        portWorldSavedData.addPortToList(port, portWorldSavedData);
        source.sendFeedback(new TranslationTextComponent("commands.createPort.name", name, playerPosX, playerPosZ), true);
        return 1;
    }

    /**
     * Create a port with a given name at the provided x- and z-coordinates
     * @param source
     * @param name Name of the port
     * @param xCoordinate X-coordinate of the port
     * @param zCoordinate Z-coordinate of the port
     * @return
     * @throws CommandSyntaxException
     */
    private static int createPort(CommandSource source, String name, int xCoordinate, int zCoordinate) throws CommandSyntaxException {
        Port port = new Port(source.asPlayer(), name, xCoordinate, zCoordinate);
        PortWorldSavedData portWorldSavedData = PortWorldSavedData.get(source.asPlayer().getServerWorld());
        portWorldSavedData.addPortToList(port, portWorldSavedData);
        source.sendFeedback(new TranslationTextComponent("commands.createPort.name.x.y", name , xCoordinate, zCoordinate), true);
        return 1;
    }
}
