package com.coe.bean;

import java.io.Serializable;

import mobi.stos.podataka_lib.annotations.Column;
import mobi.stos.podataka_lib.annotations.Entity;
import mobi.stos.podataka_lib.annotations.PrimaryKey;

@Entity
public class Aula implements Serializable {

    @PrimaryKey(autoIncrement = false)
    private String id;

    @Column
    private String videoId;

    @Column
    private String nome;

    @Column
    private String conteudo;

    @Column
    private String url;

    @Column
    private int numeroOrdem;

    @Column
    private String cursoId;

    @Column
    private int v;

    @Column
    private Boolean visualizado;

    @Column
    private String dataMatricula;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getNumeroOrdem() {
        return numeroOrdem;
    }

    public void setNumeroOrdem(int numeroOrdem) {
        this.numeroOrdem = numeroOrdem;
    }

    public String getCursoId() {
        return cursoId;
    }

    public void setCursoId(String cursoId) {
        this.cursoId = cursoId;
    }

    public int getV() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }

    public Boolean getVisualizado() {
        return visualizado;
    }

    public void setVisualizado(Boolean visualizado) {
        this.visualizado = visualizado;
    }

    public String getDataMatricula() {
        return dataMatricula;
    }

    public void setDataMatricula(String dataMatricula) {
        this.dataMatricula = dataMatricula;
    }

    @Override
    public String toString() {
        return "{" +
                "\"_id\":" + "\"" + id + "\""  +
                ",\"video\":" + "\"" + videoId + "\"" +
                ", \"visualizado\":" +  visualizado +
                '}';


    }
}
