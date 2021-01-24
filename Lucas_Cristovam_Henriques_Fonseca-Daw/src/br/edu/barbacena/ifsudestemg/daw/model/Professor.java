package br.edu.barbacena.ifsudestemg.daw.model;

public class Professor {

	private Long id;
	private String name;
	private String email;
	private String degree;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	@Override
	public String toString() {
		return "Professor {id=" + id + ", name=" + name + ", email=" + email + ", degree=" + degree + "}";
	}

}
