package br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.logic;

import br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.ScheduleDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditScheduleLogic implements Logic {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String code = request.getParameter("code");

        var schedule = new ScheduleDao().fetchByCode(code);

        String url;
        if (schedule != null) {
            request.setAttribute("schedule", schedule);
            url = "schedule.jsp";
        } else url = "schedules-list.jsp";

        request.getRequestDispatcher(url).forward(request, response);
    }
}