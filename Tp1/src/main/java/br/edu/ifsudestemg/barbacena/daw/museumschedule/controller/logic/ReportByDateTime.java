package br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.logic;

import br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.PagesNames;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalTime;

public class ReportByDateTime implements Logic {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        var date = LocalDate.parse(request.getParameter("date"));
        var time = LocalTime.parse(request.getParameter("time"));
        Long museumId = null;
        User user = getCurrentUser(request);

        if (!user.isSystemAdmin())
            museumId = getCurrentUser(request).getEmployee().getId();

        request.setAttribute("date", date);
        request.setAttribute("time", time);
        request.setAttribute("museum_id", museumId);

        return PagesNames.REPORT_VISITORS_BY_DATE_TIME;
    }

    private User getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (User) session.getAttribute("user");
    }
}
