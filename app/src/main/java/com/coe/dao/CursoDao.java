package com.coe.dao;

import android.content.Context;

import com.coe.bean.Curso;

import mobi.stos.podataka_lib.repository.AbstractRepository;

public class CursoDao extends AbstractRepository<Curso> {

    public CursoDao(Context context){
        super(context,Curso.class);
    }



}
