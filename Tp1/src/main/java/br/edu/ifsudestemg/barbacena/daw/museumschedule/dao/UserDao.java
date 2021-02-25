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
        final String query = String.format("INSERT INTO %s (username, password, role_id, museum_id) values (?, ?, ?, ?)",
                tableName);

        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setLong(3, user.getRole().getCode());
            statement.setLong(4, user.getMuseum_id());

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

    public boolean remove(User user) {
        final String query = String.format("DELETE FROM %s WHERE username=?", tableName);

        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            statement.setString(1, user.getUsername());
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

            if(rs.next()) {
                user = new User();
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                user.setMuseum_id(rs.getLong("museum_id"));
                user.setRole(User.Role.from(rs.getInt("role_id")));
            }
        } catch (Exception e) {
            e.printStackTrace();
            user =null;
        }

        return user;
    }

    public List<User> fetchAll() {
        final String query = String.format("SELECT * FROM %s", tableName);

        List<User> users = new ArrayList<>();

        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                User user = new User();

                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                user.setMuseum_id(rs.getLong("museum_id"));
                user.setRole(User.Role.from(rs.getInt("role_id")));

                users.add(user);
            }

        } catch (SQLException ignored) {
        }

        return users;
    }
}
