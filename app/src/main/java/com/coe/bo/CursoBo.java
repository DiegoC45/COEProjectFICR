package com.coe.bo;

import android.content.Context;

import com.coe.bean.Curso;
import com.coe.dao.CursoDao;

import mobi.stos.podataka_lib.interfaces.IOperations;
import mobi.stos.podataka_lib.service.AbstractService;

public class CursoBo extends AbstractService<Curso> {

    private CursoDao cursoDao;

    public CursoBo(Context context) {
        super();
        this.cursoDao = new CursoDao(context);
    }

    @Override
    protected IOperations<Curso> getDao() {
        return cursoDao;
    }

    public Curso autenticado(){
        return cursoDao.get(null, null);
    }
}
