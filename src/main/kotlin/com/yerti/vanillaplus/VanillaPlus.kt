package com.yerti.vanillaplus

import com.yerti.core.YertiPlugin
import com.yerti.core.block.storage.SQLPersistentBlockStorage
import com.yerti.core.database.sql.types.sqlite.SQLiteInfo
import com.yerti.core.database.sql.types.sqlite.SQLiteManager
import com.yerti.vanillaplus.commands.VPCommands
import com.yerti.vanillaplus.machines.Machine

class VanillaPlus : YertiPlugin() {

    companion object {
        val instance = this
    }

    override fun onPluginEnable() {
        registerCommandClass(VPCommands::class.java)

        val sqlManager = SQLiteManager(this, SQLiteInfo("db", "user", "pass"), false)
        val data = SQLPersistentBlockStorage(sqlManager)

        val machines : List<Machine> = listOf()

        machines.forEach { it.save(data) }


    }

    override fun onPluginDisable() {

    }



}