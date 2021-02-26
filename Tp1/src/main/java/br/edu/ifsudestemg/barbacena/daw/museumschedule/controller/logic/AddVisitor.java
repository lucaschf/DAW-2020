package br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.logic;

import br.com.caelum.stella.tinytype.CPF;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.VisitorsDao;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Message;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Schedule;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.TicketType;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Visitor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static br.edu.ifsudestemg.barbacena.daw.museumschedule.util.Constants.FAIL_TO_ADD_VISITOR;
import static br.edu.ifsudestemg.barbacena.daw.museumschedule.util.Constants.INVALID_CPF;

public class AddVisitor implements Logic {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        final String scheduleAttr = "schedule";
        Schedule schedule = (Schedule) request.getSession().getAttribute(scheduleAttr);
        request.getSession().removeAttribute(scheduleAttr);

        var visitor = new Visitor();
        visitor.setCpf(new CPF(request.getParameter("cpf")));
        visitor.setName(request.getParameter("visitorName"));
        visitor.setTicketType(TicketType.from(Integer.parseInt(request.getParameter("ticket"))));

        String url = processData(request, schedule, visitor);

        request.setAttribute(scheduleAttr, schedule);
        request.getRequestDispatcher(url).forward(request, response);
    }

    private String processData(HttpServletRequest request, Schedule schedule, Visitor visitor) {
        String url = "/schedule_add_visitors_and_confirm.jsp";
        String sourcePage = "/schedule_add_visitors_and_confirm.jsp";


        if (!visitor.getCpf().isValido()) {
            request.setAttribute(MESSAGE_PARAM, new Message(INVALID_CPF));
            putBackData(request, visitor);
            return sourcePage;
        }

        if (new VisitorsDao().isAlreadyBooked(visitor.getCpf(), schedule.getDate(), schedule.getHours())) {
            request.setAttribute(MESSAGE_PARAM, new Message("Ja existe um agendamento para este vistante no horario informado"));
            putBackData(request, visitor);
            return sourcePage;
        }

        if (!schedule.addVisitor(visitor)) {
            request.setAttribute(MESSAGE_PARAM, new Message(FAIL_TO_ADD_VISITOR));
            putBackData(request, visitor);
            return sourcePage;
        }

        return url;
    }

    private void putBackData(HttpServletRequest request, Visitor visitor){
        request.setAttribute("cpf", visitor.getCpf().getNumero());
        request.setAttribute("visitorName", visitor.getName());
        request.setAttribute("ticket", visitor.getTicketType().getCode());
    }

}
