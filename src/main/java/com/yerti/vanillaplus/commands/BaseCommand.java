package com.yerti.vanillaplus.commands;

import com.yerti.core.command.CustomCommand;
import com.yerti.core.command.SubCommand;
import com.yerti.core.items.ItemMetaData;
import com.yerti.vanillaplus.utils.config.Messages;
import com.yerti.vanillaplus.utils.inventory.CustomItemStack;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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

        ItemStack stack = new CustomItemStack(Material.GOLD_HOE, ChatColor.translateAlternateColorCodes('&', Messages.config.getTranslation("wrench-name")), 1).getStack();

        stack = ItemMetaData.setMetadata(stack, "current-setting", 0);


        ((Player) executor).getInventory().addItem(stack);

    }

}
