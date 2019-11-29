package com.yerti.vanillaplus.utils.config;

import org.bukkit.plugin.Plugin;

public class Messages {

    Plugin pl;

    public Messages(Plugin pl) {
        this.pl = pl;
    }

    public static Config config;

    public void init() {

        config = new Config(pl);

        config.setPrefix("&f[&aVanillaPlus&f]");

        config.setDefault("wrench-name", "&4Wrench");

        pl.saveConfig();



    }





}
