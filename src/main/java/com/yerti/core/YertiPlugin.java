package com.yerti.core;


import com.yerti.core.command.CommandFramework;
import com.yerti.core.entity.ModelProtection;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Class for loading any additional resources with only calling one method
 * Main class extends YertiPlugin (which then extends JavaPlugin
 */
public class YertiPlugin extends JavaPlugin {

    /**
     * Loads needed resources
     */
    protected void load(Class<?> commandClass) {
        new CommandFramework(this, commandClass);
        getServer().getPluginManager().registerEvents(new ModelProtection(), this);
    }

    protected void load() {
        getServer().getPluginManager().registerEvents(new ModelProtection(), this);
    }

}
