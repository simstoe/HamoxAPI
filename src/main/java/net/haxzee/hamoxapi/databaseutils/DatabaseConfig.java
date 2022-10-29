package net.haxzee.hamoxapi.databaseutils;

public class DatabaseConfig {
    private String host;

    private String database;

    private String password;

    private String username;

    private int port;

    public DatabaseConfig(String host, String database, String password, String username, int port) {
        this.host = host;
        this.database = database;
        this.password = password;
        this.username = username;
        this.port = port;
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getDatabase() {
        return this.database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
