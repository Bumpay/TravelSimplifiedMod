package com.bumpay.travelsimplified.trasim.dock;

import net.minecraft.util.math.MutableBoundingBox;
import org.lwjgl.system.CallbackI;

public class Dock {
    public boolean isHomeDock;
    public boolean isUsed;
    public MutableBoundingBox dockArea;

    public Dock(){

    }

    public MutableBoundingBox getDockArea(){ return dockArea; }
}
