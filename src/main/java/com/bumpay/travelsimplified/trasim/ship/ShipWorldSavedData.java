package com.bumpay.travelsimplified.trasim.ship;

import com.bumpay.travelsimplified.trasim.port.PortWorldSavedData;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;

public class ShipWorldSavedData extends WorldSavedData {

    private static final String NAME = "TraSim_ShipData";

    public ShipWorldSavedData() {
        super(NAME);
    }

    public ShipWorldSavedData(String name) {
        super(name);
    }

    @Override
    public void read(CompoundNBT nbt) {

    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        return null;
    }

    public static ShipWorldSavedData get(ServerWorld world) {
        DimensionSavedDataManager data = world.getSavedData();
        ShipWorldSavedData instance = (ShipWorldSavedData) data.getOrCreate(() -> {return null;}, NAME);

        if(instance == null) {
            instance = new ShipWorldSavedData();
        }

        return instance;
    }
}
