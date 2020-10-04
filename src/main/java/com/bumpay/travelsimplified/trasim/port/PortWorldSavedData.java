package com.bumpay.travelsimplified.trasim.port;

import com.bumpay.travelsimplified.trasim.dock.Dock;
import jdk.nashorn.internal.ir.Block;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class PortWorldSavedData extends WorldSavedData {

    private static final String NAME = "TraSim_PortData";
    private static HashMap<Integer, Port> portHashMap = new HashMap<>();

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
                        dockNBT.getInt("id"),
                        dockNBT.getBoolean("isHomedock"),
                        dockNBT.getBoolean("isUsed"),
                        new BlockPos(dockNBT.getIntArray("pos1")[0], dockNBT.getIntArray("pos1")[1], dockNBT.getIntArray("pos1")[2]),
                        new BlockPos(dockNBT.getIntArray("pos2")[0], dockNBT.getIntArray("pos2")[1], dockNBT.getIntArray("pos2")[2]));
                docks.add(dock);
            }

            Port port = new Port(
                    new UUID(portNBT.getLong("uuidM"), portNBT.getLong("uuidL")),
                    portNBT.getString("name"),
                    new BlockPos(portNBT.getIntArray("pos")[0], portNBT.getIntArray("pos")[1], portNBT.getIntArray("pos")[2]),
                    portNBT.getBoolean("isOpen"),
                    docks);

            portHashMap.put(port.getName().hashCode(), port);
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {                                                                    //NBT_Compound

        ListNBT portsListNBT = new ListNBT();
        compound.put("ports", portsListNBT);                                                                            //-NBT_List "ports"

        for (Port p: portHashMap.values()){
            CompoundNBT portNBT = new CompoundNBT();                                                                    //--NBT_Compound "a single port"
            portNBT.putLong("uuidL", p.getUuidOwner().getLeastSignificantBits());                                       //---NBT_Long "uuidL"
            portNBT.putLong("uuidM", p.getUuidOwner().getMostSignificantBits());                                        //---NBT_Long "uuidM"
            portNBT.putBoolean("isOpen", p.isOpen());                                                                   //---NBT_Bool "isOpen"
            portNBT.putString("name", p.getName());                                                                     //---NBT_String "name"
            portNBT.putIntArray("pos", new int[]{p.getPos().getX(), p.getPos().getY(), p.getPos().getZ()});                                                                   //---NBT_Int "z"

            ListNBT docksListNBT = new ListNBT();
            portNBT.put("docks", docksListNBT);                                                                         //---NBT_List "docks"

            for (Dock d:p.getDocks())
            {
                CompoundNBT dockNBT = new CompoundNBT();                                                                //----NBT_Compound "a single dock"
                dockNBT.putInt("id", d.getId());
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
    public static boolean addPortToList(Port port, PortWorldSavedData instance){
        if(portHashMap.containsKey(port.getName().hashCode()))
        {
            return false;
        }
        portHashMap.put(port.getName().hashCode(), port);
        instance.markDirty();

        return true;
    }

    public static HashMap<Integer, Port> getPortHashMap(PortWorldSavedData instance) {
        instance.read(new CompoundNBT());

        return portHashMap;
    }

    /**
     * Gets a list of all port names
     * @param instance Instance of the PortWorldSavedData
     * @return
     */
    public static ArrayList<String> getPortNameListByUuid(UUID uuid, PortWorldSavedData instance) {
        instance.read(new CompoundNBT());

        ArrayList<String> portNames = new ArrayList<>();
        for (Port p: portHashMap.values())
        {
            if(p.getUuidOwner().equals(uuid))
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
        portHashMap.get(port.hashCode()).addDock(dock);
        dock.setId(portHashMap.get(port.hashCode()).getDockId(true));
        //TODO check if too far away from port and if return false

        instance.markDirty();
        return true;
    }

    public static HashMap<Integer, Port> getPortsOfPlayer(UUID uuid, PortWorldSavedData instance){
        instance.read(new CompoundNBT());

        HashMap<Integer, Port> ports = new HashMap<>();
        for(Port p: portHashMap.values()){
            if(p.getUuidOwner().equals(uuid))
                ports.put(p.getName().hashCode(), p);
        }
        return ports;
    }

    public static boolean deletePort(String portName, PortWorldSavedData instance){
        instance.read(new CompoundNBT());
        if(!portHashMap.containsKey(portName.hashCode()))
           return false;

        portHashMap.remove(portName.hashCode());
        instance.markDirty();
        return true;
    }
}

