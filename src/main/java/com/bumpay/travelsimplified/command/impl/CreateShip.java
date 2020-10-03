package com.bumpay.travelsimplified.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.Vec3Argument;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * Create Ship command
 */
public class CreateShip {
    //ExceptionMessages

    public static ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher) {
        return Commands.literal("ship")
                .then(
                        Commands.argument("shipName", StringArgumentType.string())
                                .then(
                                        Commands.argument("portName", StringArgumentType.string())
                                                .then(
                                                        Commands.argument("pos1", Vec3Argument.vec3())
                                                                .then(
                                                                        Commands.argument("pos2", Vec3Argument.vec3()).executes(source -> {
                                                                            return createShip(source.getSource(),
                                                                                    StringArgumentType.getString(source, "shipName"),
                                                                                    StringArgumentType.getString(source, "portName"),
                                                                                    Vec3Argument.getVec3(source, "pos1"),
                                                                                    Vec3Argument.getVec3(source, "pos2"));
                                                                        })
                                                                )
                                                )
                                )


                );
    }


    public static int createShip(CommandSource source, String shipName, String portName, Vec3d pos1, Vec3d pos2) {
        source.sendFeedback(new TranslationTextComponent("commands.create.ship.name.port.corner1.corner2"), true);
        return 1;
    }
}
