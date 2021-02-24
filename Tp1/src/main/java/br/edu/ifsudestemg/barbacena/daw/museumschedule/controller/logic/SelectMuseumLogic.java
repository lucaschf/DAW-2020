package br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.logic;

import br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.MuseumDAO;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Message;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Schedule;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static br.edu.ifsudestemg.barbacena.daw.museumschedule.util.Constants.*;

public class SelectMuseumLogic implements Logic {

    private final MuseumDAO dao = new MuseumDAO();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        var schedule = getScheduleFromRequest(request);

        request.getRequestDispatcher(processData(schedule, request))
                .forward(request, response);
    }

    private Schedule getScheduleFromRequest(HttpServletRequest request) {
        var museum = dao.fetchById(Long.parseLong(request.getParameter("museum_id")));

        Schedule schedule = new Schedule();
        schedule.setSchedulerEmail(request.getParameter("email"));
        schedule.setDate(LocalDate.parse(request.getParameter("date")));
        schedule.setMuseum(museum);
        try {
            schedule.setHours(LocalTime.parse(request.getParameter("time")));
        } catch (Exception e) {
            schedule.setHours(null);
        }
        schedule.setVisitorsCount(Integer.parseInt(request.getParameter("visitors")));
        var termsAccepted = request.getParameter("termsAccepted").equalsIgnoreCase("on");
        schedule.setTermsAcceptanceDate(termsAccepted ? LocalDateTime.now() : null);

        return schedule;
    }

    private String processData(Schedule schedule, HttpServletRequest request) {
        var museum = schedule.getMuseum();

        request.setAttribute("schedule", schedule);

        var visitHour = schedule.getHours();

        String sourcePage = "/schedule_museum_select.jsp";
        if (visitHour == null) {
            request.setAttribute(MESSAGE_PARAM, new Message(INVALID_TIME));
            return sourcePage;
        }

        var scheduleDateTime = schedule.getDate().atTime(visitHour);
        if (scheduleDateTime.isBefore(LocalDateTime.now())) {
            request.setAttribute(MESSAGE_PARAM, new Message(SCHEDULING_CANNOT_BE_DATE_EARLIER_THAN_CURRENT_DATE));
            return sourcePage;
        }

        var availableHours = dao.fetchAvailableTimes(museum.getId(), schedule.getDate());
        if (availableHours.isEmpty() || visitHour.isAfter(museum.getClosesAt()) || visitHour.isBefore(museum.getOpensAt())) {
            request.setAttribute(MESSAGE_PARAM, new Message(THE_MUSEUM_IS_CLOSED_AT_INFORMED_TIME));
            return sourcePage;
        }

        var v = availableHours.stream().filter(a -> a.getHour().equals(visitHour)).findAny();
        if (!v.isPresent() || v.get().getVacations() < schedule.getVisitorsCount()) {
            request.setAttribute(MESSAGE_PARAM, new Message(THERE_ARE_NOT_ENOUGH_PLACES_FOR_THIS_SCHEDULE));
            return sourcePage;
        }

        if (schedule.getTermsAcceptanceDate() == null) {
            request.setAttribute(MESSAGE_PARAM, new Message(YOU_MUST_ACCEPT_THE_TERMS));
            return sourcePage;
        }

        return "/schedule_museum_add_visitors.jsp";
    }
}
