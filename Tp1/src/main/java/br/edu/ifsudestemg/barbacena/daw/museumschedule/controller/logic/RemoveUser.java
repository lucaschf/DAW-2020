package br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.logic;

import br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.UserDao;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Message;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Message.MessageType.SUCCESS;
import static br.edu.ifsudestemg.barbacena.daw.museumschedule.util.Constants.*;

public class RemoveUser implements Logic {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        var url = "user_list.jsp";

        if (new UserDao().remove(request.getParameter("username"))) {
            request.setAttribute(MESSAGE_PARAM, new Message(USER_SUCCESSFUL_REMOVED, SUCCESS));
        } else
            request.setAttribute(MESSAGE_PARAM, new Message(FAIL_TO_REMOVE_USER));

        request.getRequestDispatcher(url).forward(request, response);
    }
}
