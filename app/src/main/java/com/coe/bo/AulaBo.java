package com.coe.bo;

import android.content.Context;

import com.coe.bean.Aula;
import com.coe.dao.AulaDao;

import mobi.stos.podataka_lib.interfaces.IOperations;
import mobi.stos.podataka_lib.service.AbstractService;

public class AulaBo extends AbstractService<Aula> {

    private AulaDao dao;

    public AulaBo(Context context) {
        super();
        this.dao = new AulaDao(context);
    }

    @Override
    protected IOperations<Aula> getDao() {
        return dao;
    }

    public Aula autenticado(){
        return dao.get(null,null);
    }
}
