package com.coe.bean;

import java.io.Serializable;

import mobi.stos.podataka_lib.annotations.Column;
import mobi.stos.podataka_lib.annotations.Entity;
import mobi.stos.podataka_lib.annotations.PrimaryKey;

@Entity
public class Conquista implements Serializable {

    @PrimaryKey(autoIncrement = false)
    private String id;

    @Column
    private String nome;

    @Column
    private String imgUrl;

    @Column
    private int drawable;


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

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
