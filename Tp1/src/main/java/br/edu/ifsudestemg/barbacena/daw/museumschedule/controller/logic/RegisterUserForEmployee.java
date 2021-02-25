package br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.logic;

import br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.UserDao;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Employee;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Message;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.User;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.util.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Message.MessageType.SUCCESS;

public class RegisterUserForEmployee implements Logic {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        final String employeeAttr = "employee";
        Employee employee = (Employee) request.getSession().getAttribute(employeeAttr);
        request.getSession().removeAttribute(employeeAttr);

        var username = request.getParameter("username");
        var password = request.getParameter("password");

        var url = "employee_search.jsp";

        var user = new User();
        user.setPassword(password);
        user.setUsername(username);
        user.setRole(User.Role.EMPLOYEE);
        user.setEmployeeId(employee.getId());

        if (!request.getParameter("confirmation_password").equals(password)) {
            request.setAttribute(MESSAGE_PARAM, new Message(Constants.PASSWORDS_DOESNT_MATCH));
            request.setAttribute("username", username);
            request.setAttribute(employeeAttr, employee);
            url = "user_registration.jsp";
        } else if (!new UserDao().add(user)) {
            request.setAttribute(MESSAGE_PARAM, new Message(Constants.FAIL_TO_ADD_USER));
            request.setAttribute("username", username);
            request.setAttribute(employeeAttr, employee);
            url = "user_registration.jsp";
        } else {
            request.setAttribute(MESSAGE_PARAM, new Message(Constants.USER_SUCCESSFUL_ADDED, SUCCESS));
        }

        request.getRequestDispatcher(url).forward(request, response);
    }
}
