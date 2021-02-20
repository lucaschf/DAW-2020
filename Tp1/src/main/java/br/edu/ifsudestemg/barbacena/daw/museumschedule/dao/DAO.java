package br.edu.ifsudestemg.barbacena.daw.museumschedule.dao;

import br.edu.ifsudestemg.barbacena.daw.museumschedule.jdbc.ConnectionFactory;

import java.sql.Connection;

public abstract class DAO {
    private final Connection connection;

    public DAO() {
        this.connection = ConnectionFactory.getConnection();
    }

    protected Connection getConnection() {
        return connection;
    }
}
