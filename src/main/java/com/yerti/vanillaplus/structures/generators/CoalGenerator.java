package com.yerti.vanillaplus.structures.generators;

import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.yerti.vanillaplus.VanillaPlus;
import com.yerti.vanillaplus.core.block.BlockUtils;
import com.yerti.vanillaplus.core.entity.CustomModel;
import com.yerti.vanillaplus.core.entity.CustomModelPart;
import com.yerti.vanillaplus.structures.Structure;
import com.yerti.vanillaplus.utils.BlockUpdater;
import org.bukkit.*;
import org.bukkit.block.Furnace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.List;


public class CoalGenerator extends Generator {




    public CoalGenerator(Location loc) {
        super(loc, "COAL_GENERATOR", 10000);

        create();


    }

    public CoalGenerator(Location loc, String type, Integer maxEnergy, Integer energy) {
        super(loc, type, maxEnergy, energy);

        setHologram(HologramsAPI.createHologram(VanillaPlus.getInstance(), new Location(getLoc().getWorld(), getLoc().getX() + 0.5, getLoc().getY() + 2, getLoc().getZ() + 0.5)));
        line = getHologram().appendTextLine(ChatColor.RED + "" + new DecimalFormat("##.#").format(getEnergy() / 1000.) + "/" + new DecimalFormat("##.#").format(getMaxEnergy() / 1000.) + "k VU");
        BlockUpdater.machines.put(loc, this);


    }


    @Override
    public void create() {
        super.create();

        model.addArmorStand(new CustomModelPart(getLoc().clone().add(0.5, 0.25, 0.5)).material(new ItemStack(Material.REDSTONE_BLOCK)).small(true).gravity(false));
        model.addArmorStand(new CustomModelPart(getLoc().clone().add(0.5, -0.5, 0.5)).material(new ItemStack(Material.GLASS)).gravity(false));

    }

    @Override
    public void update() {


        if (getLoc().getBlock().getState() == null) return;
        if (!(getLoc().getBlock().getState() instanceof Furnace)) return;


        Furnace generator = (Furnace) getLoc().getBlock().getState();
        FurnaceInventory inventory = generator.getInventory();

        if (inventory.getFuel() == null) {
            generator.setBurnTime((short) 0);
        } else if (inventory.getFuel().getType().equals(Material.COAL) || inventory.getFuel().getType().equals(Material.COAL_BLOCK)) {


            if (getEnergy() < getMaxEnergy() - 1000) {

                int energyAmount = inventory.getFuel().getType().equals(Material.COAL) ? 1 : 9;


                generator.setBurnTime((short) (generator.getBurnTime() + (short) 100));

                getLoc().getWorld().playEffect(getLoc(), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);


                addEnergy((100 * energyAmount));


                if (getEnergy() > getMaxEnergy()) setEnergy(getMaxEnergy());

                if (generator.getInventory().getSmelting() != null) {
                    generator.getInventory().setSmelting(null);
                }


                if (inventory.getFuel().getAmount() == 1) {
                    inventory.setFuel(new ItemStack(Material.AIR));
                } else {
                    generator.getInventory().getFuel().setAmount(generator.getInventory().getFuel().getAmount() - 1);
                }



            }


        }

        //Super method for handling energy output to other machines
        handleEnergy();



        //Update hologram
        line.setText(ChatColor.RED + "" + new DecimalFormat("##.#").format(getEnergy() / 1000.) + "k/" + new DecimalFormat("##.#").format(getMaxEnergy() / 1000.) + "k VU");

    }

}
