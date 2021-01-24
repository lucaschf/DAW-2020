package br.edu.barbacena.ifsudestemg.daw.model;

import java.time.LocalDate;

public class Student {

	private Long id;
	private String name;
	private String email;
	private String address;
	private LocalDate birthDate;

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	@Override
	public String toString() {
		return String.format("Student {id=%s, name=%s, email=%s, address=%s, birthDate=%s}", id, name, email, address,
				birthDate);
	}

}
