package com.yerti.vanillaplus.commands

import com.yerti.core.YertiPlugin
import com.yerti.core.command.CommandHandler
import com.yerti.core.command.CustomCommand
import com.yerti.core.menus.Pagination
import com.yerti.core.utils.ChatUtils
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class VPCommands {
    @CustomCommand(name = "vanillaplus", permission = "", aliases = ["vp"], description = "Main command for VanillaPlus", usage = "/vanillaplus")
    fun baseCommand(plugin: YertiPlugin?, sender: CommandSender?, command: Command?, args: Array<String?>?) {
        if (sender is Player) {






        }
    }


    private fun displayCommands(player : Player, plugin: YertiPlugin?) {
        var commands = CommandHandler.getCommands(plugin)

        val pagination = Pagination(8, commands)



        player.sendMessage("Page 1/${pagination.totalPages()}")

        pagination.getPage(1).forEach {
            player.sendMessage(ChatUtils.style("&c${it}"))
        }

        commands.forEach {

        }
    }
}