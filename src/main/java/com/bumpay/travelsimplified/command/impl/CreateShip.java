package com.bumpay.travelsimplified.command.impl;

import com.bumpay.travelsimplified.trasim.port.Port;
import com.bumpay.travelsimplified.trasim.port.PortWorldSavedData;
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
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.gen.feature.template.Template;

/**
 * Create Ship command
 */
public class CreateShip {

    private static final SuggestionProvider<CommandSource> SUGGEST_PORT = (source, builder) -> ISuggestionProvider.suggest(PortWorldSavedData.getPortNameListByUuid(source.getSource().asPlayer().getUniqueID(), PortWorldSavedData.get(source.getSource().asPlayer().getServerWorld())).stream(), builder);

    public static ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher) {
        return Commands.literal("ship")
                .then(
                        Commands.argument("shipName", StringArgumentType.string())
                                .then(
                                        Commands.argument("portName", StringArgumentType.string()).suggests(SUGGEST_PORT)
                                                .then(
                                                        Commands.argument("pos1", BlockPosArgument.blockPos())
                                                                .then(
                                                                        Commands.argument("pos2", BlockPosArgument.blockPos()).executes(source -> {
                                                                            return createShip(source.getSource(),
                                                                                    StringArgumentType.getString(source, "shipName"),
                                                                                    StringArgumentType.getString(source, "portName"),
                                                                                    BlockPosArgument.getBlockPos(source, "pos1"),
                                                                                    BlockPosArgument.getBlockPos(source, "pos2"));
                                                                        })
                                                                )
                                                )
                                )


                );
    }


    public static int createShip(CommandSource source, String shipName, String portName, BlockPos pos1, BlockPos pos2) throws CommandSyntaxException {
        ShipWorldSavedData instanceShip = ShipWorldSavedData.get(source.asPlayer().getServerWorld());
        PortWorldSavedData instancePort = PortWorldSavedData.get(source.asPlayer().getServerWorld());

        if(portName.isEmpty())
            throw TraSimCommands.NAME_IS_EMPTY.create();

        BlockPos size = new BlockPos(Math.abs(pos1.getX() - pos2.getX()), Math.abs(pos1.getY() - pos2.getY()), Math.abs(pos1.getY() - pos2.getY()));
        Template template = new Template();
        template.takeBlocksFromWorld(source.asPlayer().getServerWorld().getWorld(), pos1, size, true, null);
        Port port = instancePort.getPortHashMap(instancePort).get(portName.hashCode());
        Ship ship = new Ship(shipName, source.asPlayer().getUniqueID(), port, template);

        if(!instanceShip.addShipToList(ship, instanceShip))
            throw TraSimCommands.NAME_EXISTS.create();

        source.sendFeedback(new TranslationTextComponent("commands.create.ship.name.port.corner1.corner2", shipName, portName), true);
        return 1;
    }
}
