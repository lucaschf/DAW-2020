package br.edu.ifsudestemg.barbacena.daw.museumschedule.dao;

import br.com.caelum.stella.tinytype.CPF;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.TicketType;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Visitor;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ScheduleReportDao extends DAO {

    public List<ScheduleReport> generateReportByMuseumInDayAndTime(LocalDate date, LocalTime time, Long museum_id) {
        final String query = "{call visitors_per_day_time (?, ?, ?)}";

        var data = new ArrayList<ScheduleReport>();

        try (CallableStatement statement = getConnection().prepareCall(query)) {
            statement.setDate(1, Date.valueOf(date));
            statement.setTime(2, Time.valueOf(time));

            if (museum_id != null)
                statement.setLong(3, museum_id);
            else
                statement.setNull(3, Types.BIGINT);

            fetchEntries(data, statement);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    private void fetchEntries(ArrayList<ScheduleReport> data, CallableStatement statement) throws SQLException {
        var rs = statement.executeQuery();

        while (rs.next()) {
            ScheduleReport entry = new ScheduleReport();
            entry.setId(rs.getInt("schedule_id"));
            entry.setConfirmationCode(rs.getString("schedule_number"));
            entry.setSchedulerEmail(rs.getString("scheduler_email"));
            entry.setDate(rs.getDate("schedule_date").toLocalDate());
            entry.setHours(rs.getTime("schedule_time").toLocalTime());

            entry.setMuseumName(rs.getString("museum_name"));

            Visitor visitor = new Visitor();

            visitor.setAttended(rs.getBoolean("attended"));
            visitor.setTicketType(TicketType.from(rs.getInt("ticket_type")));
            visitor.setCpf(new CPF(rs.getString("cpf")));
            visitor.setName(rs.getString("name"));

            entry.setVisitor(visitor);

            data.add(entry);
        }
    }

    public List<ScheduleReport> reportOfAttendedVisitorsByMuseumInDay(LocalDate date, Long museum_id) {
        final String query = "{call visitors_who_attended_per_day (?, ?)}";

        var data = new ArrayList<ScheduleReport>();

        try (CallableStatement statement = getConnection().prepareCall(query)) {
            statement.setDate(1, Date.valueOf(date));

            if (museum_id != null)
                statement.setLong(2, museum_id);
            else
                statement.setNull(2, Types.BIGINT);

            fetchEntries(data, statement);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    public static class ScheduleReport {
        private long id;
        private String confirmationCode = "";
        private String schedulerEmail;

        private LocalDate date;
        private LocalTime hours;
        private int visitorsCount;
        private String museumName;
        private Visitor visitor;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getConfirmationCode() {
            return confirmationCode;
        }

        public void setConfirmationCode(String confirmationCode) {
            this.confirmationCode = confirmationCode;
        }

        public String getSchedulerEmail() {
            return schedulerEmail;
        }

        public void setSchedulerEmail(String schedulerEmail) {
            this.schedulerEmail = schedulerEmail;
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public LocalTime getHours() {
            return hours;
        }

        public void setHours(LocalTime hours) {
            this.hours = hours;
        }

        public int getVisitorsCount() {
            return visitorsCount;
        }

        public void setVisitorsCount(int visitorsCount) {
            this.visitorsCount = visitorsCount;
        }

        public String getMuseumName() {
            return museumName;
        }

        public void setMuseumName(String museumName) {
            this.museumName = museumName;
        }

        public Visitor getVisitor() {
            return visitor;
        }

        public void setVisitor(Visitor visitor) {
            this.visitor = visitor;
        }
    }

}
