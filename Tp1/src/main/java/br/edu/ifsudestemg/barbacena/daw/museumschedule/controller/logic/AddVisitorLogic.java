package br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.logic;

import br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.ScheduleDao;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.VisitorsDao;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.TicketType;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Visitor;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.util.MaskUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddVisitorLogic implements Logic {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        var visitor = new Visitor();

        visitor.setScheduleCode(request.getParameter("scheduleCode"));
        visitor.setCpf(MaskUtil.unmask(request.getParameter("cpf")));
        visitor.setName(request.getParameter("visitorName"));
        visitor.setTicketType(TicketType.from(Integer.parseInt(request.getParameter("ticket"))));

        String url = "/schedule.jsp";
        new VisitorsDao().add(visitor);

        request.setAttribute("schedule", new ScheduleDao().fetchByCode(visitor.getScheduleCode()));

        request.getRequestDispatcher(url).forward(request, response);
    }
}
