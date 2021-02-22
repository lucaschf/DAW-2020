package br.edu.ifsudestemg.barbacena.daw.museumschedule.model;

import br.com.caelum.stella.tinytype.CPF;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Schedule {
    private long id;
    private String confirmationCode;
    private String schedulerEmail;

    private LocalDate date;
    private LocalTime hours;
    private int visitorsCount;
    private Museum museum;
    private LocalDateTime termsAcceptanceDate = null;
    private final ArrayList<Visitor> visitors = new ArrayList<>();

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

    public LocalDateTime getTermsAcceptanceDate() {
        return termsAcceptanceDate;
    }

    public void setTermsAcceptanceDate(LocalDateTime termsAcceptanceDate) {
        this.termsAcceptanceDate = termsAcceptanceDate;
    }

    public void addVisitors(@SuppressWarnings("unused") List<Visitor> visitors) {
        visitors.forEach(this::addVisitor);
    }

    public ArrayList<Visitor> getVisitors() {
        return visitors;
    }

    public boolean addVisitor(Visitor visitor) {
        if (visitorsCount <= visitors.size())
            return false;

        if (!visitorAlreadyBooked(visitor)) {
            return visitors.add(visitor);
        }

        return false;
    }

    public boolean visitorAlreadyBooked(Visitor visitor) {
        return visitors.stream().anyMatch(v -> visitor.getCpf().equals(v.getCpf()));
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
        return Objects.hash(schedulerEmail, date, hours);
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "confirmationCode='" + confirmationCode + '\'' +
                ", schedulerEmail='" + schedulerEmail + '\'' +
                ", date=" + date +
                ", hours=" + hours +
                ", visitorsCount=" + visitorsCount +
                ", museum=" + museum +
                ", visitors=" + visitors +
                '}';
    }

    public void removeVisitorByCpf(CPF cpf) {
        visitors.removeIf(v -> v.getCpf().equals(cpf));
    }
}
