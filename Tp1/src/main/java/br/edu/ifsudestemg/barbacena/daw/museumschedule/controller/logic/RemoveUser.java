package br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.logic;

import br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.PagesNames;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.UserDao;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Message;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Message.MessageType.SUCCESS;
import static br.edu.ifsudestemg.barbacena.daw.museumschedule.util.Constants.*;

public class RemoveUser implements Logic {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Message message;

        if (new UserDao().remove(request.getParameter("username"))) {
            message = new Message(USER_SUCCESSFUL_REMOVED, SUCCESS);
        } else
            message = new Message(FAIL_TO_REMOVE_USER);

        request.setAttribute(MESSAGE_PARAM, message);

        return PagesNames.USER_LIST;
    }
}
