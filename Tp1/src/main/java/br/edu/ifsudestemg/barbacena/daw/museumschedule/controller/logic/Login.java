package br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.logic;

import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Message;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Login implements  Logic {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("LOGICA");

        request.setAttribute(MESSAGE_PARAM, new Message("Usuario ou senha incorretos"));
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}
