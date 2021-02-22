package br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.logic;

import br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.MuseumDAO;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.BookedTime;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.ErrorMessage;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Schedule;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.util.FormatterUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

import static br.edu.ifsudestemg.barbacena.daw.museumschedule.util.Constants.*;

public class SelectMuseumLogic implements Logic {

    private final MuseumDAO dao = new MuseumDAO();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        var schedule = new Schedule();

        var museum = dao.fetchById(Long.parseLong(request.getParameter("museum_id")));

        schedule.setSchedulerEmail(request.getParameter("email"));
        schedule.setDate(LocalDate.parse(request.getParameter("date"),
                new FormatterUtils().getBrazilianDateFormatter()));
        schedule.setMuseum(museum);
        schedule.setHours(LocalTime.parse(request.getParameter("time")));
        schedule.setVisitorsCount(Integer.parseInt(request.getParameter("visitors")));
        var termsAccepted = request.getParameter("termsAccepted").equalsIgnoreCase("on");
        schedule.setTermsAcceptanceDate(termsAccepted ? LocalDateTime.now() : null);

        request.getRequestDispatcher(processData(schedule, request))
                .forward(request, response);
    }

    private String processData(Schedule schedule, HttpServletRequest request) {
        var museum = schedule.getMuseum();
        var availableHours = dao.fetchAvailableTimes(museum.getId(), schedule.getDate());
        String sourcePage = "/schedule_museum_select.jsp";
        String url = "/schedule_museum_add_visitors.jsp";

        request.setAttribute("schedule", schedule);

        var scheduleDateTime = schedule.getDate().atStartOfDay()
                .withHour(schedule.getHours().getHour())
                .withMinute(schedule.getHours().getMinute());

        if (scheduleDateTime.isBefore(LocalDateTime.now())) {
            request.setAttribute("errorMessage",
                    new ErrorMessage("", SCHEDULING_CANNOT_BE_DATE_EARLIER_THAN_CURRENT_DATE));

            return sourcePage;
        }

        if (availableHours.isEmpty() || schedule.getHours().isAfter(museum.getClosesAt()) || schedule.getHours().isBefore(museum.getOpensAt())) {
            request.setAttribute("errorMessage",
                    new ErrorMessage("", THE_MUSEUM_IS_CLOSED_AT_INFORMED_TIME));

            return sourcePage;
        }

        var v = availableHours.stream().filter(a -> a.getHour().equals(schedule.getHours())).findAny();
        if (!v.isPresent() || v.get().getVacations() < schedule.getVisitorsCount()) {
            request.setAttribute(
                    "errorMessage",
                    new ErrorMessage("", THERE_ARE_NOT_ENOUGH_PLACES_FOR_THIS_SCHEDULE));

            return sourcePage;
        }

        if(schedule.getTermsAcceptanceDate() == null)
        {
            request.setAttribute(
                    "errorMessage",
                    new ErrorMessage("", YOU_MUST_ACCEPT_THE_TERMS));

            return sourcePage;

        }

        return url;
    }
}
