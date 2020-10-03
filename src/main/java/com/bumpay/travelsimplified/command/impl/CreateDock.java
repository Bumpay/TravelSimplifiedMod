package com.bumpay.travelsimplified.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.TranslationTextComponent;

public class CreateDock {

    public static void register(CommandDispatcher<CommandSource> dispatcher){
        dispatcher.register(
                Commands.literal("createDock")
                        .executes(source -> {
                            return createDock(source.getSource());
                        })
                    .then(
                            Commands.argument("x1", IntegerArgumentType.integer())
                                .then(
                                        Commands.argument("y1", IntegerArgumentType.integer())
                                        )).then(
                                                Commands.argument("z1", IntegerArgumentType.integer())
                                                .executes((source -> {
                                                    return createDock(source.getSource(), IntegerArgumentType.getInteger(source, "x1"), IntegerArgumentType.getInteger(source, "y1"), IntegerArgumentType.getInteger(source, "z1"));
                                                }))

                                        )
                                );



    }

    private static int createDock(CommandSource source){
        source.sendFeedback(new TranslationTextComponent("commands.createDock"), true);
        return 1;
    }

    private static int createDock(CommandSource source, int x1, int y1, int z1){
        source.sendFeedback(new TranslationTextComponent("commands.createDock.coords1"), true);
        return 1;
    }

    private static int createDock(CommandSource source, int x1, int y1, int z1, int x2, int y2, int z2){
        source.sendFeedback(new TranslationTextComponent("commands.createDock.coords1.coords2"), true);
        return 1;
    }
}


