package com.yerti.vanillaplus.data;

import com.google.gson.JsonParser;
import com.yerti.vanillaplus.VanillaPlus;
import com.yerti.vanillaplus.structures.Structure;
import com.yerti.vanillaplus.structures.StructureType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class MachineSaver {

    private Connection connection;
    private JsonParser parser;


    public MachineSaver() {
        this.parser = new JsonParser();

        try {
            Class.forName("org.sqlite.JDBC");

            final File file = new File(VanillaPlus.instance.getDataFolder(), "machines.sqlite");

            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdir();
            }

            this.connection = DriverManager.getConnection("jdbc:sqlite:" + file.getAbsolutePath());

            final Statement statement = this.connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS machines(world varchar(255), location varchar(255), vu decimal, type tinytext);");
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();;
        }

    }

    public void saveMachineAsync(Structure structure) {

        Bukkit.getScheduler().runTaskAsynchronously(VanillaPlus.instance, () -> {
            saveMachineSync(structure);
        });


    }

    public void saveMachineSync(Structure structure) {
        String world = structure.getLoc().getWorld().getName();
        Location location = structure.getLoc();

        System.out.println("ASDADWASDW");

        try {
            Statement statement = MachineSaver.this.connection.createStatement();
            ResultSet set = statement.executeQuery("SELECT world FROM machines where world = '" + world + "' and location = '" + serializeLocation(location) + "';");
            statement.close();

            Statement statement1 = MachineSaver.this.connection.createStatement();

            if (set.next()) {
                System.out.println("Yes exists");
                statement1.execute("update machines set vu = " + structure.getEnergy() + " where world = '" + world + "' and location = '" + serializeLocation(location)  + "';");
            } else {
                System.out.println("No exists");
                statement1.execute("insert into machines(world, location, vu, type) values ('" + world + "', '" + serializeLocation(location) + "', " + structure.getEnergy() + ", '" + structure.getType()+ "')");
            }

            statement1.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeMachine(Structure structure) {
        Bukkit.getScheduler().runTaskAsynchronously(VanillaPlus.instance, () -> {
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

               StructureType.valueOf(type.toUpperCase()).getStructureParent().getConstructor(Location.class, String.class, Integer.class, Integer.class).newInstance(location, type, 10000, energy);




            }


            statement.close();



        } catch (Exception e) {
            e.printStackTrace();
        }




    }

    private String serializeLocation(Location location) {
        return (location.getX() + ";;" + location.getY() + ";;" + location.getZ());
    }

}