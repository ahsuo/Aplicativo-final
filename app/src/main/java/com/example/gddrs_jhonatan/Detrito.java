package com.example.gddrs_jhonatan;

public class Detrito {
    private String id;
    private String nomePessoa;
    private String data;
    private String endereco;
    private String tipoDetrito;
    private String telefone;
    private double latitude;
    private double longitude;

    // Construtor sem argumentos necessário para Firebase
    public Detrito() {
    }

    // Construtor com argumentos
    public Detrito(String id, String nomePessoa, String data, String endereco, String tipoDetrito, String telefone, double latitude, double longitude) {
        this.id = id;
        this.nomePessoa = nomePessoa;
        this.data = data;
        this.endereco = endereco;
        this.tipoDetrito = tipoDetrito;
        this.telefone = telefone;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters e setters para todos os campos
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomePessoa() {
        return nomePessoa;
    }

    public void setNomePessoa(String nomePessoa) {
        this.nomePessoa = nomePessoa;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTipoDetrito() {
        return tipoDetrito;
    }

    public void setTipoDetrito(String tipoDetrito) {
        this.tipoDetrito = tipoDetrito;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
