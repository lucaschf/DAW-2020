package br.edu.ifsudestemg.barbacena.daw.museumschedule.dao;

import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Museum;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MuseumDAO extends DAO {

    public List<Museum> fetchAll() {
        String tableName = "museum";
        final String query = "SELECT * FROM " + tableName;

        List<Museum> museums = new ArrayList<>();

        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Museum museum = new Museum();

                retrieveMuseumData(museum, rs);
                museums.add(museum);
            }
        } catch (SQLException ignored) {
        }

        return museums;
    }

    public List<DayOfWeek> fetchWorkingDays(long museumId) {
        String tableName = "museum_working_days";
        final String query = String.format("SELECT * FROM %s WHERE museum_id = %d", tableName, museumId);

        List<DayOfWeek> workingDays = new ArrayList<>();

        try (PreparedStatement statement = getConnection().prepareStatement(query)) {

            var rs = statement.executeQuery();

            while (rs.next()) {
                workingDays.add(DayOfWeek.of(rs.getInt("day_of_week")));
            }
        } catch (SQLException ignored) {
        }

        return workingDays;
    }

    public Museum fetchById(long museum_id) {
        String tableName = "museum";
        final String query = String.format("SELECT * FROM %s WHERE id = %d", tableName, museum_id);

        Museum museum = new Museum();

        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            var rs = statement.executeQuery();

            if (rs.next()) {
                retrieveMuseumData(museum, rs);
                return museum;
            }
        } catch (SQLException ignored) {
        }

        return null;
    }

    private void retrieveMuseumData(Museum museum, ResultSet rs) throws SQLException {
        museum.setId(rs.getLong("id"));
        museum.setMinutesBetweenVisits(rs.getInt("minutes_between_visits"));
        museum.setName(rs.getString("name"));
        museum.setVisitorsLimit(rs.getInt("visitors_at_time"));
        museum.setClosesAt(LocalTime.from(rs.getTime("closes_at").toLocalTime()));
        museum.setOpensAt(LocalTime.from(rs.getTime("opens_at").toLocalTime()));

        museum.setWorkingDays(fetchWorkingDays(museum.getId()));
    }
}
