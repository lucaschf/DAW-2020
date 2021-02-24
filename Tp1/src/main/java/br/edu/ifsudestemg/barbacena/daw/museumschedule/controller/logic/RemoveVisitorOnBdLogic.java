package br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.logic;

import br.com.caelum.stella.tinytype.CPF;
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

public class RemoveVisitorOnBdLogic implements Logic {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Visitor visitor = new Visitor();

        final String scheduleAttr = "schedule";
        Schedule schedule = (Schedule) request.getSession().getAttribute(scheduleAttr);
        request.getSession().removeAttribute(scheduleAttr);

        visitor.setCpf(new CPF(request.getParameter("cpf")));
        visitor.setScheduleID(Long.parseLong(request.getParameter("schedule_id")));

        if (new VisitorsDao().remove(visitor)) {
            schedule = new ScheduleDao().fetchById(schedule.getId());
            EmailSenderService.sendEmailMessage(SCHEDULE_UPDATED,
                    schedule.getSchedulerEmail(),
                    new ScheduleHtmlReceiptGenerator(schedule, UPDATE).generateReceipt()
            );

            request.setAttribute(MESSAGE_PARAM, new Message(VISITOR_REMOVED, SUCCESS));
        } else
            request.setAttribute(MESSAGE_PARAM, new Message(FAIL_TO_REMOVE_VISITOR));

        request.setAttribute(scheduleAttr, schedule);
        request.getRequestDispatcher("schedule_cancel.jsp").forward(request, response);
    }
}
