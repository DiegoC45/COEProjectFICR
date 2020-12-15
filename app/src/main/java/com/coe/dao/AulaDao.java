package com.coe.dao;

import android.content.Context;

import com.coe.bean.Aula;

import mobi.stos.podataka_lib.repository.AbstractRepository;

public class AulaDao extends AbstractRepository<Aula> {

    public AulaDao(Context context) {
        super(context, Aula.class);
    }
}
