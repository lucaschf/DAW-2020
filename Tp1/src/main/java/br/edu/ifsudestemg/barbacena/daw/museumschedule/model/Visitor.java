package br.edu.ifsudestemg.barbacena.daw.museumschedule.model;

import br.com.caelum.stella.tinytype.CPF;

public class Visitor {

    private Long scheduleID;
    private CPF cpf;
    private String name;
    private TicketType ticketType;

    public Long getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(Long scheduleID) {
        this.scheduleID = scheduleID;
    }

    public CPF getCpf() {
        return cpf;
    }

    public void setCpf(CPF cpf) {
        this.cpf = cpf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }
}
