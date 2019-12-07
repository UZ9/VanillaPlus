package com.yerti.vanillaplus.listeners;

import com.yerti.vanillaplus.VanillaPlus;
import com.yerti.vanillaplus.core.inventories.AnvilGUI;
import com.yerti.vanillaplus.core.items.CustomItemStack;
import com.yerti.vanillaplus.core.titles.ActionBar;
import com.yerti.vanillaplus.core.utils.MathUtils;
import com.yerti.vanillaplus.core.utils.StringUtils;
import com.yerti.vanillaplus.structures.storage.gui.InventoryStorage;
import com.yerti.vanillaplus.utils.BlockUpdater;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

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
        if (event.getRawSlot() >= 54) return;
        if (event.getInventory().getHolder() == null) return;
        if (event.getInventory().getHolder() instanceof InventoryStorage) {
            Player player = (Player) event.getWhoClicked();


            if (event.getRawSlot() >= 45 && event.getRawSlot() < 54) {
                event.setCancelled(true);
            } else {

                if (event.getCurrentItem() == null) {
                    return;
                }


                if (event.getAction() == InventoryAction.PLACE_ALL || event.getAction() == InventoryAction.PLACE_SOME || event.getAction() == InventoryAction.PLACE_ONE) {


                    AtomicBoolean found = new AtomicBoolean(false);


                    ItemStack stack = event.getCursor().clone();

                    player.setItemOnCursor(null);

                    Inventory inventory = event.getInventory();


                    for (ItemStack item : inventory.getContents()) {
                        if (item == null) continue;

                        if (item.getType().equals(stack.getType())) {
                            ItemMeta meta = item.getItemMeta();
                            List<String> lore = item.getItemMeta().getLore();
                            if (lore == null) {
                                lore = new ArrayList<>();
                                lore.add(ChatColor.RED + "Amount: " + stack.getAmount());
                            }

                            if (lore.get(0).startsWith(ChatColor.RED + "Amount: ")) {
                                lore.set(0, ChatColor.RED + "Amount: " + (Integer.parseInt(lore.get(0).replaceAll("[\\D]", "")) + stack.getAmount()));
                            } else {
                                lore.add(0, ChatColor.RED + "Amount: " + (Integer.parseInt(lore.get(0).replaceAll("[\\D]", "")) + stack.getAmount()));
                            }


                            if (item.getAmount() < 64) {
                                if (item.getAmount() + stack.getAmount() > 64) {
                                    item.setAmount(64);
                                } else {
                                    item.setAmount(item.getAmount() + stack.getAmount());
                                }

                            }

                            meta.setLore(lore);
                            item.setItemMeta(meta);
                            found.set(true);
                        }
                    }

                    if (!found.get()) {
                        if (stack.getType() != null) {
                            if (stack.getType() != Material.AIR) {

                                ItemStack stack1 = new ItemStack(stack.getType(), stack.getAmount());
                                ItemMeta meta = stack1.getItemMeta();
                                if (!stack1.hasItemMeta()) meta = Bukkit.getItemFactory().getItemMeta(stack.getType());


                                List<String> lore = meta.getLore();
                                if (lore == null) lore = new ArrayList<>();


                                lore.add(ChatColor.RED + "Amount: " + stack.getAmount());
                                meta.setLore(lore);
                                stack1.setItemMeta(meta);

                                inventory.addItem(stack1);
                            }
                        }


                    }

                    System.out.println("Check 2");

                    event.setCancelled(true);

                    player.setItemInHand(null);

                    player.getOpenInventory().getTopInventory().setContents(inventory.getContents());


                }

                if (event.getAction() == InventoryAction.PICKUP_HALF) {
                    int amount = (int) Math.ceil(event.getCurrentItem().getAmount() / 2.);
                    System.out.println("Amount 3 half: " + amount);

                    ItemMeta meta = event.getCurrentItem().getItemMeta();
                    List<String> lore = meta.getLore();
                    lore.set(0, ChatColor.RED + "Amount: " + (StringUtils.parse(event.getCurrentItem().getItemMeta().getLore().get(0)) - amount));
                    System.out.println(ChatColor.RED + "Amount: " + (StringUtils.parse(event.getCurrentItem().getItemMeta().getLore().get(0)) - amount));
                    meta.setLore(lore);
                    ItemStack stack = event.getCurrentItem();
                    stack.setItemMeta(meta);
                    System.out.println(stack.getItemMeta().getLore().toString());
                    player.getOpenInventory().getTopInventory().setItem(event.getRawSlot(), stack);

                    if (StringUtils.parse(event.getCurrentItem().getItemMeta().getLore().get(0)) - amount > 0) {
                        System.out.println("Yes");
                        System.out.println(MathUtils.clamp(StringUtils.parse(event.getCurrentItem().getItemMeta().getLore().get(0)) - amount, 0, 64));

                        Bukkit.getScheduler().runTaskLaterAsynchronously(VanillaPlus.instance, () -> event.getCurrentItem().setAmount(MathUtils.clamp(StringUtils.parse(event.getCurrentItem().getItemMeta().getLore().get(0)) - amount, 0, 64)), 1L);
                    }


                    if (player.getItemOnCursor() != null && player.getItemOnCursor().getType() != Material.AIR) {

                        System.out.println("foundsssss");
                        Bukkit.getScheduler().runTaskLaterAsynchronously(VanillaPlus.instance, () -> player.setItemOnCursor(new CustomItemStack(player.getItemOnCursor()).stripLore()), 1L);
                    }

                    /*ItemStack s = player.getItemOnCursor();
                    if (s != null && event.getCursor().getType() != Material.AIR) {
                        ItemMeta meta1 = s.getItemMeta();
                        meta1.setLore(new ArrayList<>());
                        s.setItemMeta(meta1);
                    }*/


                } else {
                    System.out.println("Amount 3: " + event.getCurrentItem().getAmount());
                }


                return;
            }

            if (event.getRawSlot() == 49) {
                new AnvilGUI.Builder()
                        .preventClose()
                        .onClose(p -> player.sendMessage("Success"))
                        .onComplete((p, text) -> AnvilGUI.Response.close())

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
