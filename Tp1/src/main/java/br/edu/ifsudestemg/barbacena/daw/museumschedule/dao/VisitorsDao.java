package br.edu.ifsudestemg.barbacena.daw.museumschedule.dao;

import br.com.caelum.stella.tinytype.CPF;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.TicketType;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Visitor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VisitorsDao extends DAO {

    private final String tableName = "visitor";

    public boolean add(Visitor visitor) {
        final String query = String.format(
                "INSERT INTO %s(schedule_id, cpf, name, ticket_type) VALUES (?, ?, ?, ?);", tableName);

        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            statement.setLong(1, visitor.getScheduleID());
            statement.setString(2, visitor.getCpf().getNumero());
            statement.setString(3, visitor.getName());
            statement.setInt(4, visitor.getTicketType().getCode());

            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Visitor> fetchAllBySchedule(long code) {
        final String query = String.format("SELECT * FROM %s WHERE schedule_id = %d", tableName, code);

        List<Visitor> visitors = new ArrayList<>();

        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Visitor visitor = new Visitor();

                visitor.setCpf(new CPF(rs.getString("cpf")));
                visitor.setName(rs.getString("name"));
                visitor.setScheduleID(rs.getLong("schedule_id"));
                visitor.setTicketType(TicketType.from(rs.getInt("ticket_type")));

                visitors.add(visitor);
            }
        } catch (SQLException ignored) {
        }

        return visitors;
    }

    public boolean remove(Visitor visitor) {
        String sql = String.format("DELETE FROM %s WHERE schedule_id = %d AND cpf LIKE '%s';",
                tableName,
                visitor.getScheduleID(),
                visitor.getCpf().getNumero());

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            return stmt.executeUpdate() > 0;
        } catch (SQLException ignored) {
        }

        return false;
    }

    public void add(ArrayList<Visitor> visitors) {
        visitors.forEach(this::add);
    }
}