package br.edu.ifsudestemg.barbacena.test;

import java.awt.Dimension;

import br.edu.ifsudestemg.barbacena.dao.ProfessorDAO;
import br.edu.ifsudestemg.barbacena.model.Professor;
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
		var professors = dao.fetchAll();

		if (professors == null) {
			MessageDialog.showErrorDialog(LIST, "Falha ao carregar professores");
			return;
		}

		if (professors.isEmpty()) {
			MessageDialog.showInformationDialog(LIST, "Nenhum professor cadastrado");
			return;
		}

		int colunsWidth[] = { 50, // id
				120, // name
				120, // email
				80, // degree
		};

		var tableModel = new ProfessorTableModel(professors);

		MessageDialog.showDataTable(null, "Professores", tableModel, colunsWidth, new Dimension(640, 400));
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
