package com.yerti.vanillaplus.items

import com.yerti.core.items.ItemStackBuilder
import org.bukkit.Material
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

object VPItems {



    /* Tools */
    val WRENCH: ItemStack = ItemStackBuilder(Material.GOLD_HOE)
            .addFlag(ItemFlag.HIDE_ENCHANTS)
            .addFlag(ItemFlag.HIDE_ATTRIBUTES)
            .name("&6Wrench")
            .lore("")
            .lore("&6Used for managing machines.")


    /* Machines */
    val COAL_GENERATOR: ItemStack = ItemStackBuilder(Material.FURNACE)
            .addFlag(ItemFlag.HIDE_ENCHANTS)
            .addFlag(ItemFlag.HIDE_ATTRIBUTES)
            .name("&6Coal Generator")
            .glow()
            .lore("")
            .lore("&6Consumes Coal to generate VU.")
}