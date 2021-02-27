package br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.logic;

import br.com.caelum.stella.tinytype.CPF;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.EmployeeDao;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.UserDao;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Message;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.util.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchEmployee implements Logic {

    private final String cpfParameter = "cpf";
    private final String museumId = "museum_id";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        var cpf = request.getParameter(cpfParameter);
        long museumId = Long.parseLong(request.getParameter(this.museumId));

        var url = "user_registration.jsp";

        var employee = new EmployeeDao().fetchByCpfInMuseum(new CPF(cpf), museumId);

        if (employee == null) {
            request.setAttribute(MESSAGE_PARAM, new Message(Constants.EMPLOYEE_NOT_FOUND));
            putBackData(request, cpf, museumId);
            url = "employee_search.jsp";
        } else {
            if (new UserDao().fetchByEmployee(employee.getId()) != null) {
                request.setAttribute(MESSAGE_PARAM, new Message(Constants.USER_ALREADY_REGISTERED_FOR_THIS_EMPLOYEE));
                putBackData(request, cpf, museumId);
                url = "employee_search.jsp";
            } else
                request.setAttribute("employee", employee);
        }

        return url;
    }


    private void putBackData(HttpServletRequest request, String cpf, long museum_id) {
        request.setAttribute(cpfParameter, cpf);
        request.setAttribute(museumId, museum_id);
    }
}
