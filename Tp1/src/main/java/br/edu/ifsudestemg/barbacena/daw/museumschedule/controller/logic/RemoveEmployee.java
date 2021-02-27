package br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.logic;

import br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.PagesNames;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.EmployeeDao;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Message;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Message.MessageType.SUCCESS;
import static br.edu.ifsudestemg.barbacena.daw.museumschedule.util.Constants.*;

public class RemoveEmployee implements Logic {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Message message;

        if (new EmployeeDao().remove(Long.parseLong(request.getParameter("employee_id")))) {
            message = new Message(EMPLOYEE_SUCCESSFUL_REMOVED, SUCCESS);
        } else
            message = new Message(FAIL_TO_REMOVE_EMPLOYEE);

        request.setAttribute(MESSAGE_PARAM, message);

        return PagesNames.EMPLOYEE_LIST;
    }
}
