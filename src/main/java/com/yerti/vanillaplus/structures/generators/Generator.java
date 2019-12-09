package com.yerti.vanillaplus.structures.generators;

import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.yerti.vanillaplus.VanillaPlus;
import com.yerti.vanillaplus.core.block.BlockUtils;
import com.yerti.vanillaplus.core.entity.CustomModel;
import com.yerti.vanillaplus.structures.Structure;
import com.yerti.vanillaplus.utils.BlockUpdater;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Generator extends Structure {

    CustomModel model = new CustomModel();

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
        BlockUpdater.machines.put(getLoc(), this);
        setHologram(HologramsAPI.createHologram(VanillaPlus.getInstance(), new Location(getLoc().getWorld(), getLoc().getX() + 0.5, getLoc().getY() + 2, getLoc().getZ() + 0.5)));
        line = getHologram().appendTextLine(ChatColor.RED + "" + new DecimalFormat("##.#").format(getEnergy() / 1000.) + "k/" + new DecimalFormat("##.#").format(getMaxEnergy() / 1000.) + "k VU");


        Bukkit.getScheduler().runTaskLaterAsynchronously(VanillaPlus.getInstance(), () -> {

            List<Location> nearbyBlocks = BlockUtils.getBlocks(getLoc(), 5);
            for (Location location : nearbyBlocks) {
                if (BlockUpdater.machines.containsKey(location)) {
                    if (!(BlockUpdater.machines.get(location) instanceof Generator)) {
                        addStructure(BlockUpdater.machines.get(location));
                    }
                }


            }
        }, 20L);
    }

    @Override
    public void destroy() {
        getHologram().delete();
        BlockUpdater.machines.remove(getLoc());
        //MachineUtils.removeMachineConfig("coalGenerators", getMachineID());

        Collection<Entity> entities = getLoc().getWorld().getNearbyEntities(getLoc(), 1, 1, 1);

        for (Entity entity : entities) {
            if (entity instanceof ArmorStand) {
                if (entity.getCustomName().equals("CustomModelPart")) {
                    if (entity.getLocation().getX() == (getLoc().clone().add(0.5, 0, 0).getX()))
                        entity.remove();
                }
            }
        }

        model.destroy();
        VanillaPlus.getMachineSaver().removeMachine(this);
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
