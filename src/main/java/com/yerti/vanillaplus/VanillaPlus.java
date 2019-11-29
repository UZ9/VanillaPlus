package com.yerti.vanillaplus;

import com.yerti.vanillaplus.events.inventory.FurnacePrevention;
import com.yerti.vanillaplus.events.inventory.WrenchInteract;
import com.yerti.vanillaplus.utils.BlockUpdater;
import com.yerti.vanillaplus.utils.config.Config;
import com.yerti.vanillaplus.utils.config.GeneratorList;
import com.yerti.vanillaplus.utils.config.Messages;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
public class VanillaPlus extends JavaPlugin {

    public static GeneratorList customConfig;
    public static File customConfigFile;

    public static VanillaPlus instance;

    public void onEnable() {

        instance = this;

        //Holographic Displays hook
        //TODO: Find API version of Holographic Displays to reduce space taken
        if (!Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
            getLogger().severe("*** HolographicDisplays is not installed or not enabled. ***");
            getLogger().severe("*** This plugin will be disabled. ***");
            this.setEnabled(false);
            return;
        }

        //Custom generator for Machines stored in machines.yml
        customConfig = new GeneratorList(this);

        //Messages for config.yml
        Messages messages = new Messages(this);

        //Initiating events & config
        new Config(this);
        new FurnacePrevention(this);
        new WrenchInteract(this);

        //Initiating custom config data file (for saving)
        customConfigFile = new File(getDataFolder() + "/machines.yml");

        //Initialize messages and write to config
        messages.init();

        //Initialize Block Updater (Updating for machines)
        BlockUpdater bu = new BlockUpdater(this);

        //im sorry it's 3 am and i dont have the strength for a delay timer
        //TODO: change this badness
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Main update loop
        bu.gameLoop();


    }



}
