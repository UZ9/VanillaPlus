package com.yerti.vanillaplus.core.particles;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ParticleManager {

    public static void sendColorParticle(Location location,  int r, int g, int b) {
        PacketPlayOutWorldParticles particles = new PacketPlayOutWorldParticles(EnumParticle.FLAME, true, (float) location.getX(), (float) location.getY(), (float) location.getZ(), r, g, b, (float) 255,0, 10);

        for (Player player : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(particles);
        }

    }



}
