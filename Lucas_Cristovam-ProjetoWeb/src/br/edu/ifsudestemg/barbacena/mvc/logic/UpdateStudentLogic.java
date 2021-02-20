package br.edu.ifsudestemg.barbacena.mvc.logic;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.edu.ifsudestemg.barbacena.dao.StudentDAO;
import br.edu.ifsudestemg.barbacena.model.Student;

public class UpdateStudentLogic implements Logic {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Student aluno = new Student();
		long id = Long.parseLong(request.getParameter("id"));
		aluno.setId(id);
		aluno.setName(request.getParameter("nome"));
		aluno.setAddress(request.getParameter("endereco"));
		aluno.setEmail(request.getParameter("email"));

		// Converte a data de String para Calendar
		String dataEmTexto = request.getParameter("dataNascimento");
		var date = LocalDate.parse(dataEmTexto, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		aluno.setBirthDate(date);

		StudentDAO dao = new StudentDAO();
		dao.update(aluno);

		RequestDispatcher rd = request.getRequestDispatcher("/lista-alunos-elegante.jsp");
		rd.forward(request, response);
		System.out.println("Alterando aluno ..." + aluno.getName());
	}
}
