package bt.tsi.daw.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bt.tsi.daw.model.Contact;
import bt.tsi.daw.model.ContactBook;

@SuppressWarnings("serial")
@WebServlet("/contactsList")
public class ListContacts extends HttpServlet{
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		var out = response.getWriter();

		out.println("<html>");
		out.println("<body>");
		out.println("<h2>Contatos</h2>");
		out.println("<table border=1px>");
		out.println("<tr>");
		out.println("<th>Nome</th>");
		out.println("<th>E-mail</th>");
		out.println("<th>Telefone</th>");
		out.println("</tr>");

		for (Contact c : ContactBook.list()) {
			
			var name = String.format("<tr><td>%s</td>", c.getName());
			var email = String.format("<td>%s</td>", c.getEmail());
			var phone = String.format("<td>%s</td></tr>",c.getPhone());
			
			out.println(name);
			out.println(email);
			out.println(phone);
		}

		out.println("</table>");
		out.println("</body>");
		out.println("</html>");
	}
}
