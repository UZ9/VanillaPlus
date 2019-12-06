package com.yerti.vanillaplus.events;

import com.yerti.vanillaplus.structures.Structure;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class StructureDestroyEvent extends Event {
    private Player player;
    private static final HandlerList handlers = new HandlerList();
    private Structure structure;

    public StructureDestroyEvent(Player player, Structure structure) {
        this.player = player;
        this.structure = structure;
    }

    public Player getPlayer() {
        return player;
    }

    public Structure getStructure() {
        return structure;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
