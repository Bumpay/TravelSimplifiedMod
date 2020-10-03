package com.bumpay.travelsimplified.event;

import com.bumpay.travelsimplified.TravelSimplifiedMod;
import com.bumpay.travelsimplified.command.impl.CreatePort;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

@Mod.EventBusSubscriber(modid = TravelSimplifiedMod.MOD_ID)
public class Events {

    @SubscribeEvent
    public static void onServerStarting(final FMLServerStartingEvent event) {
        CreatePort.register(event.getCommandDispatcher());
    }
}
