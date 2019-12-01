package com.yerti.vanillaplus.structures;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class MultiblockStructure {

    Material[][][] materials;

    public MultiblockStructure(Material[][][] materials) {
        this.materials = materials;
    }

    public void checkForStructure(Player player, Location loc) {
        if (loc.getBlock().getType().equals(Material.REDSTONE_BLOCK)) {
            /*String direction = Utils.getCardinalDirection(player);

            switch (direction.toLowerCase()) {
                case "North":
                    loc.setZ(loc.getZ());
                    break;
                case "East":
                    break;
                case "South":
                    break;
                case "West":
                    break;
            }*/


        }
    }



}
