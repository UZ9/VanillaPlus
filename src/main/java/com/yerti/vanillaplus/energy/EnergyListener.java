package com.yerti.vanillaplus.energy;

import com.yerti.vanillaplus.core.block.BlockUtils;
import com.yerti.vanillaplus.events.StructureDestroyEvent;
import com.yerti.vanillaplus.events.StructurePlaceEvent;
import com.yerti.vanillaplus.structures.Structure;
import com.yerti.vanillaplus.structures.generators.Generator;
import com.yerti.vanillaplus.utils.BlockUpdater;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

public class EnergyListener implements Listener {

    @EventHandler
    public void onStructurePlace(StructurePlaceEvent event) {

        Structure structure = event.getStructure();

        if (structure instanceof Generator) return;

        List<Location> nearbyBlocks = BlockUtils.getBlocks(structure.getLoc(), 5);

        //Add this as a machine that requires energy (generators work wirelessly, machines found in the radius are from this event rather than a lag inducing update
        for (Location location : nearbyBlocks) {
            if (BlockUpdater.machines.containsKey(location)) {
                if (BlockUpdater.machines.get(location) instanceof Generator) {
                    Generator generator = (Generator) BlockUpdater.machines.get(location);
                    generator.addStructure(event.getStructure());
                }
            }
        }


    }


    @EventHandler
    public void onStructureDestroy(StructureDestroyEvent event) {
        Structure structure = event.getStructure();

        List<Location> nearbyBlocks = BlockUtils.getBlocks(structure.getLoc(), 5);

        for (Location location : nearbyBlocks) {
            if (BlockUpdater.machines.containsKey(location)) {
                if (BlockUpdater.machines.get(location) instanceof Generator) {
                    if (((Generator) BlockUpdater.machines.get(location)).containsStructure(structure)) {
                        ((Generator) BlockUpdater.machines.get(location)).removeStructure(structure);
                    }
                }
            }
        }
    }

}
