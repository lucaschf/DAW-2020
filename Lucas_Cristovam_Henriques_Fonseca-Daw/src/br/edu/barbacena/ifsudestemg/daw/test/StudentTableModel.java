package br.edu.barbacena.ifsudestemg.daw.test;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.edu.barbacena.ifsudestemg.daw.model.Student;

public class StudentTableModel extends AbstractTableModel {
	private static final String ADDRESS = "Endereco";
	private static final String EMAIL = "Email";
	private static final String BIRTH_DATE = "DataNascimento";
	private static final String ID = "Id";
	private static final String NAME = "Nome";

	private static final long serialVersionUID = 1L;
	private List<Student> students;
	private String[] columns;

	public StudentTableModel(List<Student> students) {
		super();
		this.students = students;

		this.columns = new String[] { ID, NAME, EMAIL, BIRTH_DATE, ADDRESS };
	}

	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public int getRowCount() {
		return students.size();
	}

	@Override
	public String getColumnName(int col) {
		return columns[col];
	}

	@Override
	public Object getValueAt(int row, int col) {
		var student = students.get(row);
		switch (getColumnName(col)) {
		case ID:
			return student.getId();
		case NAME:
			return student.getName();
		case ADDRESS:
			return student.getAddress();
		case BIRTH_DATE:
			return student.getBirthDate();
		case EMAIL:
			return student.getEmail();
		default:

			return null;
		}
	}
}
