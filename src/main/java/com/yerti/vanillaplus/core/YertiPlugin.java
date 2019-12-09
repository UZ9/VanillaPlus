package com.yerti.vanillaplus.core;


import com.yerti.vanillaplus.core.command.CommandFramework;
import com.yerti.vanillaplus.core.enchantmenets.CombatEnchant;
import com.yerti.vanillaplus.core.enchantmenets.EnchantmentHandler;
import com.yerti.vanillaplus.core.entity.ModelProtection;
import com.yerti.vanillaplus.core.recipe.CustomRecipeHandler;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Class for loading any additional resources with only calling one method
 * Main class extends YertiPlugin (which then extends JavaPlugin
 */
public class YertiPlugin extends JavaPlugin {

    private static Plugin hookedPlugin;

    /**
     * Loads needed resources
     */
    protected void load(Class<?> commandClass) {
        new CommandFramework(this, commandClass);
        getServer().getPluginManager().registerEvents(new ModelProtection(), this);
        getServer().getPluginManager().registerEvents(new CustomRecipeHandler(), this);
        getServer().getPluginManager().registerEvents(new EnchantmentHandler(), this);

        new CombatEnchant("Flaming Keg", 40, 40, 40, e -> {
           Entity victim = e.getEntity();
           victim.getWorld().playEffect(victim.getLocation(), Effect.MOBSPAWNER_FLAMES, 1, 1);
           victim.setFireTicks(100);
           victim.getWorld().createExplosion(victim.getLocation(), 1, false);
        });
    }

    protected void load() {
        getServer().getPluginManager().registerEvents(new ModelProtection(), this);
    }

    public static Plugin getHookedPlugin() {
        return hookedPlugin;
    }

    public static void addHookedPlugin(Plugin plugin) {
        hookedPlugin = plugin;
    }

}
