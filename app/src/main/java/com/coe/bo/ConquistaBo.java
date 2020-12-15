package com.coe.bo;

import android.content.Context;

import com.coe.bean.Conquista;
import com.coe.dao.ConquistaDao;

import mobi.stos.podataka_lib.interfaces.IOperations;
import mobi.stos.podataka_lib.service.AbstractService;

public class ConquistaBo extends AbstractService<Conquista> {

    private ConquistaDao dao;

    public ConquistaBo(Context context) {
        super();
        this.dao = new ConquistaDao(context);
    }

    @Override
    protected IOperations<Conquista> getDao() {
        return dao;
    }

    public Conquista autenticado(){
        return dao.get(null,null);
    }
}
