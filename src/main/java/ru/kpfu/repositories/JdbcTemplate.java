package ru.kpfu.repositories;

import ru.kpfu.exceptions.SQLQueryException;
import ru.kpfu.utils.RowMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate<T> {
    private String url;
    private String user;
    private String pass;

    public JdbcTemplate(String driver, String url, String user, String pass) {
        getDriverInfo(driver);
        this.url = url;
        this.user = user;
        this.pass = pass;
    }

    public <T> List<T> query(String sqlQuery, RowMapper<T> mapper, Object... args) {
        List<T> data;
        try (Connection connection = DriverManager.getConnection(url, user, pass)) {
            try (PreparedStatement statement = setParameters(connection, sqlQuery, args)) {
                data = getData(statement, mapper);
            }
        } catch (SQLException e) {
            throw new SQLQueryException("Can't execute query", e);
        }
        return data;
    }

    public void update(String sqlQuery, Object... args) {
        try (Connection connection = DriverManager.getConnection(url, user, pass)) {
            try (PreparedStatement statement = setParameters(connection, sqlQuery, args)) {
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new SQLQueryException("Can't execute update", e);
        }
    }

    private <T> List<T> getData(PreparedStatement statement, RowMapper<T> builder) {
        List<T> data = new ArrayList<>();
        try (ResultSet set = statement.executeQuery()) {
            while (set.next()) {
                data.add(builder.map(set));
            }
        } catch (SQLException e) {
            throw new SQLQueryException("Can't get data", e);
        }
        return data;
    }

    private PreparedStatement setParameters(Connection connection, String sqlQuery, Object... args) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sqlQuery);
        for (int i = 0; i < args.length; i++) {
            statement.setObject(i + 1, args[i]);
        }
        return statement;
    }

    private void getDriverInfo(String driver) {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            System.out.println("Unknown driver." + e.getMessage());
        }
    }
}
