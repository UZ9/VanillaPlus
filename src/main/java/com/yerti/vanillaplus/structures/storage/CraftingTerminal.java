package com.yerti.vanillaplus.structures.storage;

import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.yerti.vanillaplus.VanillaPlus;
import com.yerti.vanillaplus.core.entity.CustomModel;
import com.yerti.vanillaplus.core.entity.CustomModelPart;
import com.yerti.vanillaplus.core.inventories.CustomInventory;
import com.yerti.vanillaplus.structures.Structure;
import com.yerti.vanillaplus.utils.BlockUpdater;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Furnace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;
import java.util.Collection;

public class CraftingTerminal extends Structure {
    CustomModel model = new CustomModel();



    public CraftingTerminal(Location loc) {
        super(loc, "CRAFTING_TERMINAL", 10000);

        create();

    }

    public CraftingTerminal(Location loc, String type, Integer maxEnergy, Integer energy) {
        super(loc, type, maxEnergy, energy);

        setHologram(HologramsAPI.createHologram(VanillaPlus.getInstance(), new Location(getLoc().getWorld(), getLoc().getX() + 0.5, getLoc().getY() + 2, getLoc().getZ() + 0.5)));
        line = getHologram().appendTextLine(ChatColor.RED + "" + new DecimalFormat("##.#").format(getEnergy() / 1000.) + "/" + new DecimalFormat("##.#").format(getMaxEnergy() / 1000.) + "k VU");
        BlockUpdater.machines.put(loc, this);
    }



    @Override
    public void create() {

        BlockUpdater.machines.put(getLoc(), this);
        setHologram(HologramsAPI.createHologram(VanillaPlus.getInstance(), new Location(getLoc().getWorld(), getLoc().getX() + 0.5, getLoc().getY() + 2, getLoc().getZ() + 0.5)));
        line = getHologram().appendTextLine(ChatColor.RED + "" + new DecimalFormat("##.#").format(getEnergy() / 1000.) + "k/" + new DecimalFormat("##.#").format(getMaxEnergy() / 1000.) + "k VU");

        model.addArmorStand(new CustomModelPart(getLoc().clone().add(0.5, 0.25, 0.5)).material(new ItemStack(Material.SEA_LANTERN)).small(true).gravity(false));
        model.addArmorStand(new CustomModelPart(getLoc().clone().add(0.5, -0.5, 0.5)).material(new ItemStack(Material.GLASS)).gravity(false));


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
        VanillaPlus.getMachineSaver().removeMachine(this);


    }

    @Override
    public void update() {
        if (getEnergy() != 0) {
            setEnergy(getEnergy() - 1);
        }

        line.setText(ChatColor.RED + "" + new DecimalFormat("##.#").format(getEnergy() / 1000.) + "k/" + new DecimalFormat("##.#").format(getMaxEnergy() / 1000.) + "k VU");



    }
}
