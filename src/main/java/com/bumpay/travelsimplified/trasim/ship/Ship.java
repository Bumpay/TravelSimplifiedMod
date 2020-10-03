package com.bumpay.travelsimplified.trasim.ship;

import com.bumpay.travelsimplified.trasim.port.Port;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.MutableBoundingBox;

public class Ship {
    String name;
    ServerPlayerEntity owner;
    MutableBoundingBox shipArea;
    Port homePort;

    public Ship(String name, ServerPlayerEntity owner, MutableBoundingBox shipArea, Port homePort){
        this.name = name;
        this.owner = owner;
        this.shipArea = shipArea;
        this.homePort = homePort;
    }

    /**
     * Teleports the ship-schematic back into the homePort
     */
    public void toHomePort(){

    }

    /**
     * Travels to a port
     */
    public void travleTo(Port port){
        if(port.isOpen() && port.hasFreeDocks()){

        }
    }
}
