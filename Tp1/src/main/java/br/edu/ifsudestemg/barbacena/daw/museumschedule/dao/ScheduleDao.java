package br.edu.ifsudestemg.barbacena.daw.museumschedule.dao;

import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Museum;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Schedule;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDao extends DAO {

    private final String tableName = "schedule";

    public boolean add(Schedule schedule) {
        final String query = String.format("INSERT INTO %s(scheduler_email, schedule_date, schedule_time," +
                        " visitors, museum_id, code) VALUES( ?, ?, ?, ?, ?, ?)",
                tableName
        );

        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            statement.setString(1, schedule.getSchedulerEmail());
            statement.setDate(2, Date.valueOf(schedule.getDate()));
            statement.setTime(3, Time.valueOf(schedule.getHours()));
            statement.setInt(4, schedule.getVisitorsCount());
            statement.setLong(5, schedule.getMuseum().getId());
            statement.setString(6, String.valueOf(schedule.hashCode()));

            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Schedule> fetchAll(){
        final String query = String.format("SELECT * FROM %s", tableName);

        List<Schedule> schedules = new ArrayList<>();

        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Schedule  schedule= new Schedule();

                schedule.setConfirmationCode(rs.getString("code"));
                schedule.setSchedulerEmail(rs.getString("scheduler_email"));
                schedule.setDate(LocalDate.parse(rs.getString("schedule_date")));
                schedule.setHours(LocalTime.parse(rs.getString("schedule_time")));
                schedule.setMuseum(new MuseumDAO().fetchById(rs.getLong("museum_id")));
                schedule.setVisitorsCount(rs.getInt("visitors"));

                schedules.add(schedule);
            }
        } catch (SQLException ignored) {
        }

        return schedules;
    }
}
