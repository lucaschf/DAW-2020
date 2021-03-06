package br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.logic;

import br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.PagesNames;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.ScheduleDao;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Message;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

import static br.edu.ifsudestemg.barbacena.daw.museumschedule.util.Constants.NO_SCHEDULE_FOUND;

public class SearchSchedule implements Logic {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        var email = request.getParameter("email");
        var confirmationCode = request.getParameter("confirmationCode");

        var schedule = new ScheduleDao().fetchByEmailAndCode(email, confirmationCode);

        if (schedule == null) {
            request.setAttribute("email", email);
            request.setAttribute("confirmationCode", confirmationCode);
            request.setAttribute(MESSAGE_PARAM, new Message(NO_SCHEDULE_FOUND));
            return PagesNames.SCHEDULE_SEARCH;
        }

        var scheduleDateTime = schedule.getDate().atTime(schedule.getHours());
        if (LocalDateTime.now().isAfter(scheduleDateTime)) {
            request.setAttribute(MESSAGE_PARAM, new Message(NO_SCHEDULE_FOUND));
        }

        request.setAttribute("schedule", schedule);
        return PagesNames.SCHEDULE_EDIT;
    }
}
