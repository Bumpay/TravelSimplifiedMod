package com.bumpay.travelsimplified.command.impl;

import com.bumpay.travelsimplified.trasim.dock.Dock;
import com.bumpay.travelsimplified.trasim.port.PortWorldSavedData;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.Vec3Argument;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * Create Dock command
 */
public class CreateDock {
    //ExceptionMessages
    private static final SimpleCommandExceptionType PORT_NOT_EXISTS = new SimpleCommandExceptionType(new TranslationTextComponent("commands.create.dock.port_not_exists"));
    private static final SimpleCommandExceptionType PORT_TOO_FAR_AWAY = new SimpleCommandExceptionType(new TranslationTextComponent("commands.create.dock.port_too_far_away"));

    //Suggestions
    //TODO add " around the ports name
    private static final SuggestionProvider<CommandSource> SUGGEST_PORT = (source, builder) -> ISuggestionProvider.suggest(PortWorldSavedData.getPortNameList(PortWorldSavedData.get(source.getSource().asPlayer().getServerWorld())).stream(), builder);

    public static ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher) {
        return Commands.literal("dock")
                .then(
                        Commands.argument("portName", StringArgumentType.string()).suggests(SUGGEST_PORT)
                                .then(
                                        Commands.argument("pos1", Vec3Argument.vec3())
                                                .then(
                                                        Commands.argument("pos2", Vec3Argument.vec3())
                                                                .executes(source -> createDock(source.getSource(),
                                                                        StringArgumentType.getString(source, "portName"),
                                                                        Vec3Argument.getVec3(source, "pos1"),
                                                                        Vec3Argument.getVec3(source, "pos2"))
                                                                )
                                                )
                                )
                );
    }

    /**
     * Creates and adds a dock to a given port
     * @param source
     * @param portName Name of the port the dock should be added to
     * @param pos1 One corner of the dock
     * @param pos2 Other corner of the dock
     * @return
     * @throws CommandSyntaxException
     */
    private static int createDock(CommandSource source, String portName, Vec3d pos1, Vec3d pos2) throws CommandSyntaxException {
        PortWorldSavedData instance = PortWorldSavedData.get(source.asPlayer().getServerWorld());
        if (!PortWorldSavedData.getPortNameList(instance).contains(portName))
            throw PORT_NOT_EXISTS.create();

        if(!instance.addDockToPort(new Dock(false, false, new Vec3i((int)pos1.x, (int)pos1.y, (int)pos1.z), new Vec3i((int)pos2.x, (int)pos2.y, (int)pos2.z)), portName, instance))
            throw PORT_TOO_FAR_AWAY.create();

        source.sendFeedback(new TranslationTextComponent("commands.create.dock.port.corner1.corner2", portName), true);
        return 1;
    }
}


