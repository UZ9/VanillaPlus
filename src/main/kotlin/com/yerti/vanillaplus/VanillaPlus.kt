package com.yerti.vanillaplus

import com.yerti.core.YertiPlugin
import com.yerti.vanillaplus.commands.VPCommands

class VanillaPlus : YertiPlugin() {

    override fun onPluginEnable() {
        commandClass = VPCommands::class.java
    }

    override fun onPluginDisable() {

    }

}