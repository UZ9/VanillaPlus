package com.yerti.vanillaplus.structures;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.line.TextLine;
import com.yerti.vanillaplus.utils.MachineUtils;
import org.bukkit.Location;

public abstract class Structure {

    private int energy;
    private int maxEnergy;
    private String type;
    private Location loc;

    public Structure(Location loc, String type, int maxEnergy) {
        this.loc = loc;
        this.type = type;
        this.maxEnergy = maxEnergy;


        //MachineUtils.generateMachineConfig(type, loc, maxEnergy);
    }

    public Structure(Location loc, String type, int maxEnergy, int energy) {
        this.loc = loc;
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

    public void addEnergy(int energy) { this.energy += energy; }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void removeEnergy(int energy) { this.energy -= energy; }

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


    public Location getLoc() {
        return loc;
    }

    public void setLoc(Location loc) {
        this.loc = loc;
    }
}
