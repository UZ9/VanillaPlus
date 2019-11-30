package com.yerti.vanillaplus.utils;

import com.yerti.vanillaplus.VanillaPlus;
import com.yerti.vanillaplus.structures.Structure;
import com.yerti.vanillaplus.structures.StructureType;
import com.yerti.vanillaplus.structures.generators.CoalGenerator;
import com.yerti.vanillaplus.utils.config.GeneratorList;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

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
        Bukkit.getScheduler().scheduleSyncDelayedTask(pl, this::gameLoop, 20L);
    }

    private void addStructures() {
        List<Structure> structures = new ArrayList<Structure>();

        //structures.add(new CoalGenerator(null));


        FileConfiguration config = customConfig.getCustomConfig();

        if (config.get("generators") == null) return;

        for (String type : config.getConfigurationSection("generators").getKeys(false)) {
            for (String machineID : config.getConfigurationSection("generators." + type).getKeys(false)) {
                Location location = customConfig.getLocationFromConfig(type, machineID);

                try {

                    StructureType.valueOf(type.toUpperCase()).getStructureParent().getConstructor(Location.class, String.class, String.class, Integer.class, Integer.class).newInstance(location, machineID, type, config.getInt("generators." + type + "." + machineID + ".maxVU"), config.getInt("generators." + type + "." + machineID + ".VU"));
                    Bukkit.getLogger().log(Level.INFO, "Adding machine " + machineID);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }



        }

        /*for (Structure machine : structures) {


            if (customConfig == null) return;
            if (customConfig.getCustomConfig() == null) return;
            if (customConfig.getCustomConfig().getConfigurationSection(machine.getType()) == null) return;
            if(customConfig.getCustomConfig().getConfigurationSection(machine.getType()).getKeys(false) == null) return;

            for (String machineID : customConfig.getCustomConfig().getConfigurationSection(machine.getType()).getKeys(false)) {

                Location loc = customConfig.getLocationFromConfig(machine.getType(), machineID);

                try {
                    machines.put(loc, StructureType.valueOf(machine.getType().toUpperCase()).getStructureParent().getConstructor(Location.class, String.class, String.class, Integer.class, Integer.class).newInstance(loc, machineID, ));
                    Bukkit.broadcastMessage("Loaded machine");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }*/
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
