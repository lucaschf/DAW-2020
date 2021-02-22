package br.edu.ifsudestemg.barbacena.daw.museumschedule.model;

public class ErrorMessage {

    private String title;
    private String message;

    public String getTitle() {
        return title;
    }

    public ErrorMessage() {
    }

    public ErrorMessage(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
