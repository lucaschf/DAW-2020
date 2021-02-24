package br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.logic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Logout implements Logic{

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false);
        session.invalidate();

        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}
