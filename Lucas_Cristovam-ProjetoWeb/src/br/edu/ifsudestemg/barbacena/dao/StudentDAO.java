package br.edu.ifsudestemg.barbacena.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifsudestemg.barbacena.jdbc.ConnectionFactory;
import br.edu.ifsudestemg.barbacena.model.Student;

public class StudentDAO {

	private Connection connection;
	private final String tableName = "alunos";

	public StudentDAO() {
		this.connection = ConnectionFactory.getConnection();
	}

	public void add(Student student) {
		final String query = String
				.format("INSERT INTO %s (nome, email, endereco, datanascimento) values ( ?, ?, ?, ?);", tableName);

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, student.getName());
			statement.setString(2, student.getEmail());
			statement.setString(3, student.getAddress());
			statement.setDate(4, Date.valueOf(student.getBirthDate()));

			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update(Student student) {
		final String query = String.format("UPDATE %s SET nome=?, email=?, endereco=?, datanascimento=? WHERE id=?;",
				tableName);

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, student.getName());
			statement.setString(2, student.getEmail());
			statement.setString(3, student.getAddress());
			statement.setDate(4, Date.valueOf(student.getBirthDate()));
			statement.setLong(5, student.getId());

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

		List<Student> students = new ArrayList<>();

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				Student aluno = new Student();

				aluno.setId(rs.getLong("id"));
				aluno.setName(rs.getString("nome"));
				aluno.setEmail(rs.getString("email"));
				aluno.setAddress(rs.getString("endereco"));
				aluno.setBirthDate(rs.getDate("datanascimento").toLocalDate());

				students.add(aluno);
			}

		} catch (SQLException e) {
		}

		return students;
	}
}
