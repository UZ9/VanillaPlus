package com.yerti.vanillaplus.utils;

import com.yerti.vanillaplus.VanillaPlus;
import com.yerti.vanillaplus.core.cooldown.RunnableTask;
import com.yerti.vanillaplus.core.utils.ChunkUtils;
import com.yerti.vanillaplus.structures.Structure;
import com.yerti.vanillaplus.structures.machines.QuarryController;
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

        VanillaPlus.getMachineSaver().getStructures();
        updateMachines();

    }

    public void gameLoop() {

        RunnableTask quarryTask = new RunnableTask(new RunnableTask.Builder().doing(this::updateQuarries).every(15).ticks());
        RunnableTask machineTask = new RunnableTask(new RunnableTask.Builder().doing(this::updateMachines).every(1).seconds());

        quarryTask.start();
        machineTask.start();


        //Bukkit.getScheduler().scheduleSyncDelayedTask(pl, this::gameLoop, 20L);

    }


    private void updateMachines() {


        for (Map.Entry<Location, Structure> entry : machines.entrySet()) {

            Location loc = entry.getKey();

            if (loc.getWorld().isChunkLoaded(loc.getBlockX() >> 4, loc.getBlockZ() >> 4)) {
                entry.getValue().update();

            }




        }


    }

    private void updateQuarries() {

        for (Map.Entry<Location, Structure> entry : machines.entrySet()) {

            Location loc = entry.getKey();

            if (loc.getWorld().isChunkLoaded(loc.getBlockX() >> 4, loc.getBlockZ() >> 4)) {
                if (entry.getValue() instanceof QuarryController) {
                    ((QuarryController) entry.getValue()).updateQuarry();
                }
            }




        }
    }

}
