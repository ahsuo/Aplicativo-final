package com.example.gddrs_jhonatan;

public class Detrito {
    private String nome;
    private String endereco;
    private String tipoDetrito;
    private String telefone; // Novo campo de telefone

    public Detrito(String nome, String endereco, String tipoDetrito, String telefone) {
        this.nome = nome;
        this.endereco = endereco;
        this.tipoDetrito = tipoDetrito;
        this.telefone = telefone; // Inicializar o novo campo
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getTipoDetrito() {
        return tipoDetrito;
    }

    public String getTelefone() {
        return telefone;
    }
}
