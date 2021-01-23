package br.edu.barbacena.ifsudestemg.daw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.barbacena.ifsudestemg.daw.jdbc.ConnectionFactory;
import br.edu.barbacena.ifsudestemg.daw.model.Professor;

public class ProfessorDAO {

	private Connection connection;
	private final String tableName = "professores";

	public ProfessorDAO() {
		this.connection = ConnectionFactory.getConnection();
	}

	public void add(Professor professor) {
		final String query = String.format("INSERT INTO %s (nome, email, grauformacao) values (?, ?, ?);", tableName);

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, professor.getName());
			statement.setString(2, professor.getEmail());
			statement.setString(3, professor.getDegree());

			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update(Professor professor) {
		final String query = String.format("UPDATE %s SET nome=?, email=?, grauformacao=? WHERE id=?;", tableName);

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, professor.getName());
			statement.setString(2, professor.getEmail());
			statement.setString(3, professor.getDegree());
			statement.setLong(4, professor.getId());

			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void remove(Professor professor) {
		final String query = String.format("DELETE FROM %s WHERE id=?", professor);

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setLong(1, professor.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
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
