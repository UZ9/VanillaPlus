package com.yerti.vanillaplus.structures.machines;

import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.yerti.vanillaplus.VanillaPlus;
import com.yerti.vanillaplus.core.block.BlockUtils;
import com.yerti.vanillaplus.core.items.ItemStackUtils;
import com.yerti.vanillaplus.core.utils.ChatUtils;
import com.yerti.vanillaplus.core.utils.InventoryUtils;
import com.yerti.vanillaplus.structures.Structure;
import com.yerti.vanillaplus.utils.BlockUpdater;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

public class QuarryController extends Structure {

    private Queue<Location> blockQueue = new LinkedList<>();

    public QuarryController(Location loc) {
        super(loc, "QUARRY_CONTROLLER", 100000);

        int holeWidth = 5;
        int holeLength = 5;

        Location finalLoc = loc.clone().add(0, -1, 1);
        Bukkit.getScheduler().runTaskAsynchronously(VanillaPlus.getInstance(), () -> {

            System.out.println(ChatUtils.translate("&cLocation is " + finalLoc.getX() + ";" + finalLoc.getY() + ";" + finalLoc.getZ()));


            for (int y = 0; y < finalLoc.getY(); y++) {
                for (int x = 0; x < holeWidth; x++) {
                    for (int z = 0; z < holeLength; z++) {

                        blockQueue.add(finalLoc.clone().add(x, -y, z));

                    }
                }
            }
        });


        create();
    }

    public QuarryController(Location loc, String type, Integer maxEnergy, Integer energy) {
        super(loc, type, maxEnergy, energy);

        setHologram(HologramsAPI.createHologram(VanillaPlus.getInstance(), new Location(getLoc().getWorld(), getLoc().getX() + 0.5, getLoc().getY() + 2, getLoc().getZ() + 0.5)));
        line = getHologram().appendTextLine(ChatColor.RED + "" + new DecimalFormat("##.#").format(getEnergy() / 1000.) + "/" + new DecimalFormat("##.#").format(getMaxEnergy() / 1000.) + "k VU");
        BlockUpdater.machines.put(loc, this);

    }

    @Override
    public void update() {


        if (getEnergy() > 100) {
            removeEnergy(10);

            if (blockQueue.isEmpty()) {
                return;
            }

            assert blockQueue.peek() != null;

            if (blockQueue.peek().getBlock().getType() != Material.BEDROCK && !BlockUpdater.machines.containsKey(blockQueue.peek())) {

                Bukkit.broadcastMessage("Destroyed " + blockQueue.peek().getBlock().getType() + " at " + blockQueue.peek());
                assert blockQueue.peek() != null;

                Dispenser dispenser = (Dispenser) getLoc().getBlock().getState();
                for (ItemStack stack : blockQueue.peek().getBlock().getDrops()) {
                    if (stack != null) {
                        if (!InventoryUtils.hasInventorySpace(dispenser.getInventory(), stack)) {
                            getLoc().getWorld().dropItem(getLoc().clone().add(0, 1, 0), stack);
                        } else {
                            dispenser.getInventory().addItem(stack);
                        }
                    }

                }


                blockQueue.peek().getBlock().setType(Material.AIR);
                blockQueue.remove();
            }




        }

        line.setText(ChatColor.RED + "" + new DecimalFormat("##.#").format(getEnergy() / 1000.) + "k/" + new DecimalFormat("##.#").format(getMaxEnergy() / 1000.) + "k VU");

    }

    @Override
    public void create() {
        BlockUpdater.machines.put(getLoc(), this);
        setHologram(HologramsAPI.createHologram(VanillaPlus.getInstance(), new Location(getLoc().getWorld(), getLoc().getX() + 0.5, getLoc().getY() + 2, getLoc().getZ() + 0.5)));
        line = getHologram().appendTextLine(ChatColor.RED + "" + new DecimalFormat("##.#").format(getEnergy() / 1000.) + "k/" + new DecimalFormat("##.#").format(getMaxEnergy() / 1000.) + "k VU");

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

        VanillaPlus.getMachineSaver().removeMachine(this);
    }


}
