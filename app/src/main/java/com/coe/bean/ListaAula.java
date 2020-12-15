package com.coe.bean;

import java.io.Serializable;

import mobi.stos.podataka_lib.annotations.Column;
import mobi.stos.podataka_lib.annotations.Entity;
import mobi.stos.podataka_lib.annotations.PrimaryKey;

@Entity
public class ListaAula implements Serializable {

    @PrimaryKey (autoIncrement = false)
    private int id;

    @Column
    private String nome;

    @Column
    private Boolean assistido;

    public ListaAula() {
    }

    public ListaAula(String nome, Boolean assistido) {
        this.nome = nome;
        this.assistido = assistido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean getAssistido() {
        return assistido;
    }

    public void setAssistido(Boolean assistido) {
        this.assistido = assistido;
    }
}
