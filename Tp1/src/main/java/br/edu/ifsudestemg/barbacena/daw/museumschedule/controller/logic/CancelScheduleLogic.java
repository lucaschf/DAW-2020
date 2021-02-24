package br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.logic;

import br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.ScheduleDao;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Message;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Message.MessageType;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Schedule;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.service.EmailSenderService;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.util.ScheduleHtmlReceiptGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Message.MessageType.*;
import static br.edu.ifsudestemg.barbacena.daw.museumschedule.util.Constants.FAIL_TO_CANCEL_SCHEDULE;
import static br.edu.ifsudestemg.barbacena.daw.museumschedule.util.Constants.SCHEDULE_CANCELLATION;

public class CancelScheduleLogic implements Logic {


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        final String scheduleAttr = "schedule";
        Schedule schedule = (Schedule) request.getSession().getAttribute(scheduleAttr);
        request.getSession().removeAttribute(scheduleAttr);

        if (new ScheduleDao().remove(schedule)) {
            request.setAttribute(MESSAGE_PARAM,
                    new Message(String.format("Agendamento %s cancelado com sucesso", schedule.getConfirmationCode()))
                            .setType(SUCCESS));
            EmailSenderService.sendEmailMessage(SCHEDULE_CANCELLATION,
                    schedule.getSchedulerEmail(),
                    new ScheduleHtmlReceiptGenerator(schedule, ScheduleHtmlReceiptGenerator.ReceiptType.CANCELLATION).generateReceipt());
            request.getRequestDispatcher("search_schedule.jsp").forward(request, response);
        }

        request.setAttribute(MESSAGE_PARAM, new Message(FAIL_TO_CANCEL_SCHEDULE));
        request.setAttribute(scheduleAttr, schedule);
        request.getRequestDispatcher("schedule_cancel.jsp").forward(request, response);
    }
}
