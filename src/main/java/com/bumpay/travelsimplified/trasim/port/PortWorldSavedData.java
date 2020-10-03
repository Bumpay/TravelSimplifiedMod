package com.bumpay.travelsimplified.trasim.port;

import com.bumpay.travelsimplified.trasim.dock.Dock;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;

import java.util.ArrayList;

public class PortWorldSavedData extends WorldSavedData {

    private static final String NAME = "TraSim_PortData";
    public static ArrayList<Port> portList = new ArrayList<Port>();

    public PortWorldSavedData() {
        super(NAME);
    }

    public PortWorldSavedData(String name) {
        super(name);
    }

    @Override
    public void read(CompoundNBT nbt) {

    }

    //writes the Port List as .nbt File to the server so it will be saved in the Server Directory
    @Override
    public CompoundNBT write(CompoundNBT compound) {                                                                    //NBT_Compound

        ListNBT portsListNBT = new ListNBT();
        compound.put("ports", portsListNBT);                                                                            //-NBT_List "ports"

        for (Port p:portList){
            CompoundNBT portNBT = new CompoundNBT();                                                                    //--NBT_Compound "a single port"
            portNBT.putLong("uuidL", p.getOwner().getUniqueID().getLeastSignificantBits());                             //---NBT_Long "uuidL"
            portNBT.putLong("uuidM", p.getOwner().getUniqueID().getMostSignificantBits());                              //---NBT_Long "uuidM"
            portNBT.putBoolean("isOpen", p.isOpen());                                                                   //---NBT_Bool "isOpen"
            portNBT.putString("name", p.getName());                                                                     //---NBT_String "name"
            portNBT.putInt("x", p.getXCoordinate());                                                                    //---NBT_Int "x"
            portNBT.putInt("y", p.getZCoordinate());                                                                    //---NBT_Int "y"

            ListNBT docksListNBT = new ListNBT();
            portNBT.put("docks", docksListNBT);                                                                         //---NBT_List "docks"

            for (Dock d:p.getDocks())
            {
                CompoundNBT dockNBT = new CompoundNBT();                                                                //----NBT_Compound "a single dock"
                dockNBT.putBoolean("isHomeDock", d.isHomeDock);                                                         //----NBT_Bool "isHomeDock"
                dockNBT.putBoolean("isUsed", d.isUsed);                                                                 //----NBT_Bool "isUsed"
                dockNBT.putIntArray("dockArea", d.getDockArea().toNBTTagIntArray().getIntArray());                      //----NBT_Int[] "dockArea"

                docksListNBT.add(dockNBT);
            }

            portsListNBT.add(portNBT);
        }

        return compound;
    }

    public static PortWorldSavedData get(ServerWorld world) {
        DimensionSavedDataManager data = world.getSavedData();
        PortWorldSavedData instance = data.getOrCreate(() -> new PortWorldSavedData(), NAME);

        return instance;
    }

    public static void addPortToList(Port port, PortWorldSavedData instance){
        portList.add(port);
        instance.markDirty();
    }
}

