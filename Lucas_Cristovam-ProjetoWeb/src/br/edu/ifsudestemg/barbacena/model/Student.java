package br.edu.ifsudestemg.barbacena.model;

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

	public Student setId(Long id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public Student setName(String name) {
		this.name = name;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public Student setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getAddress() {
		return address;
	}

	public Student setAddress(String address) {
		this.address = address;
		return this;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public Student setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
		return this;
	}

	@Override
	public String toString() {
		return String.format("Student {id=%s, name=%s, email=%s, address=%s, birthDate=%s}", id, name, email, address,
				birthDate);
	}

}
