package com.bumpay.travelsimplified.trasim.port;

import com.bumpay.travelsimplified.trasim.dock.Dock;
import net.minecraft.entity.player.ServerPlayerEntity;

import java.util.ArrayList;

public class Port {
    private ServerPlayerEntity owner;
    private boolean isOpen = false;
    private String name;
    private int xCoordinate;
    private int zCoordinate;
    private ArrayList<Dock> docks = new ArrayList<Dock>();

    public Port(ServerPlayerEntity owner, String name, int xCoordinate, int zCoordinate){
        this.owner = owner;
        this.name = name;
        this.xCoordinate = xCoordinate;
        this.zCoordinate = zCoordinate;
    }

    public boolean hasFreeDocks(){
        for(Dock d : docks)
            if(d.isUsed) return true;
        return false;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public ServerPlayerEntity getOwner(){
        return owner;
    }

    public String getName(){
        return name;
    }

    public int getXCoordinate() {
        return xCoordinate;
    }

    public int getZCoordinate() {
        return zCoordinate;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public ArrayList<Dock> getDocks() { return docks; }
}
