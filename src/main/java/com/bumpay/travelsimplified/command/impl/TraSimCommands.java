package com.bumpay.travelsimplified.command.impl;

import com.bumpay.travelsimplified.TravelSimplifiedMod;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * TravelSimplified commands registry
 */
public class TraSimCommands {

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
