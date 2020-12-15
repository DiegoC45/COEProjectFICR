package com.coe.dao;

import android.content.Context;

import com.coe.bean.ListaAula;

import mobi.stos.podataka_lib.repository.AbstractRepository;

public class ListaAulaDao extends AbstractRepository<ListaAula> {


    public ListaAulaDao(Context context) {
        super(context, ListaAula.class);
    }
}
