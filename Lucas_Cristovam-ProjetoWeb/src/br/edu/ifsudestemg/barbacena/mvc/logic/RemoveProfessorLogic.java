package br.edu.ifsudestemg.barbacena.mvc.logic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.edu.ifsudestemg.barbacena.dao.ProfessorDAO;
import br.edu.ifsudestemg.barbacena.model.Professor;

public class RemoveProfessorLogic implements Logic{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		var id = Long.parseLong(request.getParameter("id"));

		var professor = new Professor();
		professor.setId(id);

		new ProfessorDAO().remove(professor);

		request.getRequestDispatcher("/lista-professores-elegante.jsp").forward(request, response);	
	}
}
