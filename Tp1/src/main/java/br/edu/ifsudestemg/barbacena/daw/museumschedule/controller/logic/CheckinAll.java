package br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.logic;

import br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.ScheduleDao;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.VisitorsDao;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Message;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Schedule;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Message.MessageType.SUCCESS;
import static br.edu.ifsudestemg.barbacena.daw.museumschedule.util.Constants.FAIL_TO_CHECK_IN;

public class CheckinAll implements Logic {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        final String scheduleAttr = "schedule";
        Schedule schedule = (Schedule) request.getSession().getAttribute(scheduleAttr);
        request.getSession().removeAttribute(scheduleAttr);

        if (new VisitorsDao().checkinAll(schedule)) {
            request.setAttribute(MESSAGE_PARAM,
                    new Message(String.format("Visitantes do agendamento %s confirmados com sucesso", schedule.getConfirmationCode()))
                            .setType(SUCCESS));
        } else
            request.setAttribute(MESSAGE_PARAM, new Message(FAIL_TO_CHECK_IN));

        request.setAttribute(scheduleAttr, new ScheduleDao().fetchById(schedule.getId()));
        request.getRequestDispatcher("schedule_edit.jsp").forward(request, response);
    }
}
