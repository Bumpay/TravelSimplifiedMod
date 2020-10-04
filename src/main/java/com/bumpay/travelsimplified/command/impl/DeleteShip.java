package com.bumpay.travelsimplified.command.impl;

import com.bumpay.travelsimplified.trasim.ship.ShipWorldSavedData;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.util.text.TranslationTextComponent;

public class DeleteShip {

    private static final SuggestionProvider<CommandSource> SUGGEST_SHIP = (source, builder) -> ISuggestionProvider.suggest( ShipWorldSavedData.getShipNamesByUuid(source.getSource().asPlayer().getUniqueID(), ShipWorldSavedData.get(source.getSource().asPlayer().getServerWorld())), builder);

    public static ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher) {
        return Commands.literal("ship")
                .then(
                        Commands.argument("shipToDelete", StringArgumentType.string()).suggests(SUGGEST_SHIP).executes(source -> deleteShip(source.getSource(), StringArgumentType.getString(source, "shipToDelete"))));
    }

    private static int deleteShip(CommandSource source, String shipName) throws CommandSyntaxException {
        ShipWorldSavedData instance = ShipWorldSavedData.get(source.asPlayer().getServerWorld());
        if(!instance.deleteShip(shipName, instance))
            throw TraSimCommands.NOT_FOUND.create();

        source.sendFeedback(new TranslationTextComponent("commands.delete.ship", shipName), true);
        return 1;
    }
}
