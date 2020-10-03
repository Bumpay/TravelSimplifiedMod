package com.bumpay.travelsimplified.trasim.ship;

import com.bumpay.travelsimplified.trasim.port.Port;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.Vec3i;

import java.util.UUID;

public class Ship {
    String name;
    UUID uuidOwner;
    //MutableBoundingBox shipArea;
    Vec3i pos1, pos2;
    Port homePort;

    /**
     * Ship of a player
     * @param name Name of the ship
     * @param uuidOwner uuid of the player that owns the ship
     * @param pos1 One corner of the ship area
     * @param pos2 Other corner of the ship area
     * @param homePort Homeport of the ship
     */
    public Ship(String name, UUID uuidOwner, Vec3i pos1, Vec3i pos2, Port homePort){
        this.name = name;
        this.uuidOwner = uuidOwner;
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.homePort = homePort;
    }

    /**
     * Calls the ship-schematic back into the homePort
     */
    public void toHomePort(){

    }

    /**
     * Travels to a port
     * @param port The destination port
     */
    public void travelTo(Port port){
        if(port.isOpen() && port.hasPossibleDocks()){

        }
    }
}
