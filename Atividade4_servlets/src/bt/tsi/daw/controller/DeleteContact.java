package bt.tsi.daw.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bt.tsi.daw.model.ContactBook;

@SuppressWarnings("serial")
@WebServlet("/delete")
public class DeleteContact extends HttpServlet {

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String name = request.getParameter("nome");

		var out = response.getWriter();
		var removed = ContactBook.remove(name);

		var message = removed ? " removido com sucesso" : " não encontrado";

		out.println("<html>");
		out.println("<body>");
		out.println("<h1>Contato " + name + message + "</h1>");
		out.println("</body>");
		out.println("</html>");
	}
}
