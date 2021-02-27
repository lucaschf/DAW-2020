package br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.logic;

import br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.PagesNames;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.ScheduleDao;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Message;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Schedule;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.service.EmailSenderService;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.util.ScheduleHtmlReceiptGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Message.MessageType.SUCCESS;
import static br.edu.ifsudestemg.barbacena.daw.museumschedule.util.Constants.FAIL_TO_CANCEL_SCHEDULE;
import static br.edu.ifsudestemg.barbacena.daw.museumschedule.util.Constants.SCHEDULE_CANCELLATION;
import static br.edu.ifsudestemg.barbacena.daw.museumschedule.util.ScheduleHtmlReceiptGenerator.ReceiptType.CANCELLATION;

public class CancelSchedule implements Logic {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        final String scheduleAttr = "schedule";
        Schedule schedule = (Schedule) request.getSession().getAttribute(scheduleAttr);
        request.getSession().removeAttribute(scheduleAttr);

        var message = processData(schedule);
        request.setAttribute(MESSAGE_PARAM, message);

        if(message.isSuccess())
            return PagesNames.SCHEDULE_SEARCH;

        request.setAttribute(scheduleAttr, schedule);
        return PagesNames.SCHEDULE_EDIT;
    }

    private Message processData(Schedule schedule) {
        if (new ScheduleDao().remove(schedule)) {
            sendCancellationEmail(schedule);
            return new Message(String.format("Agendamento %s cancelado com sucesso", schedule.getConfirmationCode()))
                    .setType(SUCCESS);
        }

        return new Message(FAIL_TO_CANCEL_SCHEDULE) ;
    }

    private void sendCancellationEmail(Schedule schedule) {
        EmailSenderService.sendEmailMessage(
                SCHEDULE_CANCELLATION,
                schedule.getSchedulerEmail(),
                new ScheduleHtmlReceiptGenerator(schedule, CANCELLATION).generateReceipt());
    }
}
