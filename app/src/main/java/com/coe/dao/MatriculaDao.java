package com.coe.dao;

import android.content.Context;

import com.coe.bean.Matricula;

import mobi.stos.podataka_lib.repository.AbstractRepository;

public class MatriculaDao extends AbstractRepository<Matricula> {

    public MatriculaDao(Context context){
        super(context, Matricula.class);
    }
}
