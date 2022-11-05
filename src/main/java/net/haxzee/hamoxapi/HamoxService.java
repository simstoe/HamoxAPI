package net.haxzee.hamoxapi;

import net.haxzee.hamoxapi.databaseutils.SQLConnector;
import org.bukkit.plugin.java.JavaPlugin;

public class HamoxService extends JavaPlugin {
    private static HamoxService instance;

    private static SQLConnector sqlConnector;

    @Override
    public void onEnable() {
        instance = this;
    }

    @Override
    public void onDisable() {

    }

    public static HamoxService getInstance() {
        return instance;
    }

    public static SQLConnector getSqlConnector() {
        return sqlConnector;
    }
}
