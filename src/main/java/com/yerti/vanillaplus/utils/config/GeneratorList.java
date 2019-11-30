package com.yerti.vanillaplus.utils.config;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class GeneratorList {

    private FileConfiguration customConfig;
    private File customConfigFile;

    private static Plugin pl;

    public FileConfiguration getCustomConfig() {
        return customConfig;
    }

    public GeneratorList(Plugin pl) {
        this.pl = pl;
        try {
            createCustomConfig();
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void createCustomConfig() throws IOException, InvalidConfigurationException {
        customConfigFile = new File(pl.getDataFolder(), "machines.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            pl.saveResource("machines.yml", false);
        }

        customConfig = new YamlConfiguration();
        customConfig.load(customConfigFile);
    }

    private String getMachineStats(String machineType, String machineID) {
        return "generators." + machineType + "." + machineID + ".";
    }

    public Location getLocationFromConfig(String machineType, String machineID) {

        String prefix = getMachineStats(machineType, machineID);

        return new Location(Bukkit.getWorld((String) customConfig.get(prefix + "world")), customConfig.getDouble(prefix + "x"), customConfig.getDouble(prefix + "y"), customConfig.getDouble(prefix + "z"));


    }




}
