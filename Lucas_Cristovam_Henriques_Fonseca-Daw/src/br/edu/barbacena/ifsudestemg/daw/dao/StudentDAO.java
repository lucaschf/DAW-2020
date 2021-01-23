package br.edu.barbacena.ifsudestemg.daw.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.barbacena.ifsudestemg.daw.jdbc.ConnectionFactory;
import br.edu.barbacena.ifsudestemg.daw.model.Student;

public class StudentDAO {

	private Connection connection;
	private final String tableName = "alunos";

	public StudentDAO() {
		this.connection = ConnectionFactory.getConnection();
	}

	public void add(Student aluno) {
		final String query = String
				.format("INSERT INTO %s (nome, email, endereco, datanascimento) values ( ?, ?, ?, ?);", tableName);

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, aluno.getName());
			statement.setString(2, aluno.getEmail());
			statement.setString(3, aluno.getAddress());
			statement.setDate(4, Date.valueOf(aluno.getBirthDate()));

			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update(Student aluno) {
		final String query = String.format("UPDATE %s SET nome=?, email=?, endereço=?, datanascimento=? WHERE id=?;",
				tableName);

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, aluno.getName());
			statement.setString(2, aluno.getEmail());
			statement.setString(3, aluno.getAddress());
			statement.setDate(4, Date.valueOf(aluno.getBirthDate()));
			statement.setLong(5, aluno.getId());

			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void remove(Student aluno) {
		final String query = String.format("DELETE FROM %s WHERE id=?", tableName);

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setLong(1, aluno.getId());
			statement.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Student> fetchAll() {
		final String query = "SELECT * FROM " + tableName;

		List<Student> alunos = new ArrayList<>();

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				Student aluno = new Student();

				aluno.setId(rs.getLong("id"));
				aluno.setName(rs.getString("nome"));
				aluno.setEmail(rs.getString("email"));
				aluno.setAddress(rs.getString("endereco"));
				aluno.setBirthDate(rs.getDate("datanascimento").toLocalDate());

				alunos.add(aluno);
			}

		} catch (SQLException e) {
		}

		return alunos;
	}
}
