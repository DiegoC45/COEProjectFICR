package com.coe.bo;

import android.content.Context;

import com.coe.bean.ListaAula;
import com.coe.dao.ListaAulaDao;

import mobi.stos.podataka_lib.interfaces.IOperations;
import mobi.stos.podataka_lib.service.AbstractService;

public class ListaAulaBo extends AbstractService<ListaAula> {

    private ListaAulaDao dao;

    public ListaAulaBo(Context context) {
        super();
        this.dao = new ListaAulaDao(context);
    }

    @Override
    protected IOperations<ListaAula> getDao() {
        return dao;
    }

    public ListaAula autenticado(){
        return dao.get(null,null);
    }
}
