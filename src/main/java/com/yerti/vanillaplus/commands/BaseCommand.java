package com.yerti.vanillaplus.commands;

import com.yerti.vanillaplus.VanillaPlus;
import com.yerti.vanillaplus.core.command.CustomCommand;
import com.yerti.vanillaplus.core.command.SubCommand;
import com.yerti.vanillaplus.core.inventories.AnvilGUI;
import com.yerti.vanillaplus.core.items.CustomItemStack;
import com.yerti.vanillaplus.core.items.ItemMetaData;
import com.yerti.vanillaplus.config.Messages;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BaseCommand {

    @CustomCommand(name = "vanillaplus", permission = "", aliases = {"vp"}, description = "Base command for VanillaPlus", usage = "/vanillaplus")
    public void baseCommand(CommandSender executor, Command command, String[] args) {
        for (Entity entity : ((Player) executor).getWorld().getEntities()) {
            if (entity instanceof ArmorStand) {
                if (entity.getCustomName().equals("CustomModelPart")) entity.remove();

            }
        }
    }

    @SubCommand(parent = "vanillaplus", name = "wrench", permission = "", usage = "/vanillaplus wrench", description = "Gives you a wrench")
    public void wrenchSub(CommandSender executor, Command command, String[] args) {
        if (!(executor instanceof Player)) return;

        ItemStack stack = new CustomItemStack(Material.GOLD_HOE, 1).name(ChatColor.translateAlternateColorCodes('&', Messages.config.getTranslation("wrench-name")));

        stack = ItemMetaData.setMetadata(stack, "current-setting", 0);


        ((Player) executor).getInventory().addItem(stack);

    }

    @SubCommand(parent = "vanillaplus", name = "test", permission = "", usage = "/vanillaplus test", description = "test")
    public void test(CommandSender sender, Command command,  String[] args) {
        new AnvilGUI.Builder()
                .preventClose()
                .onClose(player -> player.sendMessage("Success"))
                .onComplete((player, text) -> AnvilGUI.Response.close())

                .plugin(VanillaPlus.getInstance())
                .text("Search Items")
                .open((Player) sender);
    }

}
