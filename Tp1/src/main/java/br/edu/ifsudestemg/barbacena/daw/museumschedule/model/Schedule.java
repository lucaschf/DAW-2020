package br.edu.ifsudestemg.barbacena.daw.museumschedule.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;

public class Schedule {

    private String confirmationCode;
    private String schedulerEmail;

    private LocalDate date;
    private LocalTime hours;
    private int visitorsCount;
    private Museum museum;
    private ArrayList<Visitor> visitors = new ArrayList<>();

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

    public Museum getMuseum() {
        return museum;
    }

    public void setMuseum(Museum museum) {
        this.museum = museum;
    }

    public int getVisitorsCount() {
        return visitorsCount;
    }

    public void setVisitorsCount(int visitorsCount) {
        this.visitorsCount = visitorsCount;
    }

    public ArrayList<Visitor> getVisitors() {
        return visitors;
    }

    public void setVisitors(ArrayList<Visitor> visitors) {
        this.visitors = visitors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return schedulerEmail.equals(schedule.schedulerEmail) &&
                date.equals(schedule.date) &&
                hours.equals(schedule.hours);
    }

    @Override
    public int hashCode() {
        return Objects.hash(schedulerEmail, date, hours.getHour());
    }
}
