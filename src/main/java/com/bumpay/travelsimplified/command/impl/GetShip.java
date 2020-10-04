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
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;

public class GetShip {

    private static final SuggestionProvider<CommandSource> SUGGEST_SHIP = (source, builder) -> ISuggestionProvider.suggest(ShipWorldSavedData.getShipNamesByUuid(source.getSource().asPlayer().getUniqueID(), ShipWorldSavedData.get(source.getSource().asPlayer().getServerWorld())).stream(), builder);

    public static ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher) {
        return Commands.literal("ship").executes(source -> {
            return getShips(source.getSource());
        }).then(
                Commands.argument("ship", StringArgumentType.string()).suggests(SUGGEST_SHIP).executes(source -> getShip(source.getSource(), StringArgumentType.getString(source, "ship")))
        );
    }

    public static int getShips(CommandSource source) throws CommandSyntaxException {
        ShipWorldSavedData instance = ShipWorldSavedData.get(source.asPlayer().getServerWorld());
        ArrayList<String> ships = instance.getShipNamesByUuid(source.asPlayer().getUniqueID(), instance);

        source.sendFeedback(new TranslationTextComponent("commands.get.ships", ships), true);
        return 1;
    }

    public static int getShip(CommandSource source, String name) throws CommandSyntaxException {
        ShipWorldSavedData instance = ShipWorldSavedData.get(source.asPlayer().getServerWorld());
        Ship ship = instance.getShipsOfPlayer(source.asPlayer().getUniqueID(), instance).get(name.hashCode());
        source.sendFeedback(new TranslationTextComponent("commands.get.ships.ship", ship.getName(), ship.getHomePort().getName()), true);
        return 1;
    }
}
