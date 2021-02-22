package br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.logic;

import br.com.caelum.stella.tinytype.CPF;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Schedule;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Visitor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RemoveVisitorLogic implements Logic {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        final String scheduleAttr = "schedule";
        String sourcePage = "/schedule_museum_add_visitors.jsp";

        Schedule schedule = (Schedule) request.getSession().getAttribute(scheduleAttr);
        request.getSession().removeAttribute(scheduleAttr);


        Visitor visitor = new Visitor();
        visitor.setCpf(new CPF(request.getParameter("cpf")));

        schedule.removeVisitorByCpf(visitor.getCpf());
        request.setAttribute(scheduleAttr, schedule);
        request.getRequestDispatcher(sourcePage).forward(request, response);
    }
}
