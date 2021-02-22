package br.edu.ifsudestemg.barbacena.daw.museumschedule.dao;

import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.BookedTime;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Museum;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookedTimesDao extends DAO {

    private final String tableName = "booked_times";

    public boolean add(BookedTime bookedTime) {

        final String query = String.format(
                "INSERT INTO %s(schedule_date, schedule_time, visitors, museum_id) VALUES (?, ?, ?, ?);", tableName);

        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            statement.setDate(1, Date.valueOf(bookedTime.getDate()));
            statement.setTime(2, Time.valueOf(bookedTime.getHour()));
            statement.setInt(3, bookedTime.getVisitors());
            statement.setLong(4, bookedTime.getMuseumId());

            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean update(BookedTime bookedTime) {

        final String query = String.format(
                "UPDATE %s SET visitors=? WHERE schedule_date=? AND schedule_time=? AND museum_id=?", tableName);

        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            statement.setInt(1, bookedTime.getVisitors());
            statement.setDate(2, Date.valueOf(bookedTime.getDate()));
            statement.setTime(3, Time.valueOf(bookedTime.getHour()));
            statement.setLong(4, bookedTime.getMuseumId());

            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<BookedTime> fetchAll() {
        final String query = String.format("SELECT * FROM %s", tableName);

        List<BookedTime> bookedTimes = new ArrayList<>();

        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                BookedTime bookedTime = new BookedTime();

                bookedTime.setDate(rs.getDate("schedule_date").toLocalDate());
                bookedTime.setHour(rs.getTime("schedule_time").toLocalTime());
                bookedTime.setMuseumId(rs.getLong("museum_id"));
                bookedTime.setVisitors(rs.getInt("v"));

                bookedTimes.add(bookedTime);
            }
        } catch (SQLException ignored) {
        }

        return bookedTimes;
    }
}
