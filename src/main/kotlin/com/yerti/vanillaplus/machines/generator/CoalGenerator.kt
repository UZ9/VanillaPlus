package com.yerti.vanillaplus.machines.generator

import com.yerti.core.block.storage.SQLPersistentBlockStorage
import com.yerti.core.inventories.CustomInventory
import com.yerti.core.utils.InventoryUtils
import com.yerti.vanillaplus.VanillaPlus
import com.yerti.vanillaplus.items.VPItems
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class CoalGenerator(location: Location) : Generator(location) {

    private lateinit var inventory : CustomInventory

    override fun init() {
        inventory = CustomInventory(null, InventoryType.FURNACE, "&6Coal Generator")
    }

    override fun loadGen(persistentStorage: SQLPersistentBlockStorage) {
        inventory = CustomInventory(InventoryUtils.deserializeInventory(persistentStorage.getValue(location, "inventory") as String))
    }

    override fun saveGen(persistentStorage: SQLPersistentBlockStorage) {
        persistentStorage.storeValue(location, "inventory", InventoryUtils.serializeInventory(inventory.inventory))
    }

    override fun tick() {
        //do whatever ticking is required
    }

    override fun getItem(): ItemStack {
        return VPItems.COAL_GENERATOR
    }
}