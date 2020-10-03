package com.bumpay.travelsimplified.trasim.dock;

import net.minecraft.util.math.Vec3i;

public class Dock {
    private boolean isHomeDock;
    private boolean isUsed;
    private Vec3i pos1;
    private Vec3i pos2;

    /**
     * Docks are defined areas where a ship structure can land
     * @param isHomeDock If the dock is the homedock(reserved dock for own ship) of the port
     * @param isUsed If the dock is already occupied
     * @param pos1 One corner of the dock area
     * @param pos2 Other corner of the dock area
     */
    public Dock(boolean isHomeDock, boolean isUsed, Vec3i pos1, Vec3i pos2){
        this.isHomeDock = isHomeDock;
        this.isUsed = isUsed;
        this.pos1 = pos1;
        this.pos2 = pos2;
    }

    //GETTER
    public boolean isHomeDock() {
        return isHomeDock;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public Vec3i getPos1() {
        return pos1;
    }

    public Vec3i getPos2() {
        return pos2;
    }

    //SETTER
    public void setHomeDock(boolean homeDock) {
        isHomeDock = homeDock;
    }

    public void setPos1(Vec3i pos1) {
        this.pos1 = pos1;
    }

    public void setPos2(Vec3i pos2) {
        this.pos2 = pos2;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }
}
