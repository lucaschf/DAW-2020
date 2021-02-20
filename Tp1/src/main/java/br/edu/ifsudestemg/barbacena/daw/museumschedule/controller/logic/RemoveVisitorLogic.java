package br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.logic;

import br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.ScheduleDao;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.VisitorsDao;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Visitor;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.util.MaskUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RemoveVisitorLogic implements Logic {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Visitor visitor = new Visitor();

        visitor.setScheduleCode(request.getParameter("scheduleCode"));
        visitor.setCpf(MaskUtil.unmask(request.getParameter("cpf")));

        String url = "/schedule.jsp";
        new VisitorsDao().remove(visitor);

        request.setAttribute("schedule", new ScheduleDao().fetchByCode(visitor.getScheduleCode()));

        request.getRequestDispatcher(url).forward(request, response);
    }
}
