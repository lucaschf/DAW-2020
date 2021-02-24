package br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.logic;

import br.com.caelum.stella.tinytype.CPF;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Message;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Schedule;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.TicketType;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Visitor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static br.edu.ifsudestemg.barbacena.daw.museumschedule.util.Constants.FAIL_TO_ADD_VISITOR;
import static br.edu.ifsudestemg.barbacena.daw.museumschedule.util.Constants.INVALID_CPF;

public class AddVisitorLogic implements Logic {

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
        String url = "/schedule_museum_add_visitors.jsp";
        String sourcePage = "/schedule_museum_add_visitors.jsp";


        if (!visitor.getCpf().isValido()) {
            request.setAttribute(MESSAGE_PARAM, new Message("", INVALID_CPF));
            return sourcePage;
        }

        if(!schedule.addVisitor(visitor)){
            request.setAttribute(MESSAGE_PARAM, new Message("", FAIL_TO_ADD_VISITOR));
            return sourcePage;
        }

        return url;
    }
}
