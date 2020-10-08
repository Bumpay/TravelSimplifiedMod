package com.bumpay.travelsimplified.trasim.ship;

import com.bumpay.travelsimplified.trasim.dock.Dock;
import com.bumpay.travelsimplified.trasim.port.Port;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.Rotations;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import org.lwjgl.system.CallbackI;

import java.util.UUID;

public class Ship {
    private String name;
    private UUID uuidOwner;
    private Template shipTemplate;
    private Port homePort;
    private Direction direction;

    /**
     * Ship of a player
     * @param name Name of the ship
     * @param uuidOwner uuid of the player that owns the ship
     */
    public Ship(String name, UUID uuidOwner, Port homePort, Template shipTemplate, Direction direction){

        this.name = name;
        this.uuidOwner = uuidOwner;
        this.homePort = homePort;
        this.shipTemplate = shipTemplate;
        this.direction = direction;
    }

    public void test(){

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



    public void placeShip(ServerPlayerEntity player) {
        placeShip(player, homePort.getDocks().get(0));
    }

    public void placeShip(ServerPlayerEntity player, Dock dock){
        //TODO rotating around center of the template

        PlacementSettings placementSettings = new PlacementSettings();
        placementSettings.setRotation(rotation(direction, dock.getDirection()));

        Dock landingDock = homePort.getDocks().get(0);

        shipTemplate.addBlocksToWorld(player.getServerWorld(), landingDock.getLandingPos().add(offset(landingDock)), placementSettings);
    }

    public BlockPos offset(Dock dock){
        BlockPos offset;
        BlockPos shipSize = shipTemplate.getSize();
        int width = 0, length = 0;

        switch (direction.getAxis()) {

            case X:
                width = shipSize.getZ();
                length = shipSize.getX();
                break;

            case Z:
                length = shipSize.getZ();
                width = shipSize.getX();
                break;
        }


        dock.getLandingPos();

        Direction dir = dock.getDockingSide();

        switch (dir) {

            case NORTH:
                offset = new BlockPos(0 ,0 ,length/2);
                break;

            case EAST:
                offset = new BlockPos(width/2 ,0 ,length);
                break;

            case SOUTH:
                offset = new BlockPos(width ,0 ,length/2);
                break;

            case WEST:
                offset = new BlockPos(width/2 ,0 ,0);
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + dir);
        }



        return offset;
    }

    public Rotation rotation(Direction shipDir, Direction dockDir){
        Rotation rot = Rotation.NONE;

        int i = dockDir.getHorizontalIndex() - shipDir.getHorizontalIndex();
        if(i < 0 )
            i += 4;

        switch(i) {
            case 0:
                rot = rot.add(Rotation.NONE);
                break;

            case 1:
                rot = rot.add(Rotation.CLOCKWISE_90);
                break;

            case 2:
                rot = rot.add(Rotation.CLOCKWISE_180);
                break;

            case 3:
                rot = rot.add(Rotation.COUNTERCLOCKWISE_90);
                break;
        }

        return rot;
    }


    //GETTER
    public String getName() {
        return name;
    }

    public Direction getDirection() {
        return direction;
    }

    public Port getHomePort(){ return homePort; }

    public UUID getUuidOwner() {
        return uuidOwner;
    }

    public Template getShipTemplate() {
        return shipTemplate;
    }
}
