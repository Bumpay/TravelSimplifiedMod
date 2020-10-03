package com.bumpay.travelsimplified.trasim.port;

import com.bumpay.travelsimplified.trasim.dock.Dock;
import net.minecraft.entity.player.ServerPlayerEntity;

import java.util.ArrayList;
import java.util.UUID;

public class Port {
    private UUID uuidOwner;
    private boolean isOpen = false;
    private String name;
    private int xCoordinate;
    private int zCoordinate;
    private ArrayList<Dock> docks = new ArrayList<Dock>();

    /**
     *
     * @param uuidOwner The uuid of the player that owns the port
     * @param name The name of the port
     * @param xCoordinate The x-coordinate of the port location
     * @param zCoordinate The z-coordinate of the port location
     */
    public Port(UUID uuidOwner, String name, int xCoordinate, int zCoordinate){
        this.uuidOwner = uuidOwner;
        this.name = name;
        this.xCoordinate = xCoordinate;
        this.zCoordinate = zCoordinate;
    }

    /**
     *
     * @param uuidOwner The uuid of the player that owns the port
     * @param name The name of the port
     * @param xCoordinate The x-coordinate of the port location
     * @param zCoordinate The z-coordinate of the port location
     * @param isOpen If the port is opened for other players
     * @param docks List of docks from port
     */
    public Port(UUID uuidOwner, String name, int xCoordinate, int zCoordinate, boolean isOpen, ArrayList<Dock> docks){
        this.uuidOwner = uuidOwner;
        this.name = name;
        this.xCoordinate = xCoordinate;
        this.zCoordinate = zCoordinate;
        this.isOpen = isOpen;
        this.docks = docks;
    }

    /**
     * Checks if any dock of the port is not occupied
     * @return
     */
    public boolean hasPossibleDocks(){
        for(Dock d : docks)
            //TODO check if ship fits inside dock
            if(d.isUsed()) return true;
        return false;
    }

    /**
     * Adds a dock to the docks list
     * @param dock Dock that will be added
     */
    public void addDock(Dock dock) { docks.add(dock); }

    /**
     * Opens or closes a port
     * @param open
     */
    public void setOpen(boolean open) {
        isOpen = open;
    }

    //GETTER
    public UUID getUuidOwner(){
        return uuidOwner;
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
