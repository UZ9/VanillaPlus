package com.yerti.vanillaplus.structures.generators;

import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.yerti.vanillaplus.VanillaPlus;
import com.yerti.vanillaplus.structures.Structure;
import com.yerti.vanillaplus.utils.BlockUpdater;
import com.yerti.vanillaplus.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Furnace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;

import static com.yerti.vanillaplus.VanillaPlus.customConfig;


public class CoalGenerator extends Structure {



    private Location loc;
    private FileConfiguration config = VanillaPlus.customConfig.getCustomConfig();
    private File configFile = VanillaPlus.customConfigFile;
    private String machineID;


    public CoalGenerator(Location loc) {
        this.loc = loc;
        machineID = Utils.generateID();
    }

    @Override
    public String getType() { return "coalGenerators"; }

    @Override
    public void create() {
        Utils.generateMachineConfig("coalGenerators", machineID, loc, 100000);
        BlockUpdater.machines.put(loc, this);
        setHologram(HologramsAPI.createHologram(VanillaPlus.instance, new Location(loc.getWorld(), loc.getX() + 0.5, loc.getY() + 2, loc.getZ() + 0.5)));
        line = getHologram().appendTextLine(ChatColor.RED + "Energy: " + getEnergy());
    }

    @Override
    public void destroy() {
        getHologram().delete();
        BlockUpdater.machines.remove(loc);
        Utils.removeMachineConfig("coalGenerators", machineID);




    }

    @Override
    public void update() {

        if (loc.getBlock().getState() == null) return;
        if (!(loc.getBlock().getState() instanceof Furnace)) return;


        Furnace generator = (Furnace) loc.getBlock().getState();

        if (generator.getInventory().getFuel() == null) {
            generator.setBurnTime((short) 0);
            return;
        }

        if (generator.getInventory().getFuel().getType().equals(Material.COAL)) {

            if (generator.getInventory().getFuel().getAmount() == 1) {
                generator.getInventory().setFuel(new ItemStack(Material.AIR));
                return;
            }

            generator.getInventory().getFuel().setAmount(generator.getInventory().getFuel().getAmount() - 1);
            generator.setBurnTime((short) (generator.getBurnTime() + (short) 100));

            setEnergy(getEnergy() + 100);

            //Update hologram with energy count
            line.setText(ChatColor.RED + "Energy: " + getEnergy());


            if (generator.getInventory().getResult() != null) {
                if (generator.getInventory().getResult().getType().equals(Material.COAL)) {
                    generator.getInventory().setResult(null);
                }
            }

            if (generator.getInventory().getSmelting() != null) {
                generator.getInventory().setSmelting(null);
            }


            if (loc.getBlock().getType().equals(Material.FURNACE)) {
                generator.getInventory().getFuel().setAmount(generator.getInventory().getFuel().getAmount() + 1);
                generator.getInventory().setSmelting(new ItemStack(Material.LOG));
            }

        }
    }

}
