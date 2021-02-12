package br.edu.ifsudestemg.barbacena.test;

import java.sql.Connection;

import br.edu.ifsudestemg.barbacena.jdbc.ConnectionFactory;

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
