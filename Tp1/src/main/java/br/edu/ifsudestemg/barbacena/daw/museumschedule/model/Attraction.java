package br.edu.ifsudestemg.barbacena.daw.museumschedule.model;

import java.time.LocalDate;

public class Attraction {

    private long id;
    private String title;
    private String details;
    private String coverUrl;
    private Museum museum;

    private LocalDate beginningExhibition;
    private LocalDate endExhibition;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public Museum getMuseum() {
        return museum;
    }

    public void setMuseum(Museum museum) {
        this.museum = museum;
    }

    public LocalDate getBeginningExhibition() {
        return beginningExhibition;
    }

    public void setBeginningExhibition(LocalDate beginningExhibition) {
        this.beginningExhibition = beginningExhibition;
    }

    public LocalDate getEndExhibition() {
        return endExhibition;
    }

    public void setEndExhibition(LocalDate endExhibition) {
        this.endExhibition = endExhibition;
    }
}
