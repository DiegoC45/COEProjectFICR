package com.coe.bean;

import java.io.Serializable;
import java.util.List;

import mobi.stos.podataka_lib.annotations.Column;
import mobi.stos.podataka_lib.annotations.Entity;
import mobi.stos.podataka_lib.annotations.PrimaryKey;
import mobi.stos.podataka_lib.annotations.Transient;

@Entity
public class Matricula implements Serializable {

    @PrimaryKey(autoIncrement = false)
    private String id;

    @Column
    private Boolean statusAprovacao;

    @Column
    private String usuarioId;

    @Column
    private String nome;

    @Column
    private String cursoId;

    @Column
    private String descricao;

    @Transient
    private List<Aula> aulas;

    @Column
    private String dataMatricula;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getCursoId() {
        return cursoId;
    }

    public void setCursoId(String cursoId) {
        this.cursoId = cursoId;
    }

    public List<Aula> getAulas() {
        return aulas;
    }

    public void setAulas(List<Aula> aulas) {
        this.aulas = aulas;
    }

    public String getDataMatricula() {
        return dataMatricula;
    }

    public void setDataMatricula(String dataMatricula) {
        this.dataMatricula = dataMatricula;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getStatusAprovacao() {
        return statusAprovacao;
    }

    public void setStatusAprovacao(Boolean statusAprovacao) {
        this.statusAprovacao = statusAprovacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
