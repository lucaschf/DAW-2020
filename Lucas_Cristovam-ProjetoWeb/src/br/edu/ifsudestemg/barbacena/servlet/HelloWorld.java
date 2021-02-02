package br.edu.ifsudestemg.barbacena.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloWorld extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		var out = response.getWriter();
		
		out.println("<html>");
		out.println("<body>");
		out.println("Olá mundo!");
		out.println("</body>");
		out.println("</html>");
	}
}
