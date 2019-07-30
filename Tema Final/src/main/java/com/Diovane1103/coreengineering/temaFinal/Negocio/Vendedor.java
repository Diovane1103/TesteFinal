package com.Diovane1103.coreengineering.temaFinal.Negocio;

public class Vendedor {

    private String cpf;
    private String nome;
    private Double salario;

    public Vendedor(String cpf, String nome, Double salario) {
        this.cpf = cpf;
        this.nome = nome;
        this.salario = salario;
    }

    public String getNome() { return nome; }

    @Override
    public String toString() {
        return  "CPF: " + cpf + " - Nome: " + nome + " - Salário: " + salario;
    }
}
