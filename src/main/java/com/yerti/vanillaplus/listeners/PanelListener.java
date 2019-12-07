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
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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

    //TODO: Redo this because it's wack
    @EventHandler
    public void onGUIClick(InventoryClickEvent event) {
        if (event.getInventory() == null) return;
        if (event.getRawSlot() == -999) return;
        System.out.println("epic1");
        //if (event.getRawSlot() >= 54) return;

        if (event.getWhoClicked().getOpenInventory().getTopInventory().getHolder() instanceof InventoryStorage) {
            if (event.getRawSlot() >= 54) {

                if (event.isShiftClick()) {
                    event.setCancelled(true);
                    return;
                }

                return;
            }


        }
        if (event.getRawSlot() >= 54) return;
        if (event.getWhoClicked().getOpenInventory() == null) return;

        if (event.getInventory().getHolder() instanceof InventoryStorage || ((Player) event.getWhoClicked()).getOpenInventory().getTopInventory().getHolder() instanceof InventoryStorage) {
            if (event.isShiftClick()) return;
            System.out.println("valid");
            Player player = (Player) event.getWhoClicked();

            event.setCancelled(true);
            System.out.println("EVENT WAS CANCELLED");

            if (event.getRawSlot() >= 45 && event.getRawSlot() < 54) {
                event.setCancelled(true);
            } else {

                System.out.println(event.getAction().toString());


                if (event.getAction() == InventoryAction.PLACE_ALL || event.getAction() == InventoryAction.PLACE_SOME || event.getAction() == InventoryAction.PLACE_ONE) {


                    AtomicBoolean found = new AtomicBoolean(false);


                    ItemStack stack = event.getCursor().clone();

                    player.setItemOnCursor(null);

                    Inventory inventory = Bukkit.createInventory(event.getInventory().getHolder(), event.getInventory().getSize(), event.getInventory().getName());
                    inventory.setContents(event.getInventory().getContents());


                    Bukkit.getScheduler().runTaskAsynchronously(VanillaPlus.instance, () -> {
                        for (ItemStack item : inventory.getContents()) {
                            if (item == null) continue;

                            if (item.getType().equals(stack.getType())) {
                                CustomItemStack c = new CustomItemStack(item);
                                String lore = ChatColor.RED + "Amount: " + (Integer.parseInt(c.getLore(0).replaceAll("[\\D]", "")) + stack.getAmount());
                                if (c.getItemMeta().hasLore()) {
                                    c.lore(0, lore);
                                } else {
                                    c.lore(lore);
                                }


                                if (item.getAmount() < 64) {
                                    if (item.getAmount() + stack.getAmount() > 64) {
                                        item.setAmount(64);
                                    } else {
                                        item.setAmount(item.getAmount() + stack.getAmount());
                                    }

                                }

                                item.setItemMeta(c.getItemMeta());
                                found.set(true);
                            }
                        }

                        if (!found.get()) {
                            if (stack.getType() != null) {
                                if (stack.getType() != Material.AIR) {

                                    CustomItemStack stack1 = new CustomItemStack(stack).lore(ChatColor.RED + "Amount: " + stack.getAmount());
                                    inventory.addItem(stack1);

                                }
                            }


                        }
                        System.out.println("Check 2");


                        player.setItemInHand(null);

                        Bukkit.getScheduler().runTaskLaterAsynchronously(VanillaPlus.instance, () -> player.getOpenInventory().getTopInventory().setContents(inventory.getContents()), 2L);

                    });


                }

                //TODO: Redo this
                if (event.getAction() == InventoryAction.PICKUP_HALF) {
                    int amount = (int) Math.ceil(event.getCurrentItem().getAmount() / 2.);

                    ItemMeta meta = event.getCurrentItem().getItemMeta();
                    List<String> lore = meta.getLore();
                    lore.set(0, ChatColor.RED + "Amount: " + (StringUtils.parse(event.getCurrentItem().getItemMeta().getLore().get(0)) - amount));
                    System.out.println(ChatColor.RED + "Amount: " + (StringUtils.parse(event.getCurrentItem().getItemMeta().getLore().get(0)) - amount));
                    meta.setLore(lore);
                    ItemStack stack = event.getCurrentItem();
                    stack.setItemMeta(meta);
                    System.out.println(stack.getItemMeta().getLore().toString());
                    event.getCurrentItem().setItemMeta(meta);

                        System.out.println("Yes");
                        System.out.println(MathUtils.clamp(StringUtils.parse(event.getCurrentItem().getItemMeta().getLore().get(0)), 0, 64));

                        Bukkit.getScheduler().runTaskLaterAsynchronously(VanillaPlus.instance, () -> event.getCurrentItem().setAmount(MathUtils.clamp(StringUtils.parse(event.getCurrentItem().getItemMeta().getLore().get(0)), 0, 64)), 1L);



                    Bukkit.getScheduler().runTaskLaterAsynchronously(VanillaPlus.instance, () -> {


                        System.out.println("foundsssss");
                        player.setItemOnCursor(new CustomItemStack(event.getCurrentItem()).stripLore().amount(event.getCurrentItem().getAmount()));

                    }, 1L);


                } else if (event.getAction() == InventoryAction.PICKUP_ALL) {
                    Bukkit.getScheduler().runTaskLater(VanillaPlus.instance, () -> {

                        if (StringUtils.parse(event.getCurrentItem().getItemMeta().getLore().get(0)) <= 64) {
                            System.out.println("Found, trying to do it now");

                            player.setItemOnCursor(new CustomItemStack(event.getCurrentItem()).stripLore());
                            event.getCurrentItem().setType(Material.AIR);
                            event.getInventory().setItem(event.getRawSlot(), new ItemStack(Material.AIR));

                            return;
                        }

                        int amount = event.getCurrentItem().getAmount();
                        System.out.println("amount because why not: " + (StringUtils.parse(event.getCurrentItem().getItemMeta().getLore().get(0)) - amount));

                        ItemStack stack = new CustomItemStack(event.getCurrentItem()).lore(0, ChatColor.RED + "Amount: " + (StringUtils.parse(event.getCurrentItem().getItemMeta().getLore().get(0)) - amount));
                        event.getCurrentItem().setItemMeta(stack.getItemMeta());
                        //player.getOpenInventory().getTopInventory().setItem(event.getRawSlot(), stack);


                        //if (StringUtils.parse(event.getCurrentItem().getItemMeta().getLore().get(0)) - amount > 0) {
                        System.out.println("Yes");


                        System.out.println(MathUtils.clamp(StringUtils.parse(event.getCurrentItem().getItemMeta().getLore().get(0)) - amount, 0, 64));


                        Bukkit.getScheduler().runTaskLaterAsynchronously(VanillaPlus.instance, () -> event.getCurrentItem().setAmount(MathUtils.clamp(StringUtils.parse(event.getCurrentItem().getItemMeta().getLore().get(0)), 0, 64)), 1L);


                        //}


                        Bukkit.getScheduler().runTaskLaterAsynchronously(VanillaPlus.instance, () -> {
                            player.setItemOnCursor(new CustomItemStack(event.getCurrentItem()).amount(amount).stripLore());
                        }, 0L);


                    }, 0L);

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

    @EventHandler
    public void onDragEvent(InventoryDragEvent event) {
        if (event.getInventory() == null) return;
        if (event.getInventory().getHolder() == null) return;
        if (event.getWhoClicked().getOpenInventory() == null) return;
        if (event.getInventory().getHolder() instanceof InventoryStorage || (event.getWhoClicked()).getOpenInventory().getTopInventory().getHolder() instanceof InventoryStorage) {
            event.setCancelled(true);
        }
    }


}
