package com.bumpay.travelsimplified.network;

import com.bumpay.travelsimplified.TravelSimplifiedMod;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class TraSimPacketHandler {

    private static int ID = 0;
    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(TravelSimplifiedMod.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static int nextID() { return ID++; }

    public static void registerMessages(){
        INSTANCE.registerMessage(nextID(),
                PacketCreatePort.class,
                PacketCreatePort::toBytes,
                PacketCreatePort::new,
                PacketCreatePort::handle);
    }

}
