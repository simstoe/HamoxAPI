package net.haxzee.hamoxapi.databaseutils;

import java.sql.*;

public class SQLConnector {
    private String host = "";

    private String username = "";

    private String password = "";

    private String database = "";

    private int port;

    private static Connection connection;

    public SQLConnector(String host, String username, String password, String database, int port) {
        this.host = host;
        this.username = username;
        this.password = password;
        this.database = database;
        this.port = port;
        connect();
    }

    public void connect() {
        if (!isConnected())
            try {
                connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?autoReconnect=true", this.username, this.password);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
    }

    public void disconnect() {
        if (isConnected())
            try {
                connection.close();
            } catch (SQLException ignored) {}
    }

    public boolean isConnected() {
        return !(connection == null);
    }

    public Connection getConnection() {
        return connection;
    }

    public void update(String qry) {
        try {
            PreparedStatement ps = connection.prepareStatement(qry);
            ps.executeUpdate();
        } catch (SQLException sQLException) {}
    }

    public ResultSet getResult(String qry) {
        try {
            PreparedStatement ps = connection.prepareStatement(qry);
            return ps.executeQuery();
        } catch (SQLException sQLException) {
            return null;
        }
    }
}
