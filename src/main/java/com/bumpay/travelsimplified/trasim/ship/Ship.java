package com.bumpay.travelsimplified.trasim.ship;

import com.bumpay.travelsimplified.trasim.port.Port;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.UUID;

public class Ship {
    private String name;
    private UUID uuidOwner;
    private Template shipTemplate;
    private Port homePort;

    /**
     * Ship of a player
     * @param name Name of the ship
     * @param uuidOwner uuid of the player that owns the ship
     */
    public Ship(String name, UUID uuidOwner, Port homePort, Template shipTemplate){
        this.name = name;
        this.uuidOwner = uuidOwner;
        this.homePort = homePort;
        this.shipTemplate = shipTemplate;
    }

    public void test(){
        //shipTemplate.takeBlocksFromWorld();
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

    public void placeShip(ServerPlayerEntity player){
        shipTemplate.addBlocksToWorld(player.getServerWorld(), homePort.getDocks().get(0).getPos1(), new PlacementSettings());
    }

    public String getName() {
        return name;
    }

    public Port getHomePort(){ return homePort; }

    public UUID getUuidOwner() {
        return uuidOwner;
    }

    public Template getShipTemplate() {
        return shipTemplate;
    }
}
