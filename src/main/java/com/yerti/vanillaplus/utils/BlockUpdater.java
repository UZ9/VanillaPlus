package com.yerti.vanillaplus.utils;

import com.yerti.vanillaplus.VanillaPlus;
import com.yerti.vanillaplus.structures.Structure;
import com.yerti.vanillaplus.utils.config.GeneratorList;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class BlockUpdater {

    public static Map<Location, Structure> machines = new HashMap<Location, Structure>();

    Plugin pl;

    //private GeneratorList customConfig = VanillaPlus.customConfig;
    public BlockUpdater(Plugin pl) {
        this.pl = pl;

        VanillaPlus.instance.machineSaver.getStructures();
        updateMachines();

    }

    public void gameLoop() {

        updateMachines();


        Bukkit.getScheduler().scheduleSyncDelayedTask(pl, this::gameLoop, 20L);
    }


    private void updateMachines() {


        for (Map.Entry<Location, Structure> entry : machines.entrySet()) {

            Location loc = entry.getKey();

            if (loc.getWorld().isChunkLoaded(loc.getBlockX() >> 4, loc.getBlockZ() >> 4)) {
                entry.getValue().update();
            }


        }


    }

}
