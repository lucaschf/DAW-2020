package br.edu.barbacena.ifsudestemg.daw.test;

import java.sql.Connection;

import br.edu.barbacena.ifsudestemg.daw.jdbc.ConnectionFactory;

public class ConnectionTest {
	
	public static void main(String[] args) {
		try {
			Connection connection = ConnectionFactory.getConnection();
			System.out.println("Connection was succesful!");
			connection.close();
		} catch (Exception e) {
			System.out.println("Connection failed.");
		}
	}
}
