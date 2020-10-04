package com.bumpay.travelsimplified.trasim.ship;

import com.bumpay.travelsimplified.trasim.port.Port;
import com.bumpay.travelsimplified.trasim.port.PortWorldSavedData;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class ShipWorldSavedData extends WorldSavedData {

    private static final String NAME = "TraSim_ShipData";
    private static HashMap<Integer, Ship> shipHashMap = new HashMap<>();

    public ShipWorldSavedData() {
        super(NAME);
    }

    public ShipWorldSavedData(String name) {
        super(name);
    }

    public static ArrayList<String> getShipNamesByUuid(UUID uuid, ShipWorldSavedData instance) {
        instance.read(new CompoundNBT());

        ArrayList<String> names = new ArrayList<>();
        for(Ship s:shipHashMap.values()){
            if(s.getUuidOwner().equals(uuid))
                names.add(s.getName());
        }

        return names;
    }

    @Override
    public void read(CompoundNBT nbt) {

        ListNBT list = nbt.getList("ships", Constants.NBT.TAG_COMPOUND);

        for(int i = 0; i < list.stream().count(); i++)
        {
            CompoundNBT shipNBT = list.getCompound(i);
            Template template = new Template();
            template.read(shipNBT.getCompound("template"));
            
            Port port = new Port();
            port.read(shipNBT.getCompound("port"));
            
            Ship ship = new Ship(
                    shipNBT.getString("name"),
                    new UUID(shipNBT.getLong("uuidM"), shipNBT.getLong("uuidL")),
                    port,
                    template);

            shipHashMap.put(ship.getName().hashCode(), ship);
        }

    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {

        ListNBT shipsListNBT = new ListNBT();
        compound.put("ships", shipsListNBT);

        for (Ship s:shipHashMap.values()){
            CompoundNBT shipNBT = new CompoundNBT();
            shipNBT.putString("name", s.getName());
            shipNBT.putLong("uuidL", s.getUuidOwner().getLeastSignificantBits());
            shipNBT.putLong("uuidM", s.getUuidOwner().getMostSignificantBits());

            CompoundNBT portNBT = new CompoundNBT();
            s.getHomePort().writeToNBT(portNBT);
            shipNBT.put("port", portNBT);

            CompoundNBT templateNBT = new CompoundNBT();
            s.getShipTemplate().writeToNBT(templateNBT);
            shipNBT.put("template", templateNBT);

            shipsListNBT.add(shipNBT);
        }

        return compound;
    }

    public static ShipWorldSavedData get(ServerWorld world) {
        DimensionSavedDataManager data = world.getSavedData();
        ShipWorldSavedData instance = data.getOrCreate(() -> new ShipWorldSavedData(), NAME);

        if(instance == null) {
            instance = new ShipWorldSavedData();
        }

        return instance;
    }

    public static boolean addShipToList(Ship ship, ShipWorldSavedData instance){
        if(shipHashMap.containsKey(ship.getName().hashCode()))
        {
            return false;
        }
        shipHashMap.put(ship.getName().hashCode(), ship);
        instance.markDirty();

        return true;
    }

    public static HashMap<Integer, Ship> getShipsOfPlayer(UUID uuid, ShipWorldSavedData instance){
        instance.read(new CompoundNBT());

        HashMap<Integer, Ship> ships = new HashMap<>();
        for(Ship s:shipHashMap.values()){
            if(s.getUuidOwner().equals(uuid))
                ships.put(s.getName().hashCode(), s);
        }
        return ships;
    }

    public boolean deleteShip(String shipName, ShipWorldSavedData instance) {
        instance.read(new CompoundNBT());
        if(!shipHashMap.containsKey(shipName.hashCode()))
            return false;

        shipHashMap.remove(shipName.hashCode());
        instance.markDirty();
        return true;
    }
}
