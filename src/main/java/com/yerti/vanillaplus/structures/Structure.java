package com.yerti.vanillaplus.structures;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.line.TextLine;
import org.bukkit.Location;

public abstract class Structure {

    private int energy;

    private Hologram hologram;
    public TextLine line;

    public abstract void update();

    public abstract void create();

    public abstract void destroy();

    public abstract String getType();

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
}
