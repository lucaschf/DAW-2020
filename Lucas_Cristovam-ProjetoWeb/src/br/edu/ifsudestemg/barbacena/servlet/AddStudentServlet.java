package br.edu.ifsudestemg.barbacena.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.edu.ifsudestemg.barbacena.dao.StudentDAO;
import br.edu.ifsudestemg.barbacena.model.Student;

public class AddStudentServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

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

		StudentDAO dao = new StudentDAO();
		dao.add(student);

		out.println("<html>");
		out.println("<body>");
		out.println("Aluno " + student.getName() + " adicionado com sucesso");
		out.println("</body>");
		out.println("</html>");
	}
}