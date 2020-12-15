package com.coe.bo;

import android.content.Context;

import com.coe.bean.Matricula;
import com.coe.dao.MatriculaDao;

import mobi.stos.podataka_lib.interfaces.IOperations;
import mobi.stos.podataka_lib.service.AbstractService;

public class MatriculaBo extends AbstractService<Matricula> {

    private MatriculaDao dao;

    public MatriculaBo(Context context) {
        super();
        this.dao = new MatriculaDao(context);
    }


    @Override
    protected IOperations<Matricula> getDao() {
        return dao;
    }

    public Matricula autenticado(){
        return dao.get(null, null);
    }
}
