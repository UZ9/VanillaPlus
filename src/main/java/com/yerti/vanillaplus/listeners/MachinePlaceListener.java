package com.yerti.vanillaplus.listeners;

import com.yerti.vanillaplus.items.ItemList;
import com.yerti.vanillaplus.structures.Structure;
import com.yerti.vanillaplus.structures.generators.CoalGenerator;
import com.yerti.vanillaplus.structures.storage.CraftingTerminal;
import com.yerti.vanillaplus.utils.BlockUpdater;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class MachinePlaceListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {


        if (!event.getItemInHand().hasItemMeta()) return;

        if (event.getItemInHand().getItemMeta().equals(ItemList.COAL_GENERATOR.getItemMeta())) {
            System.out.println(event.getBlock().getLocation().toString());
            new CoalGenerator(event.getBlock().getLocation());
        } else if (event.getItemInHand().getItemMeta().equals(ItemList.CRAFTING_TERMINAL.getItemMeta())) {
            new CraftingTerminal(event.getBlock().getLocation());
        }


    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {

        for (Block block : event.blockList()) {
            if (BlockUpdater.machines.containsKey(block.getLocation())) {
                if (BlockUpdater.machines.get(block.getLocation()).getType().equalsIgnoreCase("COAL_GENERATOR")) {
                    block.getWorld().dropItem(block.getLocation(), ItemList.COAL_GENERATOR);
                } else {
                    block.getWorld().dropItem(block.getLocation(), ItemList.CRAFTING_TERMINAL);
                }
                BlockUpdater.machines.get(block.getLocation()).destroy();
                block.setType(Material.AIR);
                event.setCancelled(true);

            }
        }


    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {

        if (!BlockUpdater.machines.containsKey(e.getBlock().getLocation())) return;

        Structure structure = BlockUpdater.machines.get(e.getBlock().getLocation());
        Location loc = e.getBlock().getLocation();
        World w = e.getBlock().getWorld();

        switch (e.getBlock().getType()) {
            case FURNACE:
                System.out.println("Wack2");
            case BURNING_FURNACE:
                System.out.println("Dropping 1");
                w.dropItem(loc, ItemList.COAL_GENERATOR);
                break;
            case IRON_BLOCK:
                System.out.println("Dropping 2");
                w.dropItem(loc, ItemList.CRAFTING_TERMINAL);
                break;
        }

        e.getBlock().setType(Material.AIR);
        e.setCancelled(true);
        structure.destroy();


    }

}
