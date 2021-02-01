package bt.tsi.daw.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bt.tsi.daw.model.ContactBook;

@SuppressWarnings("serial")
@WebServlet("/findContact")
public class FindContact extends HttpServlet {

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String target = request.getParameter("nome");

		var out = response.getWriter();

		var found = ContactBook.find(target);

		out.println("<html>");
		out.println("<body>");
		if (found.isPresent()) {
			out.println("<table border=1px>");
			out.println("<tr>");
			out.println("<th>Nome</th>");
			out.println("<th>E-mail</th>");
			out.println("<th>Telefone</th>");
			out.println("</tr>");
			
			var c = found.get();
			
			var name = String.format("<tr><td>%s</td>", c.getName());
			var email = String.format("<td>%s</td>", c.getEmail());
			var phone = String.format("<td>%s</td></tr>",c.getPhone());
			
			out.println(name);
			out.println(email);
			out.println(phone);
			
			out.println("</table>");
		} else
			out.println("<h1>Contato " + target + " não encontrado </h1>");

		out.println("</body>");
		out.println("</html>");
	}
}
