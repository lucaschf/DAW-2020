package br.edu.ifsudestemg.barbacena.daw.museumschedule.dao;

import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Schedule;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.TicketType;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Visitor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class VisitorsDao extends DAO {

    private final String tableName = "visitor";

    public boolean add(Visitor visitor) {
        final String query = String.format(
                "INSERT INTO %s(schedule_code, cpf, name, ticket_type) VALUES (?, ?, ?, ?);", tableName);

        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            statement.setString(1, visitor.getScheduleCode());
            statement.setString(2, visitor.getCpf());
            statement.setString(3, visitor.getName());
            statement.setInt(4, visitor.getTicketType().getCode());

            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Visitor> fetchAllByScheduleCode(String code) {
        final String query = String.format("SELECT * FROM %s WHERE schedule_code LIKE '%s'", tableName, code);

        List<Visitor> visitors = new ArrayList<>();

        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Visitor visitor = new Visitor();

                visitor.setCpf(rs.getString("cpf"));
                visitor.setName(rs.getString("name"));
                visitor.setScheduleCode(rs.getString("schedule_code"));
                visitor.setTicketType(TicketType.from(rs.getInt("ticket_type")));

                visitors.add(visitor);
            }
        } catch (SQLException ignored) {
        }

        return visitors;
    }

    public void remove(Visitor visitor) {
        String sql = String.format("DELETE FROM %s WHERE schedule_code LIKE '%s' AND cpf LIKE '%s';",
                tableName,
                visitor.getScheduleCode(),
                visitor.getCpf());

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.execute();
        } catch (SQLException ignored) {
        }
    }
}
