package br.edu.ifsudestemg.barbacena.daw.museumschedule.util;

import java.time.format.DateTimeFormatter;

public class FormatterUtils {

    public DateTimeFormatter getBrazilianDateFormatter() {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }

    public DateTimeFormatter getBrazilianDateTimeFormatter() {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");
    }
}
