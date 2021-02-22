package br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.logic;

import br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.ScheduleDao;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.VisitorsDao;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.ErrorMessage;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Schedule;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.service.EmailSenderService;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.util.ScheduleHtmlReceiptGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConfirmScheduleLogic implements Logic {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        final String scheduleAttr = "schedule";
        String sourcePage = "/schedule_museum_add_visitors.jsp";
        String url = "/schedules-list.jsp";

        Schedule schedule = (Schedule) request.getSession().getAttribute(scheduleAttr);
        request.getSession().removeAttribute(scheduleAttr);

        if (!new ScheduleDao().add(schedule)) {
            request.setAttribute("errorMessage", new ErrorMessage("", "Falha ao realizar agendamento"));
            request.setAttribute(scheduleAttr, schedule);
            request.getRequestDispatcher(sourcePage).forward(request, response);
        }

        schedule.getVisitors().forEach(v -> v.setScheduleID(schedule.getId()));
        new VisitorsDao().add(schedule.getVisitors());
        EmailSenderService.sendEmailMessage("Confirmação de agendamento",
                schedule.getSchedulerEmail(),
                new ScheduleHtmlReceiptGenerator(schedule).generateReceipt());

        request.getRequestDispatcher(url).forward(request, response);
    }
}
