package br.edu.ifsudestemg.barbacena.daw.museumschedule.dao;

import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao extends DAO {

    private final String tableName = "users";

    public boolean add(User user) {
        final String query = String.format("INSERT INTO %s (username, password, role_id, employee_id) values (?, ?, ?, ?)",
                tableName);

        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setLong(3, user.getRole().getCode());
            statement.setLong(4, user.getEmployeeId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean update(User user) {
        final String query = String.format("UPDATE %s SET password=? WHERE username=?;", tableName);

        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            statement.setString(1, user.getPassword());
            statement.setString(2, user.getUsername());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean remove(String username) {
        final String query = String.format("DELETE FROM %s WHERE username=?", tableName);

        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            statement.setString(1, username);
            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public User auth(String username, String pass) {
        final String query = String.format("SELECT * FROM %s WHERE username=? AND password=?", tableName);
        User user = null;

        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, pass);
            var rs = statement.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                user.setEmployeeId(rs.getLong("employee_id"));
                user.setRole(User.Role.from(rs.getInt("role_id")));
            }
        } catch (Exception e) {
            e.printStackTrace();
            user = null;
        }

        return user;
    }

    public List<User> fetchAll() {
        final String query = String.format("SELECT * FROM %s", tableName);

        List<User> users = new ArrayList<>();

        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                users.add(extractUserData(rs));
            }

        } catch (SQLException ignored) {
        }

        return users;
    }

    public User fetchByEmployee(Long employeeId) {
        final String query = String.format("SELECT * FROM %s WHERE employee_id =?", tableName);

        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            statement.setLong(1, employeeId);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return extractUserData(rs);
            }

        } catch (SQLException ignored) {
        }

        return null;
    }

    private User extractUserData(ResultSet rs) throws SQLException {
        User user = new User();

        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        user.setEmployeeId(rs.getLong("employee_id"));
        user.setRole(User.Role.from(rs.getInt("role_id")));

        return user;
    }
}
