package br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.logic;

import br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.ScheduleDao;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.VisitorsDao;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Message;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Schedule;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.service.EmailSenderService;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.util.Constants;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.util.ScheduleHtmlReceiptGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Message.MessageType.SUCCESS;
import static br.edu.ifsudestemg.barbacena.daw.museumschedule.util.ScheduleHtmlReceiptGenerator.ReceiptType.CREATION;

public class ConfirmSchedule implements Logic {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        final String scheduleAttr = "schedule";
        String sourcePage = "/schedule_add_visitors_and_confirm.jsp";
        String url = "/schedule_edit.jsp";

        Schedule schedule = (Schedule) request.getSession().getAttribute(scheduleAttr);
        request.getSession().removeAttribute(scheduleAttr);

        if (!new ScheduleDao().add(schedule)) {
            request.setAttribute(MESSAGE_PARAM, new Message("", Constants.FAILED_TO_SCHEDULE));
            request.setAttribute(scheduleAttr, schedule);
            request.getRequestDispatcher(sourcePage).forward(request, response);
            return;
        }

        schedule.getVisitors().forEach(v -> v.setScheduleID(schedule.getId()));
        new VisitorsDao().add(schedule.getVisitors());
        request.setAttribute(scheduleAttr, new ScheduleDao().fetchById(schedule.getId()));
        request.setAttribute(MESSAGE_PARAM, new Message(Constants.SCHEDULE_CONFIRMED, SUCCESS));
        EmailSenderService.sendEmailMessage(Constants.SCHEDULE_CONFIRMED,
                schedule.getSchedulerEmail(),
                new ScheduleHtmlReceiptGenerator(schedule, CREATION)
                        .generateReceipt());

        request.getRequestDispatcher(url).forward(request, response);
    }
}
