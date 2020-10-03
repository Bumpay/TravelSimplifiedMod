package com.bumpay.travelsimplified.trasim.port;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.storage.WorldSavedData;

public class PortData extends WorldSavedData {
    public PortData(String name) {
        super(name);
    }

    @Override
    public void read(CompoundNBT nbt) {

    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        return null;
    }
}
