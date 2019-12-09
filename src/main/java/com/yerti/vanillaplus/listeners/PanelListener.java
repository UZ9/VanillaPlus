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

        if (event.getWhoClicked().getOpenInventory().getTopInventory().getHolder() instanceof InventoryStorage) {
            if (event.getRawSlot() >= 54) {
                if (event.isShiftClick()) {
                    event.setCancelled(true);
                }
                return;
            }

        }

        if (event.getRawSlot() >= 54) return;
        if (event.getWhoClicked().getOpenInventory() == null) return;

        if (event.getInventory().getHolder() instanceof InventoryStorage || event.getWhoClicked().getOpenInventory().getTopInventory().getHolder() instanceof InventoryStorage) {
            if (event.isShiftClick()) return;

            Player player = (Player) event.getWhoClicked();
            ItemStack item = event.getCurrentItem();

            event.setCancelled(true);


            if (event.getAction() == InventoryAction.PLACE_ALL || event.getAction() == InventoryAction.PLACE_SOME || event.getAction() == InventoryAction.PLACE_ONE) {

                ItemStack stack = event.getCursor().clone();

                player.setItemOnCursor(null);

                Bukkit.getScheduler().runTaskAsynchronously(VanillaPlus.getInstance(), () -> {
                    addItem(stack, event.getInventory());

                    player.setItemInHand(null);

                    Bukkit.getScheduler().runTaskLaterAsynchronously(VanillaPlus.getInstance(), () -> player.getOpenInventory().getTopInventory().setContents(event.getInventory().getContents()), 2L);

                });


            }

            //TODO: Redo this
            if (event.getAction() == InventoryAction.PICKUP_HALF) {
                int amount = (int) Math.ceil(item.getAmount() / 2.);

                if (item.getAmount() == 1) {
                    player.setItemOnCursor(new CustomItemStack(item).removeLore(item.getItemMeta().getLore().size() - 1).amount(amount));

                    event.getInventory().setItem(event.getRawSlot(), new ItemStack(Material.AIR));
                    return;
                }

                item.setItemMeta(new CustomItemStack(item).lore(item.getItemMeta().getLore().size() - 1, ChatColor.RED + "Amount: " + (getAmount(item) - amount)).getItemMeta());

                Bukkit.getScheduler().runTaskLaterAsynchronously(VanillaPlus.getInstance(), () -> {
                    item.setAmount(MathUtils.clamp(getAmount(item), 0, 64));
                    player.setItemOnCursor(new CustomItemStack(item).removeLore(item.getItemMeta().getLore().size() - 1).amount(amount));


                }, 1L);


            } else if (event.getAction() == InventoryAction.PICKUP_ALL) {
                Bukkit.getScheduler().runTaskLater(VanillaPlus.getInstance(), () -> {

                    if (getAmount(item) <= 64) {
                        if (item.getMaxStackSize() >= getAmount(item)) {

                            player.setItemOnCursor(new CustomItemStack(item).removeLore(item.getItemMeta().getLore().size() - 1));

                            event.getInventory().setItem(event.getRawSlot(), new ItemStack(Material.AIR));

                            return;
                        }

                    }

                    int amount = MathUtils.clamp(item.getAmount(), 0, item.getMaxStackSize());


                    ItemStack stack = new CustomItemStack(item).lore(0, ChatColor.RED + "Amount: " + (getAmount(item) - amount));
                    item.setItemMeta(stack.getItemMeta());

                    Bukkit.getScheduler().runTaskLaterAsynchronously(VanillaPlus.getInstance(), () -> {
                        item.setAmount(MathUtils.clamp(getAmount(item), 0, 64));
                        player.setItemOnCursor(new CustomItemStack(item).amount(amount).removeLore(item.getItemMeta().getLore().size() - 1));
                    }, 1L);


                }, 1L);

            }

            if (event.getRawSlot() == 49) {
                new AnvilGUI.Builder()
                        .preventClose()
                        .onClose(p -> p.sendMessage("Success"))
                        .onComplete((p, text) -> AnvilGUI.Response.close())

                        .plugin(VanillaPlus.getInstance())
                        .text("Search Items")
                        .open(player);
            }

            if (event.getInventory().getItem(event.getRawSlot()).getType().equals(Material.STAINED_GLASS_PANE)) return;

            if (event.getRawSlot() == 51) {
                open(player, 1);
            } else if (event.getRawSlot() == 47) {
                open(player, -1);

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

    private int getAmount(ItemStack stack) {
        ItemMeta meta = stack.getItemMeta();
        return StringUtils.parse(meta.getLore().get(meta.getLore().size() - 1));
    }

    private void addItem(ItemStack stack, Inventory inventory) {

        if (stack.getType() == Material.AIR) return;


        //Searches through all of the items in the inventory to check if it contains a type
        //If it finds the item, increment the amount
        if (inventory.contains(stack.getType())) {
            for (ItemStack item : inventory.getContents()) {
                if (item == null) continue;


                if (item.getType().equals(stack.getType())) {
                    CustomItemStack c = new CustomItemStack(item);
                    String lore = ChatColor.RED + "Amount: " + (Integer.parseInt(c.getLore(0).replaceAll("[\\D]", "")) + stack.getAmount());

                    c.lore(c.getItemMeta().getLore().size() - 1, lore);


                    if (item.getAmount() < 64) {
                        if (item.getAmount() + stack.getAmount() > 64) {
                            item.setAmount(64);
                        } else {
                            item.setAmount(item.getAmount() + stack.getAmount());
                        }

                    }

                    item.setItemMeta(c.getItemMeta());
                    break;
                }
            }
            //If it doesn't contain the material, add it onto the inventory
        } else {


            CustomItemStack stack1 = new CustomItemStack(stack).lore(ChatColor.RED + "Amount: " + stack.getAmount());
            inventory.addItem(stack1);


        }
    }

    private void open(Player player, int increment) {
        InventoryStorage.pageNumber.put(player.getUniqueId(), InventoryStorage.pageNumber.get(player.getUniqueId()) + increment);
        storage.open(player);
    }


}
