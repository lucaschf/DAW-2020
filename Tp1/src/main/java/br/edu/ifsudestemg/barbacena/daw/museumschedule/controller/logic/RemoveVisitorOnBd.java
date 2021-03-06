package br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.logic;

import br.com.caelum.stella.tinytype.CPF;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.PagesNames;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.ScheduleDao;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.VisitorsDao;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Message;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Schedule;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Visitor;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.service.EmailSenderService;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.util.ScheduleHtmlReceiptGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Message.MessageType.SUCCESS;
import static br.edu.ifsudestemg.barbacena.daw.museumschedule.util.Constants.*;
import static br.edu.ifsudestemg.barbacena.daw.museumschedule.util.ScheduleHtmlReceiptGenerator.ReceiptType.UPDATE;

public class RemoveVisitorOnBd implements Logic {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Visitor visitor = new Visitor();

        final String scheduleAttr = "schedule";
        Schedule schedule = (Schedule) request.getSession().getAttribute(scheduleAttr);
        request.getSession().removeAttribute(scheduleAttr);

        visitor.setCpf(new CPF(request.getParameter("cpf")));
        visitor.setScheduleID(Long.parseLong(request.getParameter("schedule_id")));

        Message message;

        if (new VisitorsDao().remove(visitor)) {
            schedule = new ScheduleDao().fetchById(schedule.getId());
            sendUpdateEmail(schedule);
            message = new Message(VISITOR_REMOVED, SUCCESS);
        } else
            message = new Message(FAIL_TO_REMOVE_VISITOR);

        request.setAttribute(MESSAGE_PARAM, message);
        request.setAttribute(scheduleAttr, schedule);

        return PagesNames.SCHEDULE_EDIT;
    }

    private void sendUpdateEmail(Schedule schedule) {
        EmailSenderService.sendEmailMessage(
                SCHEDULE_UPDATED,
                schedule.getSchedulerEmail(),
                new ScheduleHtmlReceiptGenerator(schedule, UPDATE).generateReceipt()
        );
    }
}
