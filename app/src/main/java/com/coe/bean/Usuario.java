package com.coe.bean;

import java.io.Serializable;

import mobi.stos.podataka_lib.annotations.Column;
import mobi.stos.podataka_lib.annotations.Entity;
import mobi.stos.podataka_lib.annotations.PrimaryKey;

@Entity
public class Usuario implements Serializable {

    @PrimaryKey(autoIncrement = false)
    private String id;

    @Column
    private String nome;

    @Column
    private  String sobreNome;

    @Column
    private String email;

    @Column
    private String senha;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobreNome() {
        return sobreNome;
    }

    public void setSobreNome(String sobreNome) {
        this.sobreNome = sobreNome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
