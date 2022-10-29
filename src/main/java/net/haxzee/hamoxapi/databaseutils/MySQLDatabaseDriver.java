package net.haxzee.hamoxapi.databaseutils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.sql.*;

public class MySQLDatabaseDriver {
    private static final String CONNECT_ARGUMENTS = "jdbc:mysql://%s:%d/%s?serverTimezone=UTC";

    private HikariDataSource hikariDataSource;

    private MySQLDatabaseUtils mySQLDatabaseUtils;

    public void connect(@NotNull DatabaseConfig config) {
        (new HikariConfig()).setJdbcUrl(String.format("jdbc:mysql://%s:%d/%s?serverTimezone=UTC", new Object[]{config.getHost(), Integer.valueOf(config.getPort()), config.getDatabase()}));
        (new HikariConfig()).setDriverClassName("com.mysql.jdbc.Driver");
        (new HikariConfig()).setUsername(config.getUsername());
        (new HikariConfig()).setPassword(config.getPassword());
        (new HikariConfig()).addDataSourceProperty("cachePrepStmts", "true");
        (new HikariConfig()).addDataSourceProperty("prepStmtCacheSize", "250");
        (new HikariConfig()).addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        (new HikariConfig()).addDataSourceProperty("useServerPrepStmts", "true");
        (new HikariConfig()).addDataSourceProperty("useLocalSessionState", "true");
        (new HikariConfig()).addDataSourceProperty("rewriteBatchedStatements", "true");
        (new HikariConfig()).addDataSourceProperty("cacheResultSetMetadata", "true");
        (new HikariConfig()).addDataSourceProperty("cacheServerConfiguration", "true");
        (new HikariConfig()).addDataSourceProperty("elideSetAutoCommits", "true");
        (new HikariConfig()).addDataSourceProperty("maintainTimeStats", "false");
        this.hikariDataSource = new HikariDataSource(new HikariConfig());
        this.mySQLDatabaseUtils = new MySQLDatabaseUtils(this);
    }

    public HikariDataSource getHikariDataSource() {
        return this.hikariDataSource;
    }

    public MySQLDatabaseUtils getMySQLDatabaseUtils() {
        return this.mySQLDatabaseUtils;
    }

    public int executeUpdate(String query) {
        try (Connection connection = this.hikariDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            return preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
            return -1;
        }
    }

    public <T> T executeQuery(String query, SQLFunction<ResultSet, T> function, T defaultValue) {
        try (Connection connection = this.hikariDataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            Throwable throwable = null;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return defaultValue;
        }
        return null;
    }

    private void applyParameters(@NotNull PreparedStatement preparedStatement, @NonNls Object... objects) throws SQLException {
        int i = 1;
        for (Object object : objects) {
            if (object instanceof byte[]) {
                preparedStatement.setBytes(i++, (byte[]) object);
            } else if (object instanceof Short) {
                preparedStatement.setShort(i++, ((Short) object).shortValue());
            } else if (object instanceof Integer) {
                preparedStatement.setInt(i++, ((Integer) object).intValue());
            } else if (object instanceof Long) {
                preparedStatement.setLong(i++, ((Long) object).longValue());
            } else if (object instanceof Double) {
                preparedStatement.setDouble(i++, ((Double) object).doubleValue());
            } else if (object instanceof Float) {
                preparedStatement.setFloat(i++, ((Float) object).floatValue());
            } else if (object instanceof Byte) {
                preparedStatement.setByte(i++, ((Byte) object).byteValue());
            } else if (object instanceof Boolean) {
                preparedStatement.setBoolean(i++, ((Boolean) object).booleanValue());
            } else if (object instanceof BigDecimal) {
                preparedStatement.setBigDecimal(i++, (BigDecimal) object);
            } else if (object instanceof Date) {
                preparedStatement.setDate(i++, (Date) object);
            } else if (object instanceof Timestamp) {
                preparedStatement.setTimestamp(i++, (Timestamp) object);
            } else if (object instanceof Time) {
                preparedStatement.setTime(i++, (Time) object);
            } else {
                if (!(object instanceof String))
                    throw new IllegalStateException("Unable to set object " + object.getClass().getName());
                preparedStatement.setString(i++, (String) object);
            }
        }
    }
}
