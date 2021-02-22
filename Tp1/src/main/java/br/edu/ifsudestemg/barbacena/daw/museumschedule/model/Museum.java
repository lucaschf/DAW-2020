package br.edu.ifsudestemg.barbacena.daw.museumschedule.model;

import br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.MuseumDAO;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Museum {

    private long id;
    private String name;
    private LocalTime opensAt;
    private LocalTime closesAt;

    private int visitorsLimit;
    private int minutesBetweenVisits = 60;

    private List<DayOfWeek> workingDays;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalTime getOpensAt() {
        return opensAt;
    }

    public void setOpensAt(LocalTime opensAt) {
        this.opensAt = opensAt;
    }

    public LocalTime getClosesAt() {
        return closesAt;
    }

    public void setClosesAt(LocalTime closesAt) {
        this.closesAt = closesAt;
    }

    public int getVisitorsLimit() {
        return visitorsLimit;
    }

    public void setVisitorsLimit(int visitorsLimit) {
        this.visitorsLimit = visitorsLimit;
    }

    public int getMinutesBetweenVisits() {
        return minutesBetweenVisits;
    }

    public void setMinutesBetweenVisits(int minutesBetweenVisits) {
        this.minutesBetweenVisits = minutesBetweenVisits;
    }

    public List<DayOfWeek> getWorkingDays() {
        return workingDays;
    }

    public String getWorkingDaysDisplayNames(){
        var result = new ArrayList<String>();
        workingDays.forEach(wd -> result.add(wd.getDisplayName(TextStyle.SHORT_STANDALONE, new Locale("pt", "BR"))));
        return String.join(", ",result);
    }

    public void setWorkingDays(List<DayOfWeek> workingDays) {
        this.workingDays = workingDays;
    }

    public long getId() {
        return id;
    }

    public Museum setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return "Museum{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", opensAt=" + opensAt +
                ", closesAt=" + closesAt +
                ", visitorsLimit=" + visitorsLimit +
                ", minutesBetweenVisits=" + minutesBetweenVisits +
                ", workingDays=" + workingDays +
                '}';
    }

    public static void main(String[] args) {
        var m = new MuseumDAO().fetchById(1);

        System.out.println(m.getWorkingDaysDisplayNames());
    }
}
