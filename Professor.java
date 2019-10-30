package com.example.cadastroponto;

import androidx.annotation.NonNull;

public class Professor {

    private String nome, data, entrada, saida, disciplina;

    public Professor(String nome, String data, String entrada, String saida, String disciplina){
        this.nome = nome;
        this.data = data;
        this.entrada = entrada;
        this.saida = saida;
        this.disciplina = disciplina;
    }

    public String getNome() {
        return nome;
    }

    public String getData() {
        return data;
    }

    public String getEntrada() {
        return entrada;
    }

    public String getSaida() {
        return saida;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public String toString() {
        return nome + " - " + data;
    }
}
