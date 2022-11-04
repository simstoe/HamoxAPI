package net.haxzee.hamoxapi.apiutils;

import net.haxzee.hamoxapi.databaseutils.SQLConnector;
import net.haxzee.hamoxapi.databaseutils.SQLFunctions;

import java.util.UUID;

public class StatsAPI {
    public void createTable(String table) {
        (new SQLFunctions()).createTable(table);
    }

    public void createPlayer(UUID uuid, String name, String table, String type) {
        (new SQLFunctions()).createPlayer(uuid, name, table, type);
    }

    public void addInt(UUID uuid, String name, String table, String type, int value) {
        (new SQLFunctions()).addInt(uuid, name, table, type, value);
    }

    public void removeInt(UUID uuid, String name, String table, String type, int value) {
        (new SQLFunctions()).removeInt(uuid, name, table, type, value);
    }

    public void setInt(UUID uuid, String table, String type, int value) {
        (new SQLFunctions()).setInt(uuid, table, type, value);
    }

    public Integer getInt(UUID uuid, String table, String type) {
        return (new SQLFunctions()).getInt(uuid, table, type);
    }
}
