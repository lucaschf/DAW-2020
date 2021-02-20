package br.edu.ifsudestemg.barbacena.mvc.logic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.edu.ifsudestemg.barbacena.dao.ProfessorDAO;
import br.edu.ifsudestemg.barbacena.model.Professor;

public class AdddProfessorLogic implements Logic {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		var professor = new Professor();
		
		professor.setName(request.getParameter("name"));
		professor.setDegree(request.getParameter("degree"));
		professor.setEmail(request.getParameter("email"));

		new ProfessorDAO().add(professor);

		request.getRequestDispatcher("/lista-professores-elegante.jsp").forward(request, response);
	}
}
