package br.edu.ifsudestemg.barbacena.daw.museumschedule.model;

public class Message {

    private String title;
    private String message;
    private MessageType type = MessageType.DANGER;

    public String getTitle() {
        return title;
    }

    public Message() {
    }

    public Message(String message) {
        this.title = "";
        this.message = message;
    }

    public Message(String message, MessageType type) {
        this.title = "";
        this.message = message;
        this.type = type;
    }

    public Message(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public MessageType getType() {
        return type;
    }

    public Message setType(MessageType type) {
        this.type = type;
        return this;
    }

    public boolean isSuccess(){
        return type == MessageType.SUCCESS;
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

    public enum MessageType {
        DANGER, WARNING, SUCCESS, INFO
    }
}
