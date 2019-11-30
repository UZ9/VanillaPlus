package com.yerti.vanillaplus.utils;


import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ChatManager {

    public static void blank(Player player, String message) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public static void success(Player player,  String message) {
        player.sendMessage(getPrefix() + " " + ChatColor.DARK_GRAY + "\u00BB " + ChatColor.GREEN + ChatColor.translateAlternateColorCodes('&', message));
    }

    public static void error(Player player,  String message) {
        player.sendMessage(getPrefix() + " " + ChatColor.DARK_GRAY + "\u00BB " + ChatColor.RED + ChatColor.translateAlternateColorCodes('&', message));
    }

    public static void warning(Player player,  String message) {
        player.sendMessage(getPrefix() + " " + ChatColor.DARK_GRAY + "\u00BB " + ChatColor.YELLOW + ChatColor.translateAlternateColorCodes('&', message));
    }

    public static void info(Player player,  String message) {
        player.sendMessage(getPrefix() + " " + ChatColor.DARK_GRAY + "\u00BB " + ChatColor.GRAY + ChatColor.translateAlternateColorCodes('&', message));
    }

    private static String getPrefix() {
        return ChatColor.translateAlternateColorCodes('&', "&7[&eIndustrialized&7]");
    }




}
