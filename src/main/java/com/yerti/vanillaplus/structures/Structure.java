package com.yerti.vanillaplus.structures;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.line.TextLine;
import com.yerti.vanillaplus.utils.Utils;
import org.bukkit.Location;

public abstract class Structure {

    private int energy;
    private int maxEnergy;
    private String machineID;
    private String type;
    private Location loc;

    public Structure(Location loc, String type, int maxEnergy) {
        this.loc = loc;
        this.type = type;
        this.maxEnergy = maxEnergy;
        this.machineID = Utils.generateID();


        Utils.generateMachineConfig(type, machineID, loc, maxEnergy);
    }

    public Structure(Location loc, String machineID, String type, int maxEnergy, int energy) {
        this.loc = loc;
        this.machineID = machineID;
        this.type = type;
        this.maxEnergy = maxEnergy;
        this.energy = energy;
    }

    private Hologram hologram;
    public TextLine line;

    public abstract void update();

    public abstract void create();

    public abstract void destroy();

    public String getType() {
        return type;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public Hologram getHologram() {
        return hologram;
    }

    public void setHologram(Hologram hologram) {
        this.hologram = hologram;
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    public void setMaxEnergy(int maxEnergy) {
        this.maxEnergy = maxEnergy;
    }

    public String getMachineID() {
        return machineID;
    }

    public void setMachineID(String machineID) {
        this.machineID = machineID;
    }

    public Location getLoc() {
        return loc;
    }

    public void setLoc(Location loc) {
        this.loc = loc;
    }
}
