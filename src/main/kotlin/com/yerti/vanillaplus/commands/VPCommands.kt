package com.yerti.vanillaplus.commands

import com.yerti.core.YertiPlugin
import com.yerti.core.command.CommandHandler
import com.yerti.core.command.CustomCommand
import com.yerti.core.menus.Pagination
import com.yerti.core.utils.ChatUtils
import com.yerti.vanillaplus.utils.isInt
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import javax.print.attribute.IntegerSyntax

class VPCommands {

    @CustomCommand(name = "vanillaplus", permission = "", aliases = ["vp"], description = "Main command for VanillaPlus", usage = "/vanillaplus")
    fun baseCommand(plugin: YertiPlugin?, sender: CommandSender?, command: Command?, args: Array<String?>?) {
        if (sender is Player) {
            if (args!!.isEmpty()) {
                displayCommands(sender, plugin)
            } else if (args.size == 2 && args[0].equals("help", true)) {
                val page = args[1]!!

                if (page.isInt()) {
                    displayCommands(sender, plugin, page.toInt())
                } else {
                    sender.sendMessage(ChatUtils.style("&cYour second argument needs to be a number!"))
                }

            } else {
                sender.sendMessage("&cUnknown subcommand for /vanillaplus!")
            }


        }
    }


    private fun displayCommands(player: Player, plugin: YertiPlugin?, page: Int = 0) {
        val commands = CommandHandler.getCommands(plugin).values.toList()

        val pagination = Pagination(8, commands)

        player.sendMessage("Page ${page}/${pagination.totalPages()}")

        player.sendMessage(ChatUtils.style("&c&m---------------------------"))

        pagination.getPage(page).forEach {
            player.sendMessage("&c${it.name}: &7${it.description}")
        }

        player.sendMessage(ChatUtils.style("&c&m---------------------------"))

    }


}
