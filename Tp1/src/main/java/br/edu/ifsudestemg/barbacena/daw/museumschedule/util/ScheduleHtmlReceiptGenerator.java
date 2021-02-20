package br.edu.ifsudestemg.barbacena.daw.museumschedule.util;

import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Schedule;

public class ScheduleHtmlReceiptGenerator {

    private ScheduleHtmlReceiptGenerator() {
        // prevents instantiation
    }

    public static String generateReceipt(Schedule schedule) {
        return "";
    }

    private static String generateHead(){
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "    <title>Receipt</title>" +
                "    <meta name='viewport' content='width=device-width, initial-scale=1'>" +
                "    <meta charset='utf-8'>" +
                "    <link rel='stylesheet' type='text/css' href='css/main.css'/>" +
                "    <link rel='stylesheet' type='text/css' href='css/receipt.css'/>" +
                "    <link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css' rel='stylesheet'" +
                "          integrity='sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl' crossorigin='anonymous'>" +
                "</head>";
    }


}
