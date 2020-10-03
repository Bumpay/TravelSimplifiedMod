package com.bumpay.travelsimplified.trasim.port;

import com.bumpay.travelsimplified.trasim.dock.Dock;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;
import java.util.UUID;

public class PortWorldSavedData extends WorldSavedData {

    private static final String NAME = "TraSim_PortData";
    private static ArrayList<Port> portList = new ArrayList<Port>();
    private static ArrayList<Integer> portHash = new ArrayList<>();

    /**
     * Handles port-data in world-data
     */
    public PortWorldSavedData() {
        super(NAME);
    }

    public PortWorldSavedData(String name) {
        super(name);
    }

    @Override
    public void read(CompoundNBT nbt) {
        ListNBT list = nbt.getList("ports", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < list.stream().count(); i++)
        {
            CompoundNBT portNBT = list.getCompound(i);

            ListNBT listDocks = portNBT.getList("docks", Constants.NBT.TAG_COMPOUND);
            ArrayList<Dock> docks = new ArrayList<>();
            for (int j = 0; j < listDocks.stream().count(); j++)
            {
                CompoundNBT dockNBT = listDocks.getCompound(i);

                Dock dock = new Dock(
                        dockNBT.getBoolean("isHomedock"),
                        dockNBT.getBoolean("isUsed"),
                        new Vec3i(dockNBT.getIntArray("pos1")[0], dockNBT.getIntArray("pos1")[1], dockNBT.getIntArray("pos1")[2]),
                        new Vec3i(dockNBT.getIntArray("pos2")[0], dockNBT.getIntArray("pos2")[1], dockNBT.getIntArray("pos2")[2]));
                docks.add(dock);
            }

            Port port = new Port(
                    new UUID(portNBT.getLong("uuidM"), portNBT.getLong("uuidL")),
                    portNBT.getString("name"),
                    portNBT.getInt("x"),
                    portNBT.getInt("z"),
                    portNBT.getBoolean("isOpen"),
                    docks);

            portList.add(port);
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {                                                                    //NBT_Compound

        ListNBT portsListNBT = new ListNBT();
        compound.put("ports", portsListNBT);                                                                            //-NBT_List "ports"

        for (Port p:portList){
            CompoundNBT portNBT = new CompoundNBT();                                                                    //--NBT_Compound "a single port"
            portNBT.putLong("uuidL", p.getUuidOwner().getLeastSignificantBits());                                       //---NBT_Long "uuidL"
            portNBT.putLong("uuidM", p.getUuidOwner().getMostSignificantBits());                                        //---NBT_Long "uuidM"
            portNBT.putBoolean("isOpen", p.isOpen());                                                                   //---NBT_Bool "isOpen"
            portNBT.putString("name", p.getName());                                                                     //---NBT_String "name"
            portNBT.putInt("x", p.getXCoordinate());                                                                    //---NBT_Int "x"
            portNBT.putInt("z", p.getZCoordinate());                                                                    //---NBT_Int "z"

            ListNBT docksListNBT = new ListNBT();
            portNBT.put("docks", docksListNBT);                                                                         //---NBT_List "docks"

            for (Dock d:p.getDocks())
            {
                CompoundNBT dockNBT = new CompoundNBT();                                                                //----NBT_Compound "a single dock"
                dockNBT.putBoolean("isHomeDock", d.isHomeDock());                                                       //----NBT_Bool "isHomeDock"
                dockNBT.putBoolean("isUsed", d.isUsed());                                                               //----NBT_Bool "isUsed"
                dockNBT.putIntArray("pos1", new int[]{d.getPos1().getX(), d.getPos1().getY(), d.getPos1().getZ()});     //----NBT_Int[] "pos1"
                dockNBT.putIntArray("pos2", new int[]{d.getPos2().getX(), d.getPos2().getY(), d.getPos2().getZ()});     //----NBT_Int[] "pos2"
                docksListNBT.add(dockNBT);
            }

            portsListNBT.add(portNBT);
        }

        return compound;
    }

    /**
     * Gets or creates an instance of PortWorldSavedData
     * @param world World the data will be handled in
     * @return
     */
    public static PortWorldSavedData get(ServerWorld world) {
        DimensionSavedDataManager data = world.getSavedData();
        PortWorldSavedData instance = data.getOrCreate(() -> new PortWorldSavedData(), NAME);

        return instance;
    }

    /**
     * Adds a port to the port list, if name is not already in use
     * @param port Port that will be added
     * @param instance Instance of the PortWorldSavedData
     * @return
     */
    public static boolean addPortsToList(Port port, PortWorldSavedData instance){
        for (Port p:portList)
        {
            portHash.add(p.getName().hashCode());
        }
        if(portHash.contains(port.getName().hashCode()))
        {
            return false;
        }
        portList.add(port);
        instance.markDirty();

        return true;
    }

    public static ArrayList<Port> getPortList(PortWorldSavedData instance) {
        instance.read(new CompoundNBT());
        return portList;
    }

    /**
     * Gets a list of all port names
     * @param instance Instance of the PortWorldSavedData
     * @return
     */
    public static ArrayList<String> getPortNameList(PortWorldSavedData instance) {
        instance.read(new CompoundNBT());
        ArrayList<String> portNames = new ArrayList<>();
        for (Port p: portList)
        {
            portNames.add(p.getName());
        }
        return portNames;
    }

    /**
     * Adds a dock to a given port if port is existing
     * @param dock Dock that will be added
     * @param port Port the dock will be added to
     * @param instance Instance of the PortWorldSavedData
     * @return
     */
    public static boolean addDockToPort(Dock dock, String port, PortWorldSavedData instance) {
        int i = getPortNameList(instance).indexOf(port);
        portList.get(i).addDock(dock);
        //TODO check if too far away from port and if return false

        instance.markDirty();
        return true;
    }

    public static ArrayList<Integer> getPortHash() { return portHash; }
}

