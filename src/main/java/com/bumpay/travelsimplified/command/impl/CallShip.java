package com.bumpay.travelsimplified.command.impl;

import com.bumpay.travelsimplified.trasim.ship.Ship;
import com.bumpay.travelsimplified.trasim.ship.ShipWorldSavedData;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;

public class CallShip {

    private static final SuggestionProvider<CommandSource> SUGGEST_SHIP = (source, builder) -> ISuggestionProvider.suggest(ShipWorldSavedData.getShipNamesByUuid(source.getSource().asPlayer().getUniqueID(), ShipWorldSavedData.get(source.getSource().asPlayer().getServerWorld())).stream(), builder);

    public static ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher) {
        return Commands.literal("call")
                .then(
                Commands.argument("ship", StringArgumentType.string()).suggests(SUGGEST_SHIP).executes(source -> callShip(source.getSource(), StringArgumentType.getString(source, "ship")))
        );
    }

    public static int callShip(CommandSource source, String ship) throws CommandSyntaxException {
        ShipWorldSavedData instance = ShipWorldSavedData.get(source.asPlayer().getServerWorld());
        instance.getShipsOfPlayer(source.asPlayer().getUniqueID(), instance).get(ship.hashCode()).placeShip(source.asPlayer());

        return 1;
    }
}
