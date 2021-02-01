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
@WebServlet("/addContact")
public class AddContact extends HttpServlet {

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String name = request.getParameter("nome");
		String email = request.getParameter("email");
		String phone = request.getParameter("telefone");

		var out = response.getWriter();

		var contact = new Contact(name, email, phone);
		var added = ContactBook.addContact(contact);

		var message = added ? " adicionado com sucesso" : " não adicionado";

		out.println("<html>");
		out.println("<body>");
		out.println("<h1>Contato " + name + message + "</h1>");
		out.println("</body>");
		out.println("</html>");
	}
}
