package br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.logic;

import br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.ScheduleDao;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.ErrorMessage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static br.edu.ifsudestemg.barbacena.daw.museumschedule.util.Constants.NO_SCHEDULE_FOUND;

public class SearchScheduleForCancelLogic implements Logic {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String sourcePage = "/schedule_cancel_form.jsp";
        String nextPage = "/schedule_cancel.jsp";

        var email = request.getParameter("email");
        var confirmationCode = request.getParameter("confirmationCode");

        var schedule = new ScheduleDao().fetchByEmailAndCode(email, confirmationCode);

        if (schedule == null) {
            request.setAttribute("email", email);
            request.setAttribute("confirmationCode", confirmationCode);
            request.setAttribute("errorMessage", new ErrorMessage(NO_SCHEDULE_FOUND));
            request.getRequestDispatcher(sourcePage).forward(request, response);
        }

        System.out.println(schedule);

        request.setAttribute("schedule", schedule);
        request.getRequestDispatcher(nextPage).forward(request, response);
    }
}
