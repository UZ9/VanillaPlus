package com.yerti.vanillaplus.listeners;

import com.yerti.vanillaplus.VanillaPlus;
import com.yerti.vanillaplus.core.inventories.AnvilGUI;
import com.yerti.vanillaplus.core.titles.ActionBar;
import com.yerti.vanillaplus.structures.storage.gui.InventoryStorage;
import com.yerti.vanillaplus.utils.BlockUpdater;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PanelListener implements Listener {

    InventoryStorage storage = new InventoryStorage();

    @EventHandler
    public void onPanelInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (BlockUpdater.machines.containsKey(event.getClickedBlock().getLocation())) {
                if (BlockUpdater.machines.get(event.getClickedBlock().getLocation()).getType().equalsIgnoreCase("CRAFTING_TERMINAL")) {
                    if (BlockUpdater.machines.get(event.getClickedBlock().getLocation()).getEnergy() == 0) {
                        ActionBar bar = new ActionBar("&cThis block doesn't have power!");
                        bar.sendToPlayer(event.getPlayer());
                        return;
                    }

                    storage.open(event.getPlayer());





                }
            }
        }
    }

    @EventHandler
    public void onGUIClick(InventoryClickEvent event) {
        if (event.getInventory() == null) return;
        if (event.getRawSlot() == -999) return;
        if (event.getInventory().getHolder() == null) return;
        if (event.getInventory().getHolder() instanceof InventoryStorage) {
            if (event.getRawSlot() >= 45 && event.getRawSlot() <= 54) {
                event.setCancelled(true);
            } else {

                System.out.println("Amount 1: " + event.getInventory().getItem(event.getRawSlot()).getAmount());
                System.out.println("Amount 2: " + event.getCursor().getAmount());
                System.out.println("Amount 3: " + event.getCurrentItem().getAmount());


                return;
            }

            if (event.getRawSlot() == 49) {
                new AnvilGUI.Builder()
                        .preventClose()
                        .onClose(player -> player.sendMessage("Success"))
                        .onComplete((player, text) -> AnvilGUI.Response.close())

                        .plugin(VanillaPlus.instance)
                        .text("Search Items")
                        .open((Player) event.getWhoClicked());
            }

            if (event.getInventory().getItem(event.getRawSlot()).getType().equals(Material.STAINED_GLASS_PANE)) return;

            if (event.getRawSlot() == 51) {


                InventoryStorage.pageNumber.put(((event.getWhoClicked()).getUniqueId()), InventoryStorage.pageNumber.get(event.getWhoClicked().getUniqueId()) + 1);
                storage.open((Player) event.getWhoClicked());
                //event.getWhoClicked().getOpenInventory().getTopInventory().setContents(storage.get((Player) event.getWhoClicked()).getContents());
            } else if (event.getRawSlot() == 47) {
                InventoryStorage.pageNumber.put(((event.getWhoClicked()).getUniqueId()), InventoryStorage.pageNumber.get(event.getWhoClicked().getUniqueId()) - 1);
                System.out.println(InventoryStorage.pageNumber.get(event.getWhoClicked().getUniqueId()));
                storage.open((Player) event.getWhoClicked());
                //event.getWhoClicked().getOpenInventory().getTopInventory().setContents(storage.get((Player) event.getWhoClicked()).getContents());

            }
        }
    }


}
