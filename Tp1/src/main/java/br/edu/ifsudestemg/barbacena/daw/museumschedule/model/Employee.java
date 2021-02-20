package br.edu.ifsudestemg.barbacena.daw.museumschedule.model;

public class Employee {

    private String cpf;
    private String name;
    private Museum museum;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
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
                "cpf=" + cpf +
                ", name='" + name + '\'' +
                ", museum=" + museum +
                '}';
    }
}
