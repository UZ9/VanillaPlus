package com.yerti.vanillaplus.config;

import com.yerti.vanillaplus.core.utils.ChatUtils;
import org.bukkit.entity.Player;

import java.util.Arrays;

public enum Lang {

    NO_PERMISSION("&cYou don't have permission to do that.");

    String message;

    Lang(String... message) {
        this.message = String.join("\n", message);
    }

    public void send(Player player, Object... param) {
        String m = message;

        for (int i = 0; i < param.length; i++) {
            m = m.replace("{" + i + "}", param[i].toString());
        }

        player.sendMessage(ChatUtils.translate(m));

    }


}
