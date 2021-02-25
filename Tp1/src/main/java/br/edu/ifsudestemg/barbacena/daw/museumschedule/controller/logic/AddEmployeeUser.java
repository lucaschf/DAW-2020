package br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.logic;

import br.com.caelum.stella.tinytype.CPF;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.UserDao;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Message;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Message.MessageType.SUCCESS;
import static br.edu.ifsudestemg.barbacena.daw.museumschedule.util.Constants.*;

public class AddEmployeeUser implements Logic {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        var password = request.getParameter("password");
        var username = new CPF(request.getParameter("username"));
        long museum_id = Long.parseLong(request.getParameter("museum_id"));

        var url = "employee-registration.jsp";

        if(!username.isValido()){
            request.setAttribute(MESSAGE_PARAM, new Message(INVALID_CPF));
            putBackData(request, password, username, museum_id);
        }else
        {
            User user = new User();

            user.setRole(User.Role.EMPLOYEE);
            user.setMuseum_id(museum_id);
            user.setUsername(username.getNumero());
            user.setPassword(password);

            if(new UserDao().add(user)){
                request.setAttribute(MESSAGE_PARAM, new Message(USER_SUCCESSFUL_ADDED, SUCCESS));
            }else{
                request.setAttribute(MESSAGE_PARAM, new Message(FAIL_TO_ADD_USER));
                putBackData(request, password, username, museum_id);
            }
        }

        request.getRequestDispatcher(url).forward(request,response);
    }

    private void putBackData(HttpServletRequest request, String password, CPF username, long museum_id) {
        request.setAttribute("username", username);
        request.setAttribute("password", password);
        request.setAttribute("museum_id", museum_id);
    }
}
