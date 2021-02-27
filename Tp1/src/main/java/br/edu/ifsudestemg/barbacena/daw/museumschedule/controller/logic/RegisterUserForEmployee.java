package br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.logic;

import br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.PagesNames;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.UserDao;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Employee;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Message;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.User;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.util.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Message.MessageType.SUCCESS;

public class RegisterUserForEmployee implements Logic {

    private final String employeeAttr = "employee";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        var user = getUserDataFromRequest(request);

        var message = processData(user);

        request.setAttribute(MESSAGE_PARAM, message);
        if (message.isSuccess())
            return PagesNames.EMPLOYEE_SEARCH;

        request.setAttribute("username", user.getUsername());
        request.setAttribute(employeeAttr, user.getEmployee());
        return PagesNames.USER_REGISTRATION;
    }

    private Message processData(User user) {
        if (!user.confirmationPasswordMatch()) {
            return new Message(Constants.PASSWORDS_DOESNT_MATCH);
        }

        if (!new UserDao().add(user)) {
            return new Message(Constants.FAIL_TO_ADD_USER);
        }

        return new Message(Constants.USER_SUCCESSFUL_ADDED, SUCCESS);
    }

    private User getUserDataFromRequest(HttpServletRequest request) {
        Employee employee = (Employee) request.getSession().getAttribute(employeeAttr);
        request.getSession().removeAttribute(employeeAttr);

        var username = request.getParameter("username");
        var password = request.getParameter("password");

        var user = new User();
        user.setPassword(password);
        user.setUsername(username);
        user.setRole(User.Role.EMPLOYEE);
        user.setEmployee(employee);
        user.setConfirmationPassword(request.getParameter("confirmation_password"));

        return user;
    }
}
