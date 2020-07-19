package com.yerti.vanillaplus.types

import com.yerti.core.block.storage.SQLPersistentBlockStorage

interface Persistent {
    fun load(persistentStorage : SQLPersistentBlockStorage)

    fun save(persistentStorage : SQLPersistentBlockStorage)
}