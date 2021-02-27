package br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.logic;

import br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.PagesNames;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.ScheduleDao;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.VisitorsDao;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Message;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Schedule;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.util.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Message.MessageType.SUCCESS;
import static br.edu.ifsudestemg.barbacena.daw.museumschedule.util.Constants.FAIL_TO_CHECK_IN;

public class CheckinAll implements Logic {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        final String scheduleAttr = "schedule";
        Schedule schedule = (Schedule) request.getSession().getAttribute(scheduleAttr);
        request.getSession().removeAttribute(scheduleAttr);

        Message message;
        if (new VisitorsDao().checkinAll(schedule)) {
            message = new Message(
                    String.format("%s %s %s",
                            Constants.VISITORS_OF_SCHEDULE,
                            schedule.getConfirmationCode(),
                            Constants.SUCCESSFUL_CONFIRMED),
                    SUCCESS
            );
            request.setAttribute(scheduleAttr, new ScheduleDao().fetchById(schedule.getId()));
        } else
            message = new Message(FAIL_TO_CHECK_IN);

        request.setAttribute(MESSAGE_PARAM, message);
        return PagesNames.SCHEDULE_EDIT;
    }
}
