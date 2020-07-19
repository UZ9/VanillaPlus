package com.yerti.vanillaplus.machines.generator

import com.yerti.core.block.storage.SQLPersistentBlockStorage
import com.yerti.vanillaplus.machines.Machine
import org.bukkit.Location

abstract class Generator(location: Location) : Machine(location) {

    var power = 0

    final override fun save(persistentStorage: SQLPersistentBlockStorage) {
        persistentStorage.storeValue(location, "power", power)

        saveGen(persistentStorage)
    }

    final override fun load(persistentStorage: SQLPersistentBlockStorage) {
        this.power = persistentStorage.getValue(location, "power") as Int
    }

    abstract fun loadGen(persistentStorage: SQLPersistentBlockStorage)

    abstract fun saveGen(persistentStorage: SQLPersistentBlockStorage)
}


