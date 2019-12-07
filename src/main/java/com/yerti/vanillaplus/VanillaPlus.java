package com.yerti.vanillaplus;

import com.yerti.vanillaplus.core.YertiPlugin;
import com.yerti.vanillaplus.core.entity.ModelProtection;
import com.yerti.vanillaplus.core.items.CustomItemStack;
import com.yerti.vanillaplus.core.recipe.CustomRecipe;
import com.yerti.vanillaplus.commands.BaseCommand;
import com.yerti.vanillaplus.data.MachineSaver;
import com.yerti.vanillaplus.energy.EnergyListener;
import com.yerti.vanillaplus.items.ItemList;
import com.yerti.vanillaplus.listeners.MachinePlaceListener;
import com.yerti.vanillaplus.listeners.PanelListener;
import com.yerti.vanillaplus.structures.Structure;
import com.yerti.vanillaplus.utils.BlockUpdater;
import com.yerti.vanillaplus.utils.config.Config;
import com.yerti.vanillaplus.utils.config.GeneratorList;
import com.yerti.vanillaplus.utils.config.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;


import java.io.File;
public class VanillaPlus extends YertiPlugin {

    public static GeneratorList customConfig;
    public static File customConfigFile;

    public static VanillaPlus instance;

    public MachineSaver machineSaver;

    public void onEnable() {


        Bukkit.getServer().getPluginManager().registerEvents(new ModelProtection(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new MachinePlaceListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PanelListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new EnergyListener(), this);
        load(BaseCommand.class);

        instance = this;

        //Holographic Displays hook
        //TODO: Find API version of Holographic Displays to reduce space taken
        if (!Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
            getLogger().severe("*** HolographicDisplays is not installed or not enabled. ***");
            getLogger().severe("*** This plugin will be disabled. ***");
            this.setEnabled(false);
            return;
        }

        //Custom generator for Machines stored in machines.yml
        customConfig = new GeneratorList(this);

        //Messages for config.yml
        Messages messages = new Messages(this);

        //Initiating events & config
        new Config(this);

        machineSaver = new MachineSaver();

        //Initiating custom config data file (for saving)
        customConfigFile = new File(getDataFolder() + "/machines.yml");

        //Initialize messages and write to config
        messages.init();

        //Initialize Block Updater (Updating for machines)
        BlockUpdater bu = new BlockUpdater(this);

        Bukkit.getScheduler().runTaskLater(this, () -> {
            CustomRecipe genCore = new CustomRecipe(new CustomItemStack(Material.FIREBALL, 1)
                    .name("&eGenerator Core")
                    .lore("&cCrafting component for generators")
                    .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                    .addFlag(ItemFlag.HIDE_DESTROYS)
                    .addFlag(ItemFlag.HIDE_ENCHANTS)
                    .damage(0)
                    .enchant(Enchantment.ARROW_FIRE, 1));

            genCore.shape("%%%", "%@%", "%%%");
            genCore.setIngredient('%', new ItemStack(Material.IRON_INGOT));
            genCore.setIngredient('@', new ItemStack(Material.REDSTONE_BLOCK));
            getServer().addRecipe(genCore.build());

            CustomRecipe coalGen = new CustomRecipe(ItemList.COAL_GENERATOR);


            coalGen.shape("%t%", "q@q", "efe");
            coalGen.setIngredient('%', new ItemStack(Material.IRON_INGOT));
            coalGen.setIngredient('t', new ItemStack(Material.GLASS));
            coalGen.setIngredient('q', new ItemStack(Material.IRON_BLOCK));

            //coalGen.setIngredient('@', new ItemStack(Material.FIREBALL));
            coalGen.setIngredient('@', ItemList.GENERATOR_CORE);
            coalGen.setIngredient('e', new ItemStack(Material.REDSTONE));
            coalGen.setIngredient('f', new ItemStack(Material.REDSTONE_BLOCK));
            getServer().addRecipe(coalGen.build());

            CustomRecipe terminal = new CustomRecipe(ItemList.CRAFTING_TERMINAL);

            terminal.shape("%@%", "*#*", "%*%");
            terminal.setIngredient('%', new ItemStack(Material.REDSTONE));
            terminal.setIngredient('@', new ItemStack(Material.NETHER_STAR));
            terminal.setIngredient('*', new ItemStack(Material.IRON_BLOCK));
            terminal.setIngredient('#', new ItemStack(Material.SEA_LANTERN));
            getServer().addRecipe(terminal.build());



            //Main update loop
            bu.gameLoop();


        }, 20L);





    }

    @Override
    public void onDisable() {
        for (Structure structure : BlockUpdater.machines.values()) {
            machineSaver.saveMachineSync(structure);

            //MachineUtils.updateMachineConfig(structure.getType(), structure.getMachineID(), structure.getEnergy());
        }

        machineSaver.close();
    }



}
