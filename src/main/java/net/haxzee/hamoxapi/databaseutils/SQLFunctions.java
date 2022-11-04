package net.haxzee.hamoxapi.databaseutils;

import net.haxzee.hamoxapi.HamoxService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SQLFunctions {
    public void createTable(String table) {
        HamoxService.getInstance().getSqlConnector().update("CREATE TABLE IF NOT EXISTS " + table.toLowerCase() + " (UUID VARCHAR(100), NAME VARCHAR(100), TYPE TEXT, VALUE INT)");
    }

    public boolean isExistPlayer(UUID uuid, String table, String type) {
        try {
            PreparedStatement preparedStatement = HamoxService.getInstance().getSqlConnector().getConnection().prepareStatement("SELECT * FROM " + table.toLowerCase() + " WHERE UUID = ? AND TYPE = ?");
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.setString(2, type.toUpperCase());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                return (resultSet.getString("TYPE") != null);
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return false;
    }

    public void createPlayer(UUID uuid, String name, String table, String type) {
        if (!isExistPlayer(uuid, table, type))
            try {
                PreparedStatement ps = HamoxService.getInstance().getSqlConnector().getConnection().prepareStatement("INSERT INTO " + table.toLowerCase() + " (UUID, NAME, TYPE, VALUE) VALUES (?, ?, ?, ?)");
                ps.setString(1, uuid.toString());
                ps.setString(2, name);
                ps.setString(3, type.toUpperCase());
                ps.setInt(4, 0);
                ps.executeUpdate();
                ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
    }

    public String getNameByUUID(UUID uuid, String table) {
        try {
            PreparedStatement ps = HamoxService.getInstance().getSqlConnector().getConnection().prepareStatement("SELECT * FROM " + table.toLowerCase() + " WHERE UUID = ?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getString("NAME");
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Integer getInt(UUID uuid, String table, String type) {
        try {
            PreparedStatement ps = HamoxService.getInstance().getSqlConnector().getConnection().prepareStatement("SELECT VALUE FROM " + table.toLowerCase() + " WHERE UUID = ? AND TYPE = ?");
            ps.setString(1, uuid.toString());
            ps.setString(2, type.toUpperCase());
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getInt("VALUE");
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return Integer.valueOf(0);
    }

    public void setInt(UUID uuid, String table, String type, int value) {
        if (isExistPlayer(uuid, table, type))
            try {
                PreparedStatement ps = HamoxService.getInstance().getSqlConnector().getConnection().prepareStatement("UPDATE " + table.toLowerCase() + " SET VALUE = ? WHERE UUID = ? AND TYPE = ?");
                ps.setInt(1, value);
                ps.setString(2, uuid.toString());
                ps.setString(3, type.toUpperCase());
                ps.executeUpdate();
                ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
    }

    public void addInt(UUID uuid, String name, String table, String type, int value) {
        if (isExistPlayer(uuid, table, type)) {
            setInt(uuid, table, type, getInt(uuid, table, type).intValue() + value);
        } else {
            createPlayer(uuid, name, table, type);
        }
    }

    public void removeInt(UUID uuid, String name, String table, String type, int value) {
        if (isExistPlayer(uuid, table, type)) {
            setInt(uuid, table, type, getInt(uuid, table, type).intValue() - value);
        } else {
            createPlayer(uuid, name, table, type);
        }
    }
}
