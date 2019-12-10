package com.yerti.vanillaplus;

import com.yerti.vanillaplus.commands.BaseCommand;
import com.yerti.vanillaplus.core.YertiPlugin;
import com.yerti.vanillaplus.core.entity.ModelProtection;
import com.yerti.vanillaplus.data.MachineSaver;
import com.yerti.vanillaplus.energy.EnergyListener;
import com.yerti.vanillaplus.listeners.MachinePlaceListener;
import com.yerti.vanillaplus.listeners.PanelListener;
import com.yerti.vanillaplus.structures.Structure;
import com.yerti.vanillaplus.utils.BlockUpdater;
import com.yerti.vanillaplus.config.Messages;
import org.bukkit.Bukkit;

public class VanillaPlus extends YertiPlugin {

    //public static GeneratorList customConfig;
    //private File customConfigFile;

    private static VanillaPlus instance;

    private static MachineSaver machineSaver;

    public static VanillaPlus getInstance() {
        return instance;
    }

    public static MachineSaver getMachineSaver() {
        return machineSaver;
    }

    public void onEnable() {

        addHookedPlugin(this);


        Bukkit.getServer().getPluginManager().registerEvents(new ModelProtection(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new MachinePlaceListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PanelListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new EnergyListener(), this);
        load(BaseCommand.class);

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
        //customConfig = new GeneratorList(this);

        //Messages for config.yml
        Messages messages = new Messages(this);

        //Initiating events & config
        //new Config(this);

        machineSaver = new MachineSaver();

        //Initiating custom config data file (for saving)
        //customConfigFile = new File(getDataFolder() + "/machines.yml");

        //Initialize messages and write to config
        messages.init();

        //Initialize Block Updater (Updating for machines)
        BlockUpdater bu = new BlockUpdater(this);


        //Main update loop
        bu.gameLoop();


    }

    @Override
    public void onDisable() {
        for (Structure structure : BlockUpdater.machines.values()) {
            machineSaver.saveMachineSync(structure);
        }

        //Close sql connection
        machineSaver.close();
    }


}
