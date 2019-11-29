package com.yerti.vanillaplus.events.inventory;

import com.yerti.vanillaplus.structures.Structure;
import com.yerti.vanillaplus.structures.generators.CoalGenerator;
import com.yerti.vanillaplus.utils.BlockUpdater;
import com.yerti.vanillaplus.utils.Utils;
import com.yerti.vanillaplus.utils.config.Messages;
import com.yerti.vanillaplus.utils.inventory.CustomItemStack;
import com.yerti.vanillaplus.utils.inventory.inventories.MultiBlockSelection;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

public class WrenchInteract implements Listener {

    MultiBlockSelection mbs = new MultiBlockSelection();

    public WrenchInteract(Plugin pl) {
        pl.getServer().getPluginManager().registerEvents(this, pl);
    }

    @EventHandler
    public void onPlayerClick(PlayerInteractEvent e) {

        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {

            if (e.getPlayer().getInventory().getItemInHand() == null) return;

            //Check for wrench
            if (e.getPlayer().getInventory().getItemInHand().getType().equals(Material.GOLD_HOE)) {

                e.getPlayer().getInventory().addItem(new CustomItemStack(Material.GOLD_HOE, ChatColor.translateAlternateColorCodes('&', Messages.config.getTranslation("wrench-name")), 1).getStack());

                if (!e.getPlayer().getInventory().getItemInHand().hasItemMeta()) return;

                if (e.getPlayer().getInventory().getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', Messages.config.getTranslation("wrench-name")))) {

                    if (e.getClickedBlock().getType().equals(Material.REDSTONE_BLOCK)) {
                        mbs.openInventory(e.getPlayer());
                    }

                    if (e.getClickedBlock().getType().equals(Material.FURNACE)) {

                        CoalGenerator cg = new CoalGenerator(e.getClickedBlock().getLocation());

                        if (Utils.checkMachine("coalGenerators", e.getClickedBlock().getLocation())) return;
                        else {

                            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ANVIL_LAND, 10, 2);
                            e.getPlayer().sendMessage(ChatColor.GREEN + "Successfully created a Coal Generator!");
                            cg.create();

                        }


                    }







                }
            }
        }



    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {

        if (e.getBlock().getType().equals(Material.FURNACE)) {
            if (BlockUpdater.machines.containsKey(e.getBlock().getLocation())) {
                Structure structure = BlockUpdater.machines.get(e.getBlock().getLocation());
                structure.destroy();
                e.getPlayer().sendMessage(ChatColor.RED + "Destroyed Furnace Generator.");
            }

        }

    }


}
