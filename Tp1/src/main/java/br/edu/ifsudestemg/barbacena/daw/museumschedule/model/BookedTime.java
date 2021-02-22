package br.edu.ifsudestemg.barbacena.daw.museumschedule.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class BookedTime {

    private long museumId;
    private LocalTime hour;
    private LocalDate date;
    private int visitors;

    public long getMuseumId() {
        return museumId;
    }

    public void setMuseumId(long museumId) {
        this.museumId = museumId;
    }

    public LocalTime getHour() {
        return hour;
    }

    public void setHour(LocalTime hour) {
        this.hour = hour;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getVisitors() {
        return visitors;
    }

    public void setVisitors(int visitors) {
        this.visitors = visitors;
    }
}
