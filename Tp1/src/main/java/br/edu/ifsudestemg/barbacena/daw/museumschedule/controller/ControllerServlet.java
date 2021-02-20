package br.edu.ifsudestemg.barbacena.daw.museumschedule.controller;

import br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.logic.Logic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/scheduler")
public class ControllerServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        var param = request.getParameter("logic");
        var className = String.format("%s.logic.%s", this.getClass().getPackageName(), param);

        try {
            Class<?> targetClass = Class.forName(className);
            Logic logic = (Logic) targetClass.getConstructor().newInstance();
            logic.execute(request, response);
        } catch (Exception e) {
            throw new ServletException("Business logic caused an exception", e);
        }
    }
}
