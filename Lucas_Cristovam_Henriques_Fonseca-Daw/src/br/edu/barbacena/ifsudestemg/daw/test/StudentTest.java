package br.edu.barbacena.ifsudestemg.daw.test;

import java.time.LocalDate;
import java.util.stream.Collectors;

import br.edu.barbacena.ifsudestemg.daw.dao.StudentDAO;
import br.edu.barbacena.ifsudestemg.daw.model.Student;
import tsi.too.io.InputDialog;
import tsi.too.io.MessageDialog;

public class StudentTest extends CrudTest {

	private final StudentDAO dao;

	public StudentTest() {
		super("Teste de alunos");
		this.dao = new StudentDAO();
	}

	@Override
	void add() {
		var student = readStudentData(REGISTER);

		if (student != null)
			dao.add(student);
	}

	@Override
	void update() {
		Long id = readStudentId();
		if (id == null)
			return;

		var student = readStudentData(UPDATE);
		if (student == null)
			return;

		student.setId(id);
		dao.update(student);
	}

	private Long readStudentId() {
		return InputDialog.showLongInputDialog(UPDATE, "Id do aluno", input -> {
			return input <= 0 ? "Id inv�lido" : "";
		});
	}

	@Override
	void delete() {
		Long id = readStudentId();
		if (id == null)
			return;

		var student = new Student();
		student.setId(id);

		dao.remove(student);
	}

	@Override
	void listAll() {
		var message = String.join("\n", dao.fetchAll().stream().map(s -> s.toString()).collect(Collectors.toList()));
		MessageDialog.showTextMessage("Alunos", message.isEmpty() ? "Nenhum aluno cadastrado" : message);
	}

	private Student readStudentData(String title) {
		var name = InputDialog.showStringInputDialog(title, "Nome",
				InputDialog.createEmptyStringValidator("Nome nao pode ser vazio"));

		if (name == null)
			return null;

		var email = InputDialog.showStringInputDialog(title, "Email",
				InputDialog.createEmptyStringValidator("Email nao pode ser vazio"));

		if (email == null)
			return null;

		var address = InputDialog.showStringInputDialog(title, "Endere�o",
				InputDialog.createEmptyStringValidator("Endere�o nao pode ser vazio"));

		if (address == null)
			return null;

		var birthDate = InputDialog.showBrazilianDateInputDialog(title, "Data de nascimento", date -> {
			return date.isAfter(LocalDate.now()) ? "Data invalida" : "";
		});

		if (birthDate == null)
			return null;

		var student = new Student();

		student.setName(name);
		student.setEmail(email);
		student.setAddress(address);
		student.setBirthDate(birthDate);

		return student;
	}
	
	public static void main(String[] args) {
		new StudentTest().showMenu();
	}
}
