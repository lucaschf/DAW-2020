package br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.logic;

import br.com.caelum.stella.tinytype.CPF;
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
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        var name = request.getParameter("name");
        var cpf = new CPF(request.getParameter("cpf"));
        long museum_id = Long.parseLong(request.getParameter("museum_id"));

        var url = "employee_registration.jsp";

        if(!cpf.isValido()){
            request.setAttribute(MESSAGE_PARAM, new Message(INVALID_CPF));
            putBackData(request, name, cpf, museum_id);
        }else
        {
            Employee employee = new Employee();

            employee.setMuseum(new Museum().setId(museum_id));
            employee.setCpf(cpf);
            employee.setName(name);

            if(new EmployeeDao().add(employee)){
                request.setAttribute(MESSAGE_PARAM, new Message(EMPLOYEE_SUCCESSFUL_ADDED, SUCCESS));
            }else{
                request.setAttribute(MESSAGE_PARAM, new Message(FAIL_TO_ADD_EMPLOYEE));
                putBackData(request, name, cpf, museum_id);
            }
        }

        request.getRequestDispatcher(url).forward(request,response);
    }

    private void putBackData(HttpServletRequest request, String password, CPF username, long museum_id) {
        request.setAttribute("name", username);
        request.setAttribute("cpf", password);
        request.setAttribute("museum_id", museum_id);
    }
}
