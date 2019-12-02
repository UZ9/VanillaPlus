package com.yerti.vanillaplus.utils;

import org.bukkit.entity.Player;


public class MachineUtils {

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
