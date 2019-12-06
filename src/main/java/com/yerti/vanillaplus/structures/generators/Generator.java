package com.yerti.vanillaplus.structures.generators;

import com.yerti.vanillaplus.structures.Structure;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class Generator extends Structure {

    //I still have no idea what to name this
    private List<Structure> energyConsumingMachines = new ArrayList<>();

    public Generator(Location loc, String type, int maxEnergy) {
        super(loc, type, maxEnergy);
    }

    public Generator(Location loc, String type, int maxEnergy, int energy) {
        super(loc, type, maxEnergy, energy);
    }

    @Override
    public void update() {
        System.out.println("Update");
    }

    @Override
    public void create() {

    }

    @Override
    public void destroy() {

    }

    public void handleEnergy() {
        if (getEnergy() != 0) {

            int structureCount = getStructures().size();

            if (structureCount != 0) {

                int energyPerStructure = getEnergy() / structureCount;

                for (Structure structure : getStructures()) {


                    if (structure.getMaxEnergy() - structure.getEnergy() < energyPerStructure) {
                        int margin = structure.getMaxEnergy() - structure.getEnergy();
                        removeEnergy(margin);
                        structure.addEnergy(margin);
                        energyPerStructure = getEnergy() / (structureCount == 1 ? structureCount : structureCount - 1);

                    } else {
                        removeEnergy(energyPerStructure);
                        structure.addEnergy(energyPerStructure);
                    }


                }
            }


        }
    }

    public void addStructure(Structure structure) {
        energyConsumingMachines.add(structure);
    }

    public void removeStructure(Structure structure) {
        energyConsumingMachines.remove(structure);
    }

    public boolean containsStructure(Structure structure) {
        return energyConsumingMachines.contains(structure);
    }

    public List<Structure> getStructures() {
        return energyConsumingMachines;
    }
}
