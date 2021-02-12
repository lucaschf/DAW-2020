package br.edu.ifsudestemg.barbacena.test;

import java.util.Arrays;

import tsi.too.io.InputDialog;

public abstract class CrudTest {
	
	protected final String REGISTER = "Cadastrar";
	protected final String UPDATE = "Atualizar";
	protected final String REMOVE = "Remover";
	protected final String LIST = "Listar";
	
	private final String title;
	
	public CrudTest(String title) {
		this.title = title;
	}

	abstract void add();

	abstract void update();

	abstract void delete();

	abstract void listAll();
	
	void showMenu() {
		InputDialog.showMenuDialog(title, "Selecione a opção desejada",
				Arrays.asList(REGISTER, UPDATE, REMOVE, LIST), "Sair", action -> execute(action));
	}

	public void execute(String action) {
		switch (action) {
		case REGISTER:
			add();
			break;
		case UPDATE:
			update();
			break;
		case REMOVE:
			delete();
			break;
		case LIST:
			listAll();
			break;
		default:
			break;
		}
	}
}
