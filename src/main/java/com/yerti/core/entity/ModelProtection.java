package com.yerti.core.entity;

import org.bukkit.ChatColor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class ModelProtection implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof ArmorStand) {
            ArmorStand stand = (ArmorStand) event.getEntity();
            if (stand.getCustomName().equals("CustomModelPart")) {
                System.out.println("Cancelling");
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEntityInteract(PlayerInteractAtEntityEvent event) {
        if (event.getRightClicked() instanceof ArmorStand) {
            ArmorStand stand = (ArmorStand) event.getRightClicked();
            if (stand.getCustomName().equals("CustomModelPart")) {

                event.setCancelled(true);
            }
        }
    }


}
