package br.edu.ifsudestemg.barbacena.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	private ConnectionFactory() {
		// prevents instantiation
	}

	public static Connection getConnection() {
		try {
			Class.forName("org.postgresql.Driver");
			return DriverManager.getConnection("jdbc:postgresql://localhost/daw", "postgres", "123456");
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
		}

		return null;
	}
}