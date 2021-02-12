package br.edu.ifsudestemg.barbacena.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.edu.ifsudestemg.barbacena.dao.ProfessorDAO;
import br.edu.ifsudestemg.barbacena.model.Professor;

public class AddProfessorServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		var out = response.getWriter();

		Professor professor = new Professor();
		professor.setName(request.getParameter("name"));
		professor.setDegree(request.getParameter("degree"));
		professor.setEmail(request.getParameter("email"));

		ProfessorDAO dao = new ProfessorDAO();
		dao.add(professor);

		out.println("<html>");
		out.println("<body>");
		out.println("Professor " + professor.getName() + " adicionado com sucesso");
		out.println("</body>");
		out.println("</html>");
	}
}