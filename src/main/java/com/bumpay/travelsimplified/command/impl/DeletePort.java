package com.bumpay.travelsimplified.command.impl;

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

public class DeletePort {

    private static final SuggestionProvider<CommandSource> SUGGEST_PORT = (source, builder) -> ISuggestionProvider.suggest(PortWorldSavedData.getPortNameListByUuid(source.getSource().asPlayer().getUniqueID(), PortWorldSavedData.get(source.getSource().asPlayer().getServerWorld())).stream(), builder);

    public static ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher) {
        return Commands.literal("port")
                .then(
                        Commands.argument("portToDelete", StringArgumentType.string()).suggests(SUGGEST_PORT).executes(source -> deletePort(source.getSource(), StringArgumentType.getString(source, "portToDelete"))));
    }

    private static int deletePort(CommandSource source, String portName) throws CommandSyntaxException {
        PortWorldSavedData instance = PortWorldSavedData.get(source.asPlayer().getServerWorld());
        if(!instance.deletePort(portName, instance))
            throw TraSimCommands.NOT_FOUND.create();

        source.sendFeedback(new TranslationTextComponent("commands.delete.port", portName), true);
        return 1;
    }
}
