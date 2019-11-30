package com.yerti.vanillaplus.listeners;

import com.yerti.vanillaplus.items.ItemList;
import com.yerti.vanillaplus.structures.generators.CoalGenerator;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class MachinePlaceListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {

        if (event.getItemInHand().equals(ItemList.COAL_GENERATOR)) {
            new CoalGenerator(event.getBlock().getLocation());
        }




    }

}
