package com.bumpay.travelsimplified.trasim.port;

import com.bumpay.travelsimplified.trasim.dock.Dock;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;
import java.util.UUID;

public class Port {
    private int idForDocks = 0;
    private UUID uuidOwner;
    private boolean isOpen = false;
    private String name;
    private BlockPos pos;
    private ArrayList<Dock> docks = new ArrayList<Dock>();

    /**
     *
     * @param uuidOwner The uuid of the player that owns the port
     * @param name The name of the
     */
    public Port(UUID uuidOwner, String name, BlockPos pos){
        this.uuidOwner = uuidOwner;
        this.name = name;
        this.pos = pos;
    }

    /**
     *
     * @param uuidOwner The uuid of the player that owns the port
     * @param name The name of the port
     * @param isOpen If the port is opened for other players
     * @param docks List of docks from port
     */
    public Port(UUID uuidOwner, String name, BlockPos pos, boolean isOpen, ArrayList<Dock> docks){
        this.uuidOwner = uuidOwner;
        this.name = name;
        this.pos = pos;
        this.isOpen = isOpen;
        this.docks = docks;
    }

    public Port() {

    }

    public CompoundNBT writeToNBT(CompoundNBT compound) {                                                               //--NBT_Compound "a single port"
        compound.putLong("uuidL", this.getUuidOwner().getLeastSignificantBits());                                       //---NBT_Long "uuidL"
        compound.putLong("uuidM", this.getUuidOwner().getMostSignificantBits());                                        //---NBT_Long "uuidM"
        compound.putBoolean("isOpen", this.isOpen());                                                                   //---NBT_Bool "isOpen"
        compound.putString("name", this.getName());                                                                     //---NBT_String "name"
        compound.putIntArray("pos", new int[]{this.getPos().getX(), this.getPos().getY(), this.getPos().getZ()});                                                                   //---NBT_Int "z"

        ListNBT docksListNBT = new ListNBT();
        compound.put("docks", docksListNBT);                                                                            //---NBT_List "docks"

        for (Dock d:this.getDocks())
        {
            CompoundNBT dockNBT = new CompoundNBT();                                                                    //----NBT_Compound "a single dock"
            dockNBT.putInt("id", d.getId());
            dockNBT.putBoolean("isHomeDock", d.isHomeDock());                                                           //----NBT_Bool "isHomeDock"
            dockNBT.putBoolean("isUsed", d.isUsed());                                                                   //----NBT_Bool "isUsed"
            dockNBT.putIntArray("pos1", new int[]{d.getPos1().getX(), d.getPos1().getY(), d.getPos1().getZ()});         //----NBT_Int[] "pos1"
            dockNBT.putIntArray("pos2", new int[]{d.getPos2().getX(), d.getPos2().getY(), d.getPos2().getZ()});         //----NBT_Int[] "pos2"
            docksListNBT.add(dockNBT);
        }

        return compound;
    }

    public void read(CompoundNBT compound){

        ListNBT listDocks = compound.getList("docks", Constants.NBT.TAG_COMPOUND);
        ArrayList<Dock> docks = new ArrayList<>();
        for (int i = 0; i < listDocks.stream().count(); i++)
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

        this.uuidOwner = new UUID(compound.getLong("uuidM"), compound.getLong("uuidL"));
        this.name = compound.getString("name");
        this.pos = new BlockPos(compound.getIntArray("pos")[0], compound.getIntArray("pos")[1], compound.getIntArray("pos")[2]);
        this.isOpen = compound.getBoolean("isOpen");
        this.docks = docks;
    }

    /**
     * Checks if any dock of the port is not occupied
     * @return
     */
    public boolean hasPossibleDocks(){
        for(Dock d : docks)
            //TODO check if ship fits inside dock
            if(d.isUsed()) return true;
        return false;
    }

    /**
     * Adds a dock to the docks list
     * @param dock Dock that will be added
     */
    public void addDock(Dock dock) { docks.add(dock); }

    public int getDockId(boolean shouldCount){
        int id = idForDocks;
        if(shouldCount)
            idForDocks++;
        return id;
    }

    /**
     * Opens or closes a port
     * @param open
     */
    public void setOpen(boolean open) {
        isOpen = open;
    }

    //GETTER
    public UUID getUuidOwner(){
        return uuidOwner;
    }

    public String getName(){
        return name;
    }

    public BlockPos getPos() {
        return pos;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public ArrayList<Dock> getDocks() { return docks; }
}
