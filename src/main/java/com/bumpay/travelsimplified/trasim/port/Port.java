package com.bumpay.travelsimplified.trasim.port;

import com.bumpay.travelsimplified.trasim.dock.Dock;
import com.google.gson.*;
import net.minecraft.entity.player.ServerPlayerEntity;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class Port {
    private ServerPlayerEntity owner;
    private boolean isOpen = false;
    private String name;
    private int xCoordinate;
    private int zCoordinate;
    private List<Dock> docks;

    public Port(ServerPlayerEntity owner, String name, int xCoordinate, int zCoordinate){
        this.owner = owner;
        this.name = name;
        this.xCoordinate = xCoordinate;
        this.zCoordinate = zCoordinate;
    }

    public JsonObject toJsonObject() {
        JsonObject jsonObject = new JsonObject();
        return jsonObject;
    }

    public void addToPortList() {

        Gson gson = new GsonBuilder().create();
        try {
            Writer writer = new FileWriter("../ports.json");
            gson.toJson(this, writer);
        }catch(IOException e){
            e.printStackTrace();
        }
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

    public List<Dock> getDocks() { return docks; }
}
