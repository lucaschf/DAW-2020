package br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.logic;

import br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.ScheduleDao;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Museum;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Schedule;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.service.EmailSenderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AddScheduleLogic implements Logic {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        var schedule = new Schedule();

        schedule.setSchedulerEmail(request.getParameter("email"));
        schedule.setDate(LocalDate.parse(request.getParameter("date"), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        schedule.setMuseum(new Museum().setId(Long.parseLong(request.getParameter("museum_id"))));
        schedule.setHours(LocalTime.parse(request.getParameter("time")));
        schedule.setVisitorsCount(Integer.parseInt(request.getParameter("visitors")));

        System.out.println(schedule);

        String url = "/schedules-list.jsp";

        if (!new ScheduleDao().add(schedule)) {
            url = "/server-error.jsp";

            request.setAttribute("message", "Não foi possível realizar o agendamento. Verifique os dados e tente novamente.");
            request.setAttribute("source_page", "schedule.jsp");
        }

//        EmailSenderService.sendEmailMessage()
        request.getRequestDispatcher(url).forward(request, response);
    }
}
