package br.edu.ifsudestemg.barbacena.daw.museumschedule.model;

import java.time.LocalTime;

public class MuseumAvailableHours {

    private LocalTime hour;
    private int vacations;

    public LocalTime getHour() {
        return hour;
    }

    public void setHour(LocalTime hour) {
        this.hour = hour;
    }

    public int getVacations() {
        return vacations;
    }

    public void setVacations(int vacations) {
        this.vacations = vacations;
    }

    @Override
    public String toString() {
        return hour + " - " + vacations;
    }
}
