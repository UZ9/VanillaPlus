package com.yerti.vanillaplus.data;

import com.google.gson.JsonParser;
import com.yerti.vanillaplus.VanillaPlus;
import com.yerti.vanillaplus.structures.Structure;
import com.yerti.vanillaplus.structures.StructureType;
import com.yerti.vanillaplus.utils.BlockUpdater;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.File;
import java.sql.*;


public class MachineSaver {

    private Connection connection;


    public MachineSaver() {

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

        Bukkit.getScheduler().runTaskTimer(VanillaPlus.instance, () -> {
            for (Structure structure : BlockUpdater.machines.values()) {
                saveMachineAsync(structure);
            }
        }, 0L, 20L * 60L * 5L);

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

        if (location == null) System.out.println("wackkkkk");


        try {
            System.out.println("results: for l a m p");

            if (MachineSaver.this.connection == null) reopen();
            Statement c = MachineSaver.this.connection.createStatement();
            ResultSet test = c.executeQuery("select * from machines where world = '" + world + "' and location  = '" + serializeLocation(location) + "'");
            connection.close();


            if (test.next()) {
                test.close();
                c.close();

                reopen();
                Statement statement = MachineSaver.this.connection.createStatement();
                statement.executeUpdate("update machines set vu = " + structure.getEnergy() + " where world = '" + world + "' and location = '" + serializeLocation(location)  + "';");
            } else {
                test.close();
                reopen();
                Statement statement = MachineSaver.this.connection.createStatement();
                statement.executeUpdate("insert into machines(world, location, vu, type) values ('" + world + "', '" + serializeLocation(location) + "', " + structure.getEnergy() + ", '" + structure.getType()+ "')");

            }

            connection.close();




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

    //I don't know why but the sql kept randomly locking until I put this here, either I messed something up or it magically worked before
    private void reopen() {
        final File file = new File(VanillaPlus.instance.getDataFolder(), "machines.sqlite");

        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdir();
        }

        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + file.getAbsolutePath());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String serializeLocation(Location location) {
        return (location.getX() + ";;" + location.getY() + ";;" + location.getZ());
    }

}
