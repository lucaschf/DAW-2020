package br.edu.ifsudestemg.barbacena.daw.museumschedule.model;

import br.com.caelum.stella.tinytype.CPF;

public class Employee {

    private long id;
    private CPF cpf;
    private String name;
    private Museum museum;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CPF getCpf() {
        return cpf;
    }

    public void setCpf(CPF cpf) {
        this.cpf = cpf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Museum getMuseum() {
        return museum;
    }

    public void setMuseum(Museum museum) {
        this.museum = museum;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", cpf=" + cpf +
                ", name='" + name + '\'' +
                ", museum=" + museum +
                '}';
    }
}
