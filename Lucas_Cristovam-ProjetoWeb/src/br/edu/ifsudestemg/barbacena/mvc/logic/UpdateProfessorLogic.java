package br.edu.ifsudestemg.barbacena.mvc.logic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.edu.ifsudestemg.barbacena.dao.ProfessorDAO;
import br.edu.ifsudestemg.barbacena.model.Professor;

public class UpdateProfessorLogic implements Logic {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Professor professor = new Professor();
		
		long id = Long.parseLong(request.getParameter("id"));
		professor.setId(id);
		professor.setName(request.getParameter("nome"));
		professor.setDegree(request.getParameter("formacao"));
		professor.setEmail(request.getParameter("email"));

		new ProfessorDAO().update(professor);

		request.getRequestDispatcher("/lista-professores-elegante.jsp").forward(request, response);
	}
}
