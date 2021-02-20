package br.edu.ifsudestemg.barbacena.mvc.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.edu.ifsudestemg.barbacena.mvc.logic.Logic;

public class ControllerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String param = request.getParameter("logica");
		String className = "br.edu.ifsudestemg.barbacena.mvc.logic." + param;

		try {
			Class<?> targetClass = Class.forName(className);
			Logic logic = (Logic) targetClass.getConstructor().newInstance();
			logic.execute(request, response);
		} catch (Exception e) {
			throw new ServletException("A logica de negocios causou uma exceção", e);
		}
	}

}
