package com.yerti.vanillaplus.listeners;

import com.yerti.vanillaplus.VanillaPlus;
import com.yerti.vanillaplus.core.inventories.AnvilGUI;
import com.yerti.vanillaplus.utils.BlockUpdater;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PanelListener implements Listener {

    @EventHandler
    public void onPanelInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (BlockUpdater.machines.containsKey(event.getClickedBlock().getLocation())) {
                if (BlockUpdater.machines.get(event.getClickedBlock().getLocation()).getType().equalsIgnoreCase("CRAFTING_TERMINAL")) {
                    if (BlockUpdater.machines.get(event.getClickedBlock().getLocation()).getEnergy() == 0) return;

                    new AnvilGUI.Builder()
                            .preventClose()
                            .onClose(player -> player.sendMessage("Success"))
                            .onComplete((player, text) -> AnvilGUI.Response.close())

                            .plugin(VanillaPlus.instance)
                            .text("Search Items")
                            .open(event.getPlayer());
                }
            }
        }
    }


}
