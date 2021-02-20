package br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.logic;

import br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.ScheduleDao;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Schedule;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CancelScheduleLogic implements Logic {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        var schedule = new Schedule();

        schedule.setConfirmationCode(request.getParameter("code"));

        String url = "/schedules-list.jsp";

        if (!new ScheduleDao().remove(schedule)) {
            url = "/server-error.jsp";

            request.setAttribute("message", "Não foi possível cancelar o agendamento. Verifique os dados e tente novamente.");
            request.setAttribute("source_page", "schedule.jsp");
        }

//        EmailSenderService.sendEmailMessage()
        request.getRequestDispatcher(url).forward(request, response);
    }
}