package com.yerti.vanillaplus.events.inventory;

import com.yerti.vanillaplus.utils.MachineUtils;
import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.Plugin;

public class FurnacePrevention implements Listener {

    public FurnacePrevention(Plugin pl) {
        pl.getServer().getPluginManager().registerEvents(this, pl);
    }

    @EventHandler
    public void onFurnaceClick(InventoryClickEvent e) {

        if (e.getInventory() == null) return;

        if (e.getInventory().getHolder() instanceof Furnace) {

            if (MachineUtils.checkMachine("coalGenerators", ((Furnace) e.getInventory().getHolder()).getLocation())) {
                if ((e.getSlot() == 0 || e.getSlot() == 2) && e.getSlotType().equals(InventoryType.SlotType.RESULT)) {
                    e.setCancelled(true);
                }
            }

        }
    }

}
