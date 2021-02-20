package br.edu.ifsudestemg.barbacena.daw.museumschedule.model;

public enum TicketType {

    ENTIRE(1),
    HALF_ENTRY(2),
    EXEMPTED(3),
    REGISTERED_RESIDENT(4),
    ACCREDITED_DRIVERS(5);

    private final int code;

    TicketType(int code) {
        this.code = code;
    }

    public static TicketType from(int code) throws IllegalArgumentException {
        for (TicketType t : values())
            if (t.code == code)
                return t;

        throw new IllegalArgumentException("No such ticket type");
    }
}