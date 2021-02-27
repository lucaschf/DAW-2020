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

    public boolean isEmailAlreadyBooked(String email, LocalDate date, LocalTime time) {
        String query = "{call is_email_booked(?, ?, ?)}";

        try (CallableStatement statement = getConnection().prepareCall(query)) {
            statement.setString(1, email);
            statement.setDate(2, Date.valueOf(date));
            statement.setTime(3, Time.valueOf(time));

            var rs = statement.executeQuery();

            if (rs.next()) {
                return rs.getBoolean(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    public boolean add(Schedule schedule) {
        final String query = String.format("INSERT INTO %s(scheduler_email, schedule_date, schedule_time," +
                        " visitors, museum_id, termsAcceptanceDate) VALUES( ?, ?, ?, ?, ?, ?) RETURNING %s.id",
                tableName,
                tableName
        );

        try (PreparedStatement statement = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, schedule.getSchedulerEmail());
            statement.setDate(2, Date.valueOf(schedule.getDate()));
            statement.setTime(3, Time.valueOf(schedule.getHours()));
            statement.setInt(4, schedule.getVisitorsCount());
            statement.setLong(5, schedule.getMuseum().getId());
            statement.setTimestamp(6, Timestamp.valueOf(schedule.getTermsAcceptanceDate()));

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

        return fetchSchedules(query);
    }

    public List<Schedule> fetchAllByMuseum(long museumId)
    {
        final String query = String.format("SELECT * FROM %s WHERE museum_id= %d", tableName, museumId);
        return fetchSchedules(query);
    }

    public Schedule fetchByEmailAndCode(String email, String confirmationCode) {
        final String query = String.format("SELECT * FROM %s WHERE scheduler_email = '%s' AND code = '%s'",
                tableName,
                email,
                confirmationCode);

        return fetchSchedule(query);
    }

    public Schedule fetchById(long id) {
        final String query = String.format("SELECT * FROM %s WHERE id = %d",
                tableName,
                id);

        return fetchSchedule(query);
    }

    private Schedule fetchSchedule(String query) {
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Schedule schedule = new Schedule();
                getSchedule(schedule, rs);
                return schedule;
            }
        } catch (Exception ignored) {
        }

        return null;
    }

    private List<Schedule> fetchSchedules(String query) {
        List<Schedule> schedules = new ArrayList<>();

        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Schedule schedule = new Schedule();

                getSchedule(schedule, rs);

                schedules.add(schedule);
            }
        } catch (SQLException ignored) {
        }

        return schedules;
    }

    private void getSchedule(Schedule schedule, ResultSet rs) throws SQLException {
        schedule.setConfirmationCode(rs.getString("code"));
        schedule.setId(rs.getLong("id"));
        schedule.setSchedulerEmail(rs.getString("scheduler_email"));
        schedule.setDate(LocalDate.parse(rs.getString("schedule_date")));
        schedule.setHours(LocalTime.parse(rs.getString("schedule_time")));
        schedule.setMuseum(new MuseumDAO().fetchById(rs.getLong("museum_id")));
        schedule.setTermsAcceptanceDate(rs.getTimestamp("termsAcceptanceDate").toLocalDateTime());
        schedule.setVisitorsCount(rs.getInt("visitors"));
        schedule.addVisitors(visitorsDao.fetchAllBySchedule(schedule.getId()));
    }

    public boolean remove(Schedule schedule) {
        final String query = String.format("DELETE FROM %s WHERE id=?", tableName);

        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            statement.setLong(1, schedule.getId());
            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
