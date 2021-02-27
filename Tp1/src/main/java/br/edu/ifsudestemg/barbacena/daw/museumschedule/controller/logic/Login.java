package br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.logic;

import br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.PagesNames;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.UserDao;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Message;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Login implements Logic {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        var username = request.getParameter("username");
        var password = request.getParameter("password");

        var user = new UserDao().auth(username, password);

        if (user == null) {
            request.setAttribute(MESSAGE_PARAM, new Message("Usuario ou senha incorretos"));
            request.setAttribute("username", username);
            return PagesNames.LOGIN;
        } else {
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(15 * 60);
            session.setAttribute("status", true);
            session.setAttribute("user", user.withoutPassword());
        }

        return PagesNames.HOME;
    }
}
