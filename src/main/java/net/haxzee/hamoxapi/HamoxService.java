package net.haxzee.hamoxapi;

import net.haxzee.hamoxapi.databaseutils.MySQLDatabaseDriver;
import org.bukkit.plugin.java.JavaPlugin;

public class HamoxService extends JavaPlugin {
    private static HamoxService instance;

    private MySQLDatabaseDriver mySQLDatabaseDriver;

    @Override
    public void onEnable() {
        instance = this;
        this.mySQLDatabaseDriver = new MySQLDatabaseDriver();
    }

    @Override
    public void onDisable() {

    }

    public static HamoxService getInstance() {
        return instance;
    }

    public MySQLDatabaseDriver getMySQLDatabaseDriver() {
        return mySQLDatabaseDriver;
    }
}
