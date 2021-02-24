package br.edu.ifsudestemg.barbacena.daw.museumschedule.util;

import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Schedule;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static br.edu.ifsudestemg.barbacena.daw.museumschedule.util.Constants.*;

public class ScheduleHtmlReceiptGenerator {

    private Schedule schedule;
    private ReceiptType type;

    public ScheduleHtmlReceiptGenerator(Schedule schedule, ReceiptType type) {
        this.schedule = schedule;
        this.type = type;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public void setType(ReceiptType type) {
        this.type = type;
    }

    public String generateReceipt() {
        return generateBody();
    }

    private String generateBody() {
        return "<body style=\"background-color: #ede7f6; font-family: 'proxima-nova', sans-serif; padding: 40px 0\";>"
                + generateMainTable() + "</body>";
    }

    private String getTitle() {
        if (type == ReceiptType.CREATION)
            return SCHEDULE_CONFIRMED;

        if (type == ReceiptType.CANCELLATION)
            return SCHEDULE_CANCELLED;

        return SCHEDULE_UPDATED;
    }

    private String generateMainTable() {
        return "<table style='margin: 0 auto;' >" +
                "<tbody>" +
                generateDataTable(generateHeaderLogo()
                        + generateTitle()
                        + generateScheduleDateData(schedule.getDate())
                        + generateReceiptImage()
                        + generateAdditionalInfo()
                        + generateCodeInfo(schedule.getConfirmationCode())
                        + generateBaseScheduleInfo(schedule)
                        + generateVisitorsInfo(schedule)
                ) +
                "</tbody>" +
                "<table>";
    }

    private String generateDataTable(String tableContent) {
        return "<tr><td><table style='overflow-x: hidden;" +
                "border: 0;" +
                "align-content: center;" +
                "padding-bottom: 20px;" +
                "border-radius: 10px;" +
                "text-align: center;" +
                "background-color: #fff'>" +
                "<tbody>" +
                tableContent +
                "</tbody>" +
                "</table>" +
                "</td>" +
                "</tr>";
    }

    private String generateHeaderLogo() {
        return " <tr>" +
                "<td align='center'>" +
                "<img src='https://raw.githubusercontent.com/lucaschf/DAW-2020/main/Tp1/web/images/logo_.png'" +
                "alt='Logo' style=' width: 64px;margin-top: 40px; margin-bottom: 16px'>" +
                "</td>" +
                "</tr>";
    }

    private String generateTitle() {
        return " <tr>" +
                "<td style='padding: 20px 40px 0px 40px; text-align: center;'>" +
                "<h1 style='  color: #8e24aa;" +
                "margin: 0;" +
                "font-size: 22px;" +
                "line-height: 150%'>" +
                getTitle() +
                "</h1>" +
                "<hr/>" +
                "</td>" +
                "</tr>";
    }

    private String generateScheduleDateData(LocalDate date) {
        return "<tr>" +
                "<td>" +
                "<p style='font-size: 18px;" +
                "line-height: 150%;" +
                "font-weight: bold;" +
                "text-align: center;" +
                "margin: 16px;" +
                "color: #5d666f'" +
                ">" +
                generateSubtitle() +
                "<h1 style='  color: #8e24aa;" +
                "margin: 0;" +
                "font-size: 22px;" +
                "line-height: 150%'>" +
                date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                "</h1>" +
                "</p>" +
                "</td>" +
                "</tr>";
    }

    private String generateSubtitle() {
        if (type == ReceiptType.CREATION)
            return YOUR_SCHEDULE_IS_CONFIRMED_FOR;

        if (type == ReceiptType.CANCELLATION)
            return YOUR_SCHEDULE_IS_CANCELLED;

        return YOUR_SCHEDULE_IS_UPDATED;
    }

    private String generateReceiptImage() {
        return "<tr>" +
                "<td style='padding: 20px 40px 0px 40px;'>" +
                "<div style='margin-left: auto;" +
                "margin-right: auto;" +
                "width: 192px;" +
                "height: 192px;" +
                "background: #fff url('" + getImageUrl() + "') no-repeat center;" +
                "background-size: contain;'>" +
                "</div>" +
                "</td>" +
                "</tr>";
    }

    private String getImageUrl(){
        final String baseImageUrl = "https://raw.githubusercontent.com/lucaschf/DAW-2020/main/Tp1/web/images/";

        if (type == ReceiptType.CREATION)
            return baseImageUrl + "undraw_online_calendar.png";

        if (type == ReceiptType.CANCELLATION)
            return baseImageUrl + "undraw_startled.png";

        return baseImageUrl + "undraw_booking.png";
    }

    private String generateAdditionalInfo() {
        return "<tr>" +
                "<td>" +
                "<p style='font-size: 20px;" +
                "line-height: 150%;" +
                "font-weight: bold;" +
                "text-align: center;" +
                "margin: 0;" +
                "color: #5d666f'" +
                ">" +
                (type == ReceiptType.CREATION ? YOU_PERFORMED_A : type == ReceiptType.UPDATE ? YOU_CHANGED : YOU_CANCELLED) +
                "<span style='color:#8e24aa'>" + SCHEDULE + "</span>" +
                "</p>" +
                "</td>" +
                "</tr>";
    }

    private String generateCodeInfo(String confirmationCode) {
        return " <tr>" +
                "<td style='padding: 20px 40px 0px 40px; text-align: center'>" +
                "<p>" +
                "Código de confirmação:" +
                "</p>" +
                "</td>" +
                "</tr>" +
                "<tr>" +
                "<td style='padding: 0 40px 0 40px;'>" +
                "<p style=' padding: 10px;" +
                "max-width: 300px;" +
                "border-radius: 5px;" +
                "background-color: #8e24aa;" +
                "color: #ffffff;" +
                "margin: 0 auto;" +
                "font-size: 18px;" +
                "line-height: 150%;" +
                "text-align: center'" +
                ">" +
                confirmationCode +
                "</p>" +
                "</td>" +
                "</tr>"
                ;
    }

    private String generateBaseScheduleInfo(Schedule schedule) {
        return String.format("%s" +
                        "<tr>" +
                        "<td style='padding: 20px 40px 0px 40px;'>" +
                        "<table>" +
                        "<tbody>" +
                        "%s%s%s%s%s" +
                        "</tbody>" +
                        "</table>" +
                        "</td>" +
                        "</tr>",
                generateTrSeparator(),
                generateTrScheduleInfo("Email do agendador", schedule.getSchedulerEmail()),
                generateTrScheduleInfo("Local da visita", schedule.getMuseum().getName()),
                generateTrScheduleInfo("Data da visita", schedule.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))),
                generateTrScheduleInfo("Horário da visita", schedule.getHours().toString()),
                generateTrScheduleInfo("Visitantes", String.valueOf(schedule.getVisitorsCount()))
        );
    }

    private String generateVisitorsInfo(Schedule schedule) {
        var info = new StringBuilder();

        schedule.getVisitors().forEach(v -> info.append(generateTrScheduleInfo("Nome", v.getName()))
                .append(generateTrScheduleInfo("Cpf", v.getCpf().getNumeroFormatado()))
                .append(generateTrScheduleInfo("Tipo de ingresso", v.getTicketType().toString()))
                .append("<tr><td style='padding: 10px'></td></tr>")
        );

        return "<tr>" +
                "<td>" +
                "<p style='font-size: 20px;" +
                "line-height: 150%;" +
                "font-weight: bold;" +
                "text-align: center;" +
                "margin: 0;" +
                "color: #5d666f'>" +
                "Visitantes" +
                "</p>" +
                "</td>" +
                "</tr>" +
                generateTrSeparator() +
                generateInnerTable(info.toString());
    }

    private String generateInnerTable(String tableData) {
        return "<tr>" +
                "<td style='padding: 20px 40px 0px 40px;'>" +
                "<table style='width: 100%'>" +
                "<tbody>" +
                tableData +
                "</tbody>" +
                "</table>" +
                "</td>" +
                "</tr>";
    }

    private String generateTrSeparator() {
        return "<tr><td style='padding: 20px;'><hr/></td></tr>";
    }

    private String generateTrScheduleInfo(String name, String data) {
        return "<tr>" +
                // name
                "<td>" +
                "<p style='padding: 5px;" +
                "color: #5d666f;" +
                "margin: 0 30px 0 0;" +
                "font-size: 16px;" +
                "line-height: 150%;" +
                "text-align: left'>" +
                name +
                "</p>" +
                "</td>" +
                "<td'>" +
                "<p style='padding: 5px;" +
                "color: #5d666f;" +
                "margin: 0;" +
                "font-size: 16px;" +
                "line-height: 150%;" +
                "font-weight: bold;" +
                "text-align: right'>" +
                data +
                "</p>" +
                "</td>" +
                "</tr>";
    }

    public enum ReceiptType {CREATION, UPDATE, CANCELLATION}
}
