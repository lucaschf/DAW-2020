package br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.logic;

import br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.EmployeeDao;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Message;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Message.MessageType.SUCCESS;
import static br.edu.ifsudestemg.barbacena.daw.museumschedule.util.Constants.*;

public class RemoveEmployee implements Logic {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        var url = "employee_list.jsp";

        if (new EmployeeDao().remove(Long.parseLong(request.getParameter("employee_id")))) {
            request.setAttribute(MESSAGE_PARAM, new Message(EMPLOYEE_SUCCESSFUL_REMOVED, SUCCESS));
        } else
            request.setAttribute(MESSAGE_PARAM, new Message(FAIL_TO_REMOVE_EMPLOYEE));

        request.getRequestDispatcher(url).forward(request, response);
    }
}
