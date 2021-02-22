package br.edu.ifsudestemg.barbacena.daw.museumschedule.dao;

import br.com.caelum.stella.tinytype.CPF;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Employee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDao extends DAO {

    String tableName = "museum_employee";

    public boolean add(Employee employee) {
        final String query = String.format("INSERT INTO %s (name, cpf, museum_id) values (?, ?, ?);", tableName);

        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getCpf().getNumero());
            statement.setLong(3, employee.getMuseum().getId());

            statement.execute();
            return true;
        } catch (SQLException ignored) {
        }

        return false;
    }

    public List<Employee> fetchAll() {
        final String query = String.format("SELECT * FROM %s ORDER BY museum_id", tableName);

        List<Employee> employees = new ArrayList<>();

        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();

            var museumDao = new MuseumDAO();

            while (rs.next()) {
                Employee employee = new Employee();

                employee.setMuseum(museumDao.fetchById(rs.getLong("museum_id")));
                employee.setCpf(new CPF(rs.getString("cpf")));
                employee.setName(rs.getString("name"));

                employees.add(employee);
            }
        } catch (SQLException ignored) {
        }

        return employees;
    }
}
