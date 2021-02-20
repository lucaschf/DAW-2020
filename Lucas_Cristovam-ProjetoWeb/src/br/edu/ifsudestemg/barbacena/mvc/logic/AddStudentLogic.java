package br.edu.ifsudestemg.barbacena.mvc.logic;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.edu.ifsudestemg.barbacena.dao.StudentDAO;
import br.edu.ifsudestemg.barbacena.model.Student;

public class AddStudentLogic implements Logic {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		var out = response.getWriter();

		String strDate = request.getParameter("dataNascimento");

		Student student = new Student();
		student.setName(request.getParameter("nome"));
		student.setAddress(request.getParameter("endereco"));
		student.setEmail(request.getParameter("email"));

		try {
			var date = LocalDate.parse(strDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			student.setBirthDate(date);
		} catch (Exception e) {
			out.println("Erro de conversão da data");
			return;
		}

		new StudentDAO().add(student);

		request.getRequestDispatcher("/lista-alunos-elegante.jsp").forward(request, response);
	}

}
