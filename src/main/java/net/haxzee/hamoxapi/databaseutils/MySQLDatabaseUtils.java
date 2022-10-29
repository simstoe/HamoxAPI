package net.haxzee.hamoxapi.databaseutils;

import com.google.common.collect.Maps;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

public class MySQLDatabaseUtils {
    private final MySQLDatabaseDriver mySQLDatabaseDriver;

    public MySQLDatabaseUtils(MySQLDatabaseDriver mySQLDatabaseDriver) {
        this.mySQLDatabaseDriver = mySQLDatabaseDriver;
    }

    public void createTable(String tableName, Object... columns) {
        StringBuilder stringBuilder = (new StringBuilder()).append("CREATE TABLE IF NOT EXISTS `").append(tableName).append("` (`");
        int counter = 0;
        Object[] var5 = columns;
        int var6 = columns.length;
        for (int var7 = 0; var7 < var6; var7++) {
            Object object = var5[var7];
            if (counter + 1 >= columns.length) {
                stringBuilder.append(object).append("` text)");
                break;
            }
            stringBuilder.append(object).append("` text, `");
            counter++;
        }
        CompletableFuture.supplyAsync(() -> Integer.valueOf(this.mySQLDatabaseDriver.executeUpdate(stringBuilder.toString())));
    }

    public void createEntry(String table, List<String> types, List<String> list) {
        String upload = "INSERT INTO " + table + "(" + (String)types.get(0);
        int i;
        for (i = 1; i < types.size(); i++)
            upload = upload + ", " + (String)types.get(i);
        upload = upload + ") VALUES ('" + (String)list.get(0) + "'";
        for (i = 1; i < list.size(); i++)
            upload = upload + ", '" + (String)list.get(i) + "'";
        upload = upload + ");";
        String finalUpload = upload;
        CompletableFuture.supplyAsync(() -> Integer.valueOf(this.mySQLDatabaseDriver.executeUpdate(finalUpload)));
    }

    public boolean existEntry(String table, String key, String value) {
        return ((Boolean)this.mySQLDatabaseDriver.<Boolean>executeQuery("SELECT * FROM " + table + " WHERE " + key + "='" + value + "'", ResultSet::next, Boolean.valueOf(false))).booleanValue();
    }

    public String getFromTable(String tableName, String column, String value, String neededColumn) {
        return this.mySQLDatabaseDriver.<String>executeQuery("SELECT * FROM " + tableName + " WHERE " + column + "='" + value + "'", resultSet -> resultSet.next() ? resultSet.getString(neededColumn) : "null", "null");
    }

    public void setEntry(String table, String line, String entry, String value, String key) {
        CompletableFuture.supplyAsync(() -> Integer.valueOf(this.mySQLDatabaseDriver.executeUpdate("UPDATE " + table + " SET " + line + "= '" + entry + "' WHERE " + value + "= '" + key + "';")));
    }

    public HashMap sortByObject(String table, String type, int max) {
        AtomicInteger count = new AtomicInteger(1);
        HashMap list = Maps.newHashMap();
        this.mySQLDatabaseDriver.executeQuery("SELECT * FROM " + table + " ORDER BY " + type + " DESC LIMIT " + max, resultSet -> {
            while (resultSet.next())
                list.put(Integer.valueOf(count.getAndIncrement()), resultSet.getString("uuid"));
            return list;
        }, list);
        return list;
    }
}
