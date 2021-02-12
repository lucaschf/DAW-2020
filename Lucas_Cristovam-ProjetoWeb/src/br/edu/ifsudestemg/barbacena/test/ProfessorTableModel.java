package br.edu.ifsudestemg.barbacena.test;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.edu.ifsudestemg.barbacena.model.Professor;

public class ProfessorTableModel extends AbstractTableModel {
	private static final String DEGREE = "Grau de formação";
	private static final String EMAIL = "Email";
	private static final String ID = "Id";
	private static final String NAME = "Nome";

	private static final long serialVersionUID = 1L;
	private List<Professor> professors;
	private String[] columns;

	public ProfessorTableModel(List<Professor> professors) {
		super();
		this.professors = professors;

		this.columns = new String[] { ID, NAME, EMAIL, DEGREE };
	}

	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public int getRowCount() {
		return professors.size();
	}

	@Override
	public String getColumnName(int col) {
		return columns[col];
	}

	@Override
	public Object getValueAt(int row, int col) {
		var professor = professors.get(row);
		switch (getColumnName(col)) {
		case ID:
			return professor.getId();
		case NAME:
			return professor.getName();
		case DEGREE:
			return professor.getDegree();
		case EMAIL:
			return professor.getEmail();
		default:

			return null;
		}
	}
}
