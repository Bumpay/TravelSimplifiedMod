package com.bumpay.travelsimplified.trasim.dock;

import net.minecraft.client.renderer.FaceDirection;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Rotations;

//TODO add isFacing and docking site attribute

public class Dock {
    private int id;
    private int depth;
    private boolean isHomeDock;
    private boolean isUsed;
    private BlockPos pos1;
    private BlockPos pos2;
    private BlockPos landingPos;
    private Direction direction;
    private Direction dockingSide;

    /**
     * Docks are defined areas where a ship structure can land
     * @param isHomeDock If the dock is the homedock(reserved dock for own ship) of the port
     * @param isUsed If the dock is already occupied
     * @param pos1 One corner of the dock area
     * @param pos2 Other corner of the dock area
     */
    public Dock(boolean isHomeDock, boolean isUsed, BlockPos pos1, BlockPos pos2){
        this.isHomeDock = isHomeDock;
        this.isUsed = isUsed;
        this.pos1 = pos1;
        this.pos2 = pos2;
    }

    public Dock(int id, boolean isHomeDock, boolean isUsed, BlockPos pos1, BlockPos pos2){
        this.id = id;
        this.isHomeDock = isHomeDock;
        this.isUsed = isUsed;
        this.pos1 = pos1;
        this.pos2 = pos2;
    }

    public BlockPos setDockingPos(BlockPos posIn){
        BlockPos dockingPos = posIn;

        return dockingPos;
    }

    //GETTER
    public boolean isHomeDock() {
        return isHomeDock;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public BlockPos getPos1() {
        return pos1;
    }

    public BlockPos getPos2() {
        return pos2;
    }

    public BlockPos getLandingPos() {
        return landingPos;
    }

    public int getId() {
        return id;
    }

    public Direction getDirection() {
        return direction;
    }

    public Direction getDockingSide() {
        return dockingSide;
    }

    //SETTER
    public void setHomeDock(boolean homeDock) {
        isHomeDock = homeDock;
    }

    public void setPos1(BlockPos pos1) {
        this.pos1 = pos1;
    }

    public void setPos2(BlockPos pos2) {
        this.pos2 = pos2;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDockingSide(Direction dockingSide) {
        this.dockingSide = dockingSide;
    }
}
