package br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.logic;

import br.com.caelum.stella.tinytype.CPF;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.PagesNames;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.VisitorsDao;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Message;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Schedule;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.TicketType;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Visitor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static br.edu.ifsudestemg.barbacena.daw.museumschedule.util.Constants.*;

public class AddVisitor implements Logic {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        final String scheduleAttr = "schedule";
        Schedule schedule = (Schedule) request.getSession().getAttribute(scheduleAttr);
        request.getSession().removeAttribute(scheduleAttr);

        Visitor visitor = getVisitorDataFromRequest(request);
        var message = processData(schedule, visitor);

        if (message != null) {
            putBackData(request, visitor);
        }

        request.setAttribute(scheduleAttr, schedule);
        return PagesNames.ADD_VISITORS_AND_CONFIRM_SCHEDULE;
    }

    private Visitor getVisitorDataFromRequest(HttpServletRequest request) {
        var visitor = new Visitor();
        visitor.setCpf(new CPF(request.getParameter("cpf")));
        visitor.setName(request.getParameter("visitorName"));
        visitor.setTicketType(TicketType.from(Integer.parseInt(request.getParameter("ticket"))));

        return visitor;
    }

    private Message processData(Schedule schedule, Visitor visitor) {

        if (!visitor.getCpf().isValido()) {
            return new Message(INVALID_CPF);
        }

        if (new VisitorsDao().isAlreadyBooked(visitor.getCpf(), schedule.getDate(), schedule.getHours())) {
            return new Message(THERE_IS_ANOTHER_SCHEDULE_AT_THIS_TIME);
        }

        if (!schedule.addVisitor(visitor)) {
            return new Message(FAIL_TO_ADD_VISITOR);
        }

        return null;
    }

    private void putBackData(HttpServletRequest request, Visitor visitor) {
        request.setAttribute("cpf", visitor.getCpf().getNumero());
        request.setAttribute("visitorName", visitor.getName());
        request.setAttribute("ticket", visitor.getTicketType().getCode());
    }
}
