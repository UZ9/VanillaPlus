package com.yerti.vanillaplus.utils;

import com.yerti.vanillaplus.VanillaPlus;
import com.yerti.vanillaplus.structures.Structure;
import com.yerti.vanillaplus.structures.StructureType;
import com.yerti.vanillaplus.structures.generators.CoalGenerator;
import com.yerti.vanillaplus.utils.config.GeneratorList;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockUpdater {

    public static Map<Location, Structure> machines = new HashMap<Location, Structure>();

    Plugin pl;

    private GeneratorList customConfig = VanillaPlus.customConfig;
    public BlockUpdater(Plugin pl) {
        this.pl = pl;
        addStructures();

    }

    public void gameLoop() {

        updateMachines();
        Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
            @Override
            public void run() {
                gameLoop();
            }
        }, 20L);
    }

    private void addStructures() {
        List<Structure> structures = new ArrayList<Structure>();

        structures.add(new CoalGenerator(null));




        for (Structure machine : structures) {


            if (customConfig == null) return;
            if (customConfig.getCustomConfig() == null) return;
            if (customConfig.getCustomConfig().getConfigurationSection(machine.getType()) == null) return;
            if(customConfig.getCustomConfig().getConfigurationSection(machine.getType()).getKeys(false) == null) return;

            for (String machineID : customConfig.getCustomConfig().getConfigurationSection(machine.getType()).getKeys(false)) {

                Location loc = customConfig.getLocationFromConfig(machine.getType(), machineID);

                try {
                    machines.put(loc, StructureType.valueOf(machine.getType().toUpperCase()).getStructureParent().getConstructor(Location.class).newInstance(loc));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void updateMachines() {

        for (Map.Entry<Location, Structure> entry : machines.entrySet()) {

            Location loc = entry.getKey();

            if (loc.getWorld().isChunkLoaded(loc.getBlockX() >> 4, loc.getBlockZ() >> 4)) {
                entry.getValue().update();
            }


        }





        //Machine list







    }

    public boolean containsMachine(Block block) {
        return containsMachine(block.getLocation());
    }

    public boolean containsMachine(Location loc) {



        return false;
    }
}
