package com.yerti.vanillaplus.utils;

import com.yerti.vanillaplus.VanillaPlus;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.List;
import java.util.Random;


public class MachineUtils {

    private static String chars = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static FileConfiguration config = VanillaPlus.customConfig.getCustomConfig();

    /**
     * Generates a 32 character string to represent machine ID in machine yaml file
     * @return 32 character string
     */
    public static String generateID() {
        StringBuilder machineID = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            int random = new Random().nextInt(33);
            machineID.append(chars, random, random + 1);
        }
        return machineID.toString();

    }

    /***
     * Generates default machine configuration in machine yaml file containing the location as well as the stored energy inside
     * @param machineType
     * @param machineID
     * @param loc
     * @param maxVU
     */
    public static void generateMachineConfig(String machineType, String machineID, Location loc, int maxVU) {
        config.set("generators." + machineType + "." + machineID + ".x", loc.getBlockX());
        config.set("generators." + machineType + "." + machineID + ".y", loc.getBlockY());
        config.set("generators." + machineType + "." + machineID + ".z", loc.getBlockZ());
        config.set("generators." + machineType + "." + machineID + ".world", loc.getWorld().getName());
        config.set("generators." + machineType + "." + machineID + ".VU", 0);
        config.set("generators." + machineType + "." + machineID + ".maxVU", maxVU);

        try {
            config.save(VanillaPlus.customConfigFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TODO: Errors (?)
    public static void removeMachineConfig(String machineType, String machineID) {
        List<String> list = config.getStringList(machineType);

        list.addAll(config.getConfigurationSection("generators." + machineType).getKeys(false));

        list.remove(machineID);
        config.set("generators." + machineType, list);
        config.set("generators." + machineType + "." + machineID, null);

        try {
            config.save(VanillaPlus.customConfigFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TODO: Add to BlockUpdater
    public static void updateMachineConfig(String machineType, String machineID, int voxelUnits) {
        config.set("generators." + machineType + "." + machineID + ".VU", voxelUnits);

        try {
            config.save(VanillaPlus.customConfigFile);
        } catch (IOException e) {
            System.out.println("An exception during saving the config happened.");
        }
    }

    public static boolean checkMachine(String machineType, Location loc) {
        if (config.getConfigurationSection(machineType) == null)
            return false;

        for (String machineIDs : config.getConfigurationSection(machineType).getKeys(false)) {

            if (VanillaPlus.customConfig.getLocationFromConfig(machineType, machineIDs).equals(loc))
                return true;

        }

        return false;
    }

    public static String getCardinalDirection(Player player) {
        double rot = (player.getLocation().getYaw() - 90) % 360;
        if (rot < 0) {
            rot += 360.0;
        }
        return getDirection(rot);
    }

    private static String getDirection(double rot) {
        if (0 <= rot && rot < 89.5) {
            return "North";
        } else if (90 <= rot && rot < 179.5) {
            return "East";
        } else if (180 <= rot && rot < 269.5) {
            return "South";
        } else if (270 <= rot && rot < 360) {
            return "West";
        } else {
            return null;
        }
        }


}
