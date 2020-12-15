package com.coe.dao;

import android.content.Context;

import com.coe.bean.Conquista;

import mobi.stos.podataka_lib.repository.AbstractRepository;

public class ConquistaDao extends AbstractRepository<Conquista> {

    public ConquistaDao(Context context) {
        super(context, Conquista.class);
    }
}
