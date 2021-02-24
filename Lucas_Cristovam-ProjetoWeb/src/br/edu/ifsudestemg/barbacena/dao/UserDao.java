package br.edu.ifsudestemg.barbacena.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifsudestemg.barbacena.jdbc.ConnectionFactory;
import br.edu.ifsudestemg.barbacena.model.Professor;
import br.edu.ifsudestemg.barbacena.model.User;

public class UserDao {
	
	private Connection connection;
	private final String tableName = "usuario";

	public UserDao() {
		this.connection = ConnectionFactory.getConnection();
	}

	public void add(User user) {
		final String query = String.format("INSERT INTO %s (login, senha) values (?, ?,)", tableName);

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, user.getLogin());
			statement.setString(2, user.getPassword());

			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update(User user) {
		final String query = String.format("UPDATE %s SET senha=? WHERE login=?;", tableName);

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, user.getPassword());
			statement.setString(2, user.getLogin());

			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void remove(User user) {
		final String query = String.format("DELETE FROM %s WHERE login=?", tableName);

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, user.getLogin());
			statement.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public User fetch(String login) {
		final String query = String.format("SELECT * FROM %s WHERE login=?", tableName);
		User user = null;
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, login);
			var rs = statement.executeQuery();
			
			if(rs.next()) {
				user = new User();
				user.setLogin(rs.getString("login"));
				user.setPassword(rs.getString("senha"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return user;
	}
	
	public User validateCredentials(String login, String pass) {
		final String query = String.format("SELECT * FROM %s WHERE login=? AND senha=?", tableName);
		User user = null;
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, login);
			statement.setString(2, pass);
			var rs = statement.executeQuery();
			
			if(rs.next()) {
				user = new User();
				user.setLogin(rs.getString("login"));
				user.setPassword(rs.getString("senha"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return user;
	}
	
	public List<Professor> fetchAll() {
		final String query = "SELECT * FROM " + tableName;

		List<Professor> professores = new ArrayList<>();

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				Professor professor = new Professor();

				professor.setId(rs.getLong("id"));
				professor.setName(rs.getString("nome"));
				professor.setEmail(rs.getString("email"));
				professor.setDegree(rs.getString("grauformacao"));

				professores.add(professor);
			}

		} catch (SQLException e) {
		}

		return professores;
	}
}
