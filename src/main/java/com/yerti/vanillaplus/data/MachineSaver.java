package com.yerti.vanillaplus.data;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yerti.vanillaplus.VanillaPlus;
import com.yerti.vanillaplus.core.inventories.CustomInventory;
import com.yerti.vanillaplus.core.items.ItemStackUtils;
import com.yerti.vanillaplus.core.utils.LocationUtils;
import com.yerti.vanillaplus.structures.Structure;
import com.yerti.vanillaplus.structures.StructureType;
import com.yerti.vanillaplus.structures.storage.CraftingTerminal;
import com.yerti.vanillaplus.structures.storage.gui.InventoryStorageHolder;
import com.yerti.vanillaplus.utils.BlockUpdater;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class MachineSaver {

    private Connection connection;


    public MachineSaver() {

        try {
            Class.forName("org.sqlite.JDBC");

            final File file = new File(VanillaPlus.getInstance().getDataFolder(), "machines.sqlite");

            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdir();
            }

            this.connection = DriverManager.getConnection("jdbc:sqlite:" + file.getAbsolutePath());

            final Statement statement = this.connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS machines(world varchar(255), location varchar(255), vu decimal, type tinytext);");
            statement.execute("CREATE TABLE IF NOT EXISTS storageinventories(world varchar(255), location varchar(255), data longtext);");
            statement.close();


        } catch (Exception e) {
            e.printStackTrace();
            ;
        }

        Bukkit.getScheduler().runTaskTimer(VanillaPlus.getInstance(), () -> {
            for (Structure structure : BlockUpdater.machines.values()) {
                saveMachineAsync(structure);
            }
        }, 0L, 20L * 60L * 5L);

    }

    public void saveMachineAsync(Structure structure) {

        Bukkit.getScheduler().runTaskAsynchronously(VanillaPlus.getInstance(), () -> {
            saveMachineSync(structure);
        });


    }

    public void saveMachineSync(Structure structure) {
        String world = structure.getLoc().getWorld().getName();
        Location location = structure.getLoc();


        if (location == null) return;


        try {


            try {
                Statement c = MachineSaver.this.connection.createStatement();


                ResultSet test = c.executeQuery("select * from machines where world = '" + world + "' and location  = '" + serializeLocation(location) + "'");

                if (test.next()) {
                    test.close();

                    c.executeUpdate("update machines set vu = " + structure.getEnergy() + " where world = '" + world + "' and location = '" + serializeLocation(location) + "';");

                } else {
                    test.close();

                    c.executeUpdate("insert into machines(world, location, vu, type) values ('" + world + "', '" + serializeLocation(location) + "', " + structure.getEnergy() + ", '" + structure.getType() + "')");

                }

                c.close();


            } catch (Exception e) {
                e.printStackTrace();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeMachine(Structure structure) {
        Bukkit.getScheduler().runTaskAsynchronously(VanillaPlus.getInstance(), () -> {
            try {
                Statement statement = MachineSaver.this.connection.createStatement();
                statement.execute("DELETE FROM 'machines' WHERE world = '" + structure.getLoc().getWorld().getName() + "' AND location = '" + serializeLocation(structure.getLoc()) + "'");
                statement.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void getStructures() {

        try {

            Statement statement = MachineSaver.this.connection.createStatement();
            ResultSet set = statement.executeQuery("select * from machines");

            while (set.next()) {

                World world = Bukkit.getWorld(set.getString(1));
                String[] splitLocation = set.getString(2).split(";;");

                Location location = new Location(world, Double.parseDouble(splitLocation[0]), Double.parseDouble(splitLocation[1]), Double.parseDouble(splitLocation[2]));

                int energy = set.getInt(3);
                String type = set.getString(4);

                if (location == null) System.out.println("location null");
                if (type == null) System.out.println("type null");


                System.out.println("Currently working on type " + type);
                StructureType.valueOf(type.toUpperCase()).getStructureParent().getConstructor(Location.class, String.class, Integer.class, Integer.class).newInstance(location, type, 10000, energy);




            }


            set.close();
            statement.close();


        } catch (Exception e) {

            System.out.println("Invocation. Cause: " + e.getCause());

            e.printStackTrace();
        }


    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private String serializeLocation(Location location) {
        return (location.getX() + ";;" + location.getY() + ";;" + location.getZ());
    }

    public void savePanel(CraftingTerminal terminal) {
        JsonParser parser = new JsonParser();
        StringBuilder data = new StringBuilder();
        for (CustomInventory inventory : terminal.getInventoryStorage().getInventories()) {
            data.append("[");
            int j = 0;
            for (int i = 0; i < inventory.getInventory().getSize() - 9; i++) {
                if (inventory.getInventory().getItem(i) == null) continue;

                if (j != 0) {
                    data.append(";;");
                }

                JsonObject jsonObject = ItemStackUtils.serializeItemStack(inventory.getInventory().getItem(i));
                jsonObject.addProperty("slot", i);
                data.append(jsonObject.toString());
                j++;
            }

            data.append("];");
        }

        try {
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery("select world from storageinventories where world = '" + terminal.getLoc().getWorld().getName() + "' and location = '" + LocationUtils.serializeLocation(terminal.getLoc()) + "';");

            if (set.next()) {
                statement.execute("update storageinventories set data = '" + data.toString() + "' where world = '" + terminal.getLoc().getWorld().getName() + "' and location = '" + LocationUtils.serializeLocation(terminal.getLoc()) + "';");
            } else {
                statement.execute("insert into storageinventories(world, location, data) values ('" + terminal.getLoc().getWorld().getName() + "', '" + LocationUtils.serializeLocation(terminal.getLoc()) + "', '" + data.toString() + "');");
            }
            set.close();

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<CustomInventory> getPanel(CraftingTerminal terminal) {

        List<CustomInventory> inventories = new ArrayList<>();

        JsonParser parser = new JsonParser();


        final String worldName = terminal.getLoc().getWorld().getName();
        final Location location = terminal.getLoc();
        try {
            final Statement st = connection.createStatement();
            System.out.println("STARTING RESULT QUREY");
            final ResultSet set = st.executeQuery("select data from storageinventories where world='" + worldName + "' and location='" + LocationUtils.serializeLocation(terminal.getLoc()) + "';");
            if (set.next()) {

                System.out.println("FOUND RESULTSET QUERY");

                final String data = set.getString("data");
                int i = 0;
                for (final String page : data.split("];")) {
                    CustomInventory nPage = new CustomInventory(new InventoryStorageHolder(), 54, "&cCrafting Terminal", new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15));

                    final String substring = page.substring(1);
                    if (substring.contains(";;")) {
                        for (final String itemSerialized : substring.split(";;")) {
                            final JsonObject json = (JsonObject) parser.parse(itemSerialized);
                            final int slot = json.get("slot").getAsInt();
                            final ItemStack item = ItemStackUtils.deserializeItemStack(itemSerialized);
                            nPage.getInventory().setItem(slot, item);
                        }
                    } else {
                        final JsonObject json2 = (JsonObject) parser.parse(substring);
                        final int slot2 = json2.get("slot").getAsInt();
                        final ItemStack item2 = ItemStackUtils.deserializeItemStack(substring);
                        nPage.getInventory().setItem(slot2, item2);
                    }

                    inventories.add(nPage);


                    ++i;

                }
            }
            set.close();
            st.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return inventories;


    }

}
