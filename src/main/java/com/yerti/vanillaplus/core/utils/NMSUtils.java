package com.yerti.vanillaplus.core.utils;

import com.yerti.vanillaplus.core.YertiPlugin;

public class NMSUtils {
    public static Class<?> getNMSClass(String name) {
        try {
            return Class.forName("net.minecraft.server." + YertiPlugin.getHookedPlugin().getServer().getVersion() + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
