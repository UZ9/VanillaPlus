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

    CustomModel model = new CustomModel();


    public CoalGenerator(Location loc) {
        super(loc, "COAL_GENERATOR", 10000);

        create();


    }

    public CoalGenerator(Location loc, String type, Integer maxEnergy, Integer energy) {
        super(loc, type, maxEnergy, energy);

        setHologram(HologramsAPI.createHologram(VanillaPlus.instance, new Location(getLoc().getWorld(), getLoc().getX() + 0.5, getLoc().getY() + 2, getLoc().getZ() + 0.5)));
        line = getHologram().appendTextLine(ChatColor.RED + "" + new DecimalFormat("##.#").format(getEnergy() / 1000.) + "/" + new DecimalFormat("##.#").format(getMaxEnergy() / 1000.) + "k VU");
        BlockUpdater.machines.put(loc, this);

        Bukkit.getScheduler().runTaskLaterAsynchronously(VanillaPlus.instance, () -> {

            List<Location> nearbyBlocks = BlockUtils.getBlocks(getLoc(), 5);
            for (Location location : nearbyBlocks) {
                if (BlockUpdater.machines.containsKey(location)) {
                    if (!(BlockUpdater.machines.get(location) instanceof Generator)) {
                        System.out.println("Found!");
                        addStructure(BlockUpdater.machines.get(location));
                    }
                }


            }
        }, 20L);
    }


    @Override
    public void create() {

        BlockUpdater.machines.put(getLoc(), this);
        setHologram(HologramsAPI.createHologram(VanillaPlus.instance, new Location(getLoc().getWorld(), getLoc().getX() + 0.5, getLoc().getY() + 2, getLoc().getZ() + 0.5)));
        line = getHologram().appendTextLine(ChatColor.RED + "" + new DecimalFormat("##.#").format(getEnergy() / 1000.) + "k/" + new DecimalFormat("##.#").format(getMaxEnergy() / 1000.) + "k VU");

        model.addArmorStand(new CustomModelPart(getLoc().clone().add(0.5, 0.25, 0.5)).material(new ItemStack(Material.REDSTONE_BLOCK)).small(true).gravity(false));
        model.addArmorStand(new CustomModelPart(getLoc().clone().add(0.5, -0.5, 0.5)).material(new ItemStack(Material.GLASS)).gravity(false));

        List<Location> nearbyBlocks = BlockUtils.getBlocks(getLoc(), 5);

        for (Location location : nearbyBlocks) {
            if (BlockUpdater.machines.containsKey(location)) {
                if (!(BlockUpdater.machines.get(location) instanceof Generator)) {
                    System.out.println("Found!");
                    addStructure(BlockUpdater.machines.get(location));
                }
            }


        }

    }

    @Override
    public void destroy() {
        getHologram().delete();
        BlockUpdater.machines.remove(getLoc());
        //MachineUtils.removeMachineConfig("coalGenerators", getMachineID());

        Collection<Entity> entities = getLoc().getWorld().getNearbyEntities(getLoc(), 1, 1, 1);

        for (Entity entity : entities) {
            if (entity instanceof ArmorStand) {
                if (entity.getCustomName().equals("CustomModelPart")) {
                    if (entity.getLocation().getX() == (getLoc().clone().add(0.5, 0, 0).getX()))
                        entity.remove();
                }
            }
        }

        model.destroy();
        VanillaPlus.instance.machineSaver.removeMachine(this);


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

            System.out.println("Energy: " + getEnergy());
            System.out.println("Max Energy: " + getMaxEnergy());

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

        handleEnergy();




        line.setText(ChatColor.RED + "" + new DecimalFormat("##.#").format(getEnergy() / 1000.) + "k/" + new DecimalFormat("##.#").format(getMaxEnergy() / 1000.) + "k VU");

    }

}
