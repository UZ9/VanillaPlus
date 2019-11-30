package com.yerti.vanillaplus.events.inventory;

import com.yerti.vanillaplus.core.items.ItemMetaData;
import com.yerti.vanillaplus.structures.Structure;
import com.yerti.vanillaplus.structures.generators.CoalGenerator;
import com.yerti.vanillaplus.utils.BlockUpdater;
import com.yerti.vanillaplus.utils.ChatManager;
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
import org.bukkit.inventory.ItemStack;
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


                if (!e.getPlayer().getInventory().getItemInHand().hasItemMeta()) return;

                if (e.getPlayer().getInventory().getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', Messages.config.getTranslation("wrench-name")))) {

                    if (e.getClickedBlock().getType().equals(Material.REDSTONE_BLOCK)) {
                        mbs.openInventory(e.getPlayer());
                    }

                    if (e.getClickedBlock().getType().equals(Material.FURNACE)) {

                        if (BlockUpdater.machines.containsKey(e.getClickedBlock().getLocation())) return;


                        short integer = (short) ItemMetaData.getMetadata(e.getItem(), "current-setting");

                        if (integer == 0) {
                            new CoalGenerator(e.getClickedBlock().getLocation());//.create()
                            e.getPlayer().sendMessage(ChatColor.GREEN + "Successfully created a Coal Generator!");// ;
                        } else {

                        }





                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ANVIL_LAND, 10, 2);




                    }







                }
            }
        } else if (e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            System.out.println(1);
            ItemStack stack = new CustomItemStack(Material.GOLD_HOE, ChatColor.translateAlternateColorCodes('&', Messages.config.getTranslation("wrench-name")), 1).getStack();


            if (e.getItem() == null || e.getItem().getType().equals(Material.AIR)) return;
            if (e.getItem().getItemMeta() == null) return;

            System.out.println(2);
            if (stack.getItemMeta().getDisplayName().equals(e.getItem().getItemMeta().getDisplayName()) && ItemMetaData.hasMetadata(e.getItem(), "current-setting")) {
                short integer = (short) ItemMetaData.getMetadata(e.getItem(), "current-setting");
                integer++;
                System.out.println(3);
                if (integer >= 2) integer = 0;

                switch (integer) {
                    case 0:
                        ChatManager.info(e.getPlayer(), "Switched to Coal Generator");
                        break;
                    case 1:
                        ChatManager.info(e.getPlayer(), "Switched to Lava Generator");
                        break;
                }

                System.out.println(4);

                e.getPlayer().setItemInHand(ItemMetaData.setMetadata(e.getItem(), "current-setting", integer));


            }
        }



    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {

        if (e.getBlock().getType().equals(Material.FURNACE) || e.getBlock().getType().equals(Material.BURNING_FURNACE)) {
            if (BlockUpdater.machines.containsKey(e.getBlock().getLocation())) {
                Structure structure = BlockUpdater.machines.get(e.getBlock().getLocation());
                structure.destroy();
                e.getPlayer().sendMessage(ChatColor.RED + "Destroyed Furnace Generator.");
            }

        }

    }


}
