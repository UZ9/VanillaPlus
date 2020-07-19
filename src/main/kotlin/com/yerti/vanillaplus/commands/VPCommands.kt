package com.yerti.vanillaplus.commands

import com.yerti.core.YertiPlugin
import com.yerti.core.command.CustomCommand
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class VPCommands {
    @CustomCommand(name = "vanillaplus", permission = "", aliases = ["vp"], description = "Main command for VanillaPlus", usage = "/vanillaplus")
    fun baseCommand(plugin: YertiPlugin?, sender: CommandSender?, command: Command?, args: Array<String?>?) {
        if (sender is Player) {
        }
    }
}