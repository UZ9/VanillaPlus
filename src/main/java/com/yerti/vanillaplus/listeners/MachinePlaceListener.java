package com.yerti.vanillaplus.listeners;

import com.yerti.vanillaplus.items.ItemList;
import com.yerti.vanillaplus.structures.Structure;
import com.yerti.vanillaplus.structures.generators.CoalGenerator;
import com.yerti.vanillaplus.utils.BlockUpdater;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class MachinePlaceListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {


        if (!event.getItemInHand().hasItemMeta()) return;

        if (event.getItemInHand().getItemMeta().equals(ItemList.COAL_GENERATOR.getItemMeta())) {
            System.out.println(event.getBlock().getLocation().toString());
            new CoalGenerator(event.getBlock().getLocation());
        }




    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {

        for (Block block : event.blockList()) {
            if (BlockUpdater.machines.containsKey(block.getLocation())) {
                BlockUpdater.machines.get(block.getLocation()).destroy();
                block.setType(Material.AIR);
                event.setCancelled(true);
                block.getWorld().dropItem(block.getLocation(), ItemList.COAL_GENERATOR);
            }
        }


    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {

        if (e.getBlock().getType().equals(Material.FURNACE) || e.getBlock().getType().equals(Material.BURNING_FURNACE)) {
            if (BlockUpdater.machines.containsKey(e.getBlock().getLocation())) {
                Structure structure = BlockUpdater.machines.get(e.getBlock().getLocation());
                structure.destroy();
                e.getBlock().setType(Material.AIR);
                e.setCancelled(true);
                e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), ItemList.COAL_GENERATOR);
            }

        }

    }

}
