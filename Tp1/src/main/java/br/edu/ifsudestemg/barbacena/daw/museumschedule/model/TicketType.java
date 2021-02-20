package br.edu.ifsudestemg.barbacena.daw.museumschedule.model;

public enum TicketType {

    ENTIRE(1, "Inteiro"),
    HALF_ENTRY(2, "Meia entrada - Estudante"),
    EXEMPTED(3, "Isento"),
    REGISTERED_RESIDENT(4, "Morador cadastrado"),
    ACCREDITED_DRIVERS(5, "Condutor licenciado");

    private final int code;
    private final String description;

    TicketType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static TicketType from(int code) throws IllegalArgumentException {
        for (TicketType t : values())
            if (t.code == code)
                return t;

        throw new IllegalArgumentException("No such ticket type");
    }

    @Override
    public String toString() {
        return description;

    }
}