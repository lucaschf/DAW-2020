package br.edu.ifsudestemg.barbacena.daw.museumschedule.dao;

import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Schedule;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDao extends DAO {

    private final String tableName = "schedule";

    private final VisitorsDao visitorsDao = new VisitorsDao();

    public boolean add(Schedule schedule) {
        final String query = String.format("INSERT INTO %s(scheduler_email, schedule_date, schedule_time," +
                        " visitors, museum_id, code) VALUES( ?, ?, ?, ?, ?, ?) RETURNING %s.id",
                tableName,
                tableName
        );

        System.out.println(query);

        schedule.setConfirmationCode("MSC" + schedule.hashCode());

        try (PreparedStatement statement = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, schedule.getSchedulerEmail());
            statement.setDate(2, Date.valueOf(schedule.getDate()));
            statement.setTime(3, Time.valueOf(schedule.getHours()));
            statement.setInt(4, schedule.getVisitorsCount());
            statement.setLong(5, schedule.getMuseum().getId());
            statement.setString(6, schedule.getConfirmationCode());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating schedule failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    schedule.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating schedule failed, no ID obtained.");
                }
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Schedule> fetchAll() {
        final String query = String.format("SELECT * FROM %s", tableName);

        return getSchedules(query);
    }

    public List<Schedule> fetchByMuseumPerDay(long museumId, LocalDate date) {
        final String query = String.format("SELECT * FROM %s WHERE museum_id = %d AND schedule_date = '%s'",
                tableName,
                museumId,
                Date.valueOf(date));

        return getSchedules(query);
    }

    private List<Schedule> getSchedules(String query) {
        List<Schedule> schedules = new ArrayList<>();

        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Schedule schedule = new Schedule();

                retrieveScheduleData(schedule, rs);

                schedules.add(schedule);
            }
        } catch (SQLException ignored) {
        }

        return schedules;
    }

    private void retrieveScheduleData(Schedule schedule, ResultSet rs) throws SQLException {
        schedule.setConfirmationCode(rs.getString("code"));
        schedule.setId(rs.getLong("id"));
        schedule.setSchedulerEmail(rs.getString("scheduler_email"));
        schedule.setDate(LocalDate.parse(rs.getString("schedule_date")));
        schedule.setHours(LocalTime.parse(rs.getString("schedule_time")));
        schedule.setMuseum(new MuseumDAO().fetchById(rs.getLong("museum_id")));
        schedule.setVisitorsCount(rs.getInt("visitors"));
        schedule.addVisitors(visitorsDao.fetchAllByScheduleCode(schedule.getConfirmationCode()));
    }

    public boolean remove(Schedule schedule) {
        final String query = String.format("DELETE FROM %s WHERE code=?", tableName);

        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            statement.setString(1, schedule.getConfirmationCode());
            statement.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
