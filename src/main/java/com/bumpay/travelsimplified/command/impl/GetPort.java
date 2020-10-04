package com.bumpay.travelsimplified.command.impl;

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
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.HashMap;

public class GetPort {

    private static final SuggestionProvider<CommandSource> SUGGEST_PORT = (source, builder) -> ISuggestionProvider.suggest(PortWorldSavedData.getPortNameListByUuid(source.getSource().asPlayer().getUniqueID(), PortWorldSavedData.get(source.getSource().asPlayer().getServerWorld())).stream(), builder);

    public static ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher) {
        return Commands.literal("port").executes(source -> getPorts(source.getSource()))
                .then( Commands.argument("portName", StringArgumentType.string()).suggests(SUGGEST_PORT).executes(source -> getPort(source.getSource(), StringArgumentType.getString(source, "portName"))));
    }

    public static int getPorts(CommandSource source) throws CommandSyntaxException {
        PortWorldSavedData instance = PortWorldSavedData.get(source.asPlayer().getServerWorld());
        ArrayList<String> ports = instance.getPortNameListByUuid(source.asPlayer().getUniqueID(), instance) ;

        source.sendFeedback(new TranslationTextComponent("commands.get.ports", ports), true);
        return 1;
    }

    public static int getPort(CommandSource source, String portName) throws CommandSyntaxException {
        PortWorldSavedData instance = PortWorldSavedData.get(source.asPlayer().getServerWorld());
        HashMap<Integer, Port> ports = instance.getPortsOfPlayer(source.asPlayer().getUniqueID(), instance);

        if(!ports.containsValue(portName.hashCode()))
            throw TraSimCommands.NOT_FOUND.create();

        Port port = ports.get(portName.hashCode());

        source.sendFeedback(new TranslationTextComponent("commands.get.port.port", port.getName(), port.getPos() ,port.getDocks().size()), true);
        return 1;
    }

}
