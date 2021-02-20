package br.edu.ifsudestemg.barbacena.mvc.logic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.edu.ifsudestemg.barbacena.dao.StudentDAO;
import br.edu.ifsudestemg.barbacena.model.Student;

public class RemoveStudentLogic implements Logic {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		var id = Long.parseLong(request.getParameter("id"));

		Student student = new Student();
		student.setId(id);

		new StudentDAO().remove(student);

		request.getRequestDispatcher("/lista-alunos-elegante.jsp").forward(request, response);
	}
}
