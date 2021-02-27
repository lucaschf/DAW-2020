package br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.logic;

import br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.PagesNames;
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
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        final String scheduleAttr = "schedule";
        String url = PagesNames.SCHEDULE_EDIT;

        Schedule schedule = (Schedule) request.getSession().getAttribute(scheduleAttr);
        request.getSession().removeAttribute(scheduleAttr);

        Message message;

        if (!new ScheduleDao().add(schedule)) {
            message = new Message(Constants.FAILED_TO_SCHEDULE);
            url = PagesNames.ADD_VISITORS_AND_CONFIRM_SCHEDULE;
        } else {
            insertVisitors(schedule);
            schedule = new ScheduleDao().fetchById(schedule.getId());
            EmailSenderService.sendEmailMessage(
                    Constants.SCHEDULE_CONFIRMED,
                    schedule.getSchedulerEmail(), new ScheduleHtmlReceiptGenerator(schedule, CREATION).generateReceipt()
            );

            message = new Message(Constants.SCHEDULE_CONFIRMED, SUCCESS);
        }

        request.setAttribute(scheduleAttr, schedule);
        request.setAttribute(MESSAGE_PARAM, message);

        return url;
    }

    private void insertVisitors(Schedule schedule) {
        schedule.getVisitors().forEach(v -> v.setScheduleID(schedule.getId()));
        new VisitorsDao().add(schedule.getVisitors());
    }
}
