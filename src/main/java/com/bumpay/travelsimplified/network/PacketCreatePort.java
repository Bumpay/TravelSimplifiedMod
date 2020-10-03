package com.bumpay.travelsimplified.network;

import com.bumpay.travelsimplified.trasim.port.Port;
import com.bumpay.travelsimplified.trasim.port.PortWorldSavedData;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class PacketCreatePort {

    private final long uuidL;
    private final long uuidM;
    private final String name;
    private final int x;
    private final int z;

    public PacketCreatePort(PacketBuffer buf) {
        uuidL = buf.readLong();
        uuidM = buf.readLong();
        name = buf.readString();
        x = buf.readInt();
        z = buf.readInt();
    }

    public PacketCreatePort(UUID uuid, String name, int x, int z){
        this.uuidL = uuid.getLeastSignificantBits();
        this.uuidM = uuid.getMostSignificantBits();
        this.name = name;
        this.x = x;
        this.z = z;
    }

    public void toBytes(PacketBuffer buf){
        buf.writeLong(uuidL);
        buf.writeLong(uuidM);
        buf.writeString(name);
        buf.writeInt(x);
        buf.writeInt(z);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Port port = new Port(ctx.get().getSender(), name, x, z);
            PortWorldSavedData portWorldSavedData = PortWorldSavedData.get(ctx.get().getSender().getServerWorld());
            portWorldSavedData.addPortToList(port, portWorldSavedData);
        });
        ctx.get().setPacketHandled(true);
    }
}
