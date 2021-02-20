package br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.logic;

import br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.EmployeeDao;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Employee;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Museum;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.util.MaskUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddEmployeeLogic implements Logic {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        var employee = new Employee();

        employee.setName(request.getParameter("name"));
        employee.setCpf(MaskUtil.unmask(request.getParameter("cpf")));
        employee.setMuseum(new Museum().setId(Long.parseLong(request.getParameter("museum_id"))));

        String url = "/employee-list.jsp";

        if (!new EmployeeDao().add(employee)) {
            url = "/server-error.jsp";
            request.setAttribute("message", "Não foi possível adicionar o funcionario. Verifique os dados e tente novamente.");
            request.setAttribute("source_page", "employee-registration.jsp");
        }

        request.getRequestDispatcher(url).forward(request, response);
    }
}
