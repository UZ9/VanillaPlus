package com.yerti.vanillaplus.machines

import com.yerti.core.block.storage.SQLPersistentBlockStorage
import com.yerti.vanillaplus.types.Persistent
import com.yerti.vanillaplus.types.Tickable
import org.bukkit.Location
import org.bukkit.inventory.ItemStack


abstract class Machine(var location: Location) : Persistent, Tickable {

    abstract fun init()

    abstract fun getItem() : ItemStack

}