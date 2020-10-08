package com.bumpay.travelsimplified.command.impl;

import com.bumpay.travelsimplified.TravelSimplifiedMod;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * TravelSimplified commands registry
 */
public class TraSimCommands {

    public static final SimpleCommandExceptionType NAME_IS_EMPTY = new SimpleCommandExceptionType(new TranslationTextComponent("commands.create.name_is_empty"));
    public static final SimpleCommandExceptionType NAME_EXISTS = new SimpleCommandExceptionType(new TranslationTextComponent("commands.create.name_exists"));
    public static final SimpleCommandExceptionType TOO_FAR_AWAY = new SimpleCommandExceptionType(new TranslationTextComponent("commands.create.too_far_away"));
    public static final SimpleCommandExceptionType PORT_NOT_EXISTS = new SimpleCommandExceptionType(new TranslationTextComponent("commands.create.port_not_exists"));
    public static final SimpleCommandExceptionType NOT_FOUND = new SimpleCommandExceptionType(new TranslationTextComponent("commands.not_found"));

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(
                // trasim
                Commands.literal(TravelSimplifiedMod.MOD_ID)
                        .then(
                                // trasim create
                                Commands.literal("create").executes(source -> create(source.getSource()))
                                        .then(
                                                CreatePort.register(dispatcher)
                                        )
                                        .then(
                                                CreateDock.register(dispatcher)
                                        )
                                        .then(
                                                CreateShip.register(dispatcher)
                                        )
                        )
                        .then(
                                Commands.literal("get").executes(source -> create(source.getSource()))
                                        .then(
                                                GetPort.register(dispatcher)
                                        )
                                        .then(
                                                GetShip.register(dispatcher)
                                        )
                        )
                        .then(
                                Commands.literal("delete").executes(source -> create(source.getSource()))
                                        .then(
                                                DeletePort.register(dispatcher)
                                        )
                                        .then(
                                                DeleteShip.register(dispatcher)
                                        )
                        )
                        .then(
                                CallShip.register(dispatcher)
                        )
                .then(
                        Commands.literal("edit").executes(source -> create(source.getSource()))
                        .then(
                                EditDock.register(dispatcher)
                        )
                        .then(
                                EditPort.register(dispatcher)
                        )
                )
                //TODO create port literal
                //TODO add close command to port
                //TODO add list command to port
        );

    }

    public static int create(CommandSource source) {
        source.sendFeedback(new TranslationTextComponent("commands.create"), true);
        return 1;
    }
}
