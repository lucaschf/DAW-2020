package br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.logic;

import br.com.caelum.stella.tinytype.CPF;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.PagesNames;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.EmployeeDao;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.UserDao;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Employee;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Message;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Museum;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Message.MessageType.SUCCESS;
import static br.edu.ifsudestemg.barbacena.daw.museumschedule.util.Constants.*;

public class AddEmployee implements Logic {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Employee employee = new Employee();
        var message = getEmployeeDataFromRequest(request, employee);

        if (message == null) {
            if (new EmployeeDao().add(employee)) {
                message = new Message(EMPLOYEE_SUCCESSFUL_ADDED, SUCCESS);
            } else {
                putBackData(request, employee);
                message = new Message(FAIL_TO_ADD_EMPLOYEE);
            }
        }

        request.setAttribute(MESSAGE_PARAM, message);
        return PagesNames.EMPLOYEE_REGISTRATION;
    }

    private Message getEmployeeDataFromRequest(HttpServletRequest request, Employee out) {
        var name = request.getParameter("name");
        var cpf = new CPF(request.getParameter("cpf"));
        long museum_id = Long.parseLong(request.getParameter("museum_id"));

        out.setMuseum(new Museum().setId(museum_id));
        out.setCpf(cpf);
        out.setName(name);

        if (!cpf.isValido()) {
            putBackData(request, out);
            return new Message(INVALID_CPF);
        }

        return null;
    }

    private void putBackData(HttpServletRequest request, Employee employee) {
        request.setAttribute("name", employee.getName());
        request.setAttribute("cpf", employee.getCpf());
        request.setAttribute("museum_id", employee.getMuseum().getId());
    }
}
