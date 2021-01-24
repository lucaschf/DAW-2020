package br.edu.barbacena.ifsudestemg.daw.test;

import java.util.stream.Collectors;

import br.edu.barbacena.ifsudestemg.daw.dao.ProfessorDAO;
import br.edu.barbacena.ifsudestemg.daw.model.Professor;
import tsi.too.io.InputDialog;
import tsi.too.io.MessageDialog;

public class ProfessorTest extends CrudTest {

	private final ProfessorDAO dao;

	public ProfessorTest() {
		super("Teste de professores");
		dao = new ProfessorDAO();
	}

	@Override
	void add() {
		var professor = readProfessorData(REGISTER);

		if (professor != null)
			dao.add(professor);
	}

	@Override
	void update() {
		Long id = readProfessorId();
		if (id == null)
			return;

		var professor = readProfessorData(UPDATE);
		if (professor == null)
			return;

		professor.setId(id);
		dao.update(professor);
	}

	private Long readProfessorId() {
		return InputDialog.showLongInputDialog(UPDATE, "Id do professor", input -> {
			return input <= 0 ? "Id inválido" : "";
		});
	}

	@Override
	void delete() {
		Long id = readProfessorId();
		if (id == null)
			return;

		var professor = new Professor();
		professor.setId(id);

		dao.remove(professor);
	}

	@Override
	void listAll() {
		var message = String.join("\n", dao.fetchAll().stream().map(s -> s.toString()).collect(Collectors.toList()));
		MessageDialog.showTextMessage("Professores", message.isEmpty() ? "Nenhum professor cadastrado" : message);
	}

	private Professor readProfessorData(String title) {
		var name = InputDialog.showStringInputDialog(title, "Nome",
				InputDialog.createEmptyStringValidator("Nome nao pode ser vazio"));

		if (name == null)
			return null;

		var email = InputDialog.showStringInputDialog(title, "Email",
				InputDialog.createEmptyStringValidator("Email nao pode ser vazio"));

		if (email == null)
			return null;

		var degree = InputDialog.showStringInputDialog(title, "Grau de formação",
				InputDialog.createEmptyStringValidator("Grau de formação nao pode ser vazio"));

		if (degree == null)
			return null;

		var professor = new Professor();

		professor.setName(name);
		professor.setEmail(email);
		professor.setDegree(degree);

		return professor;
	}

	public static void main(String[] args) {
		new ProfessorTest().showMenu();
	}
	
}
