package com.coe.bo;

import android.content.Context;

import com.coe.bean.Usuario;
import com.coe.dao.UsuarioDao;

import mobi.stos.podataka_lib.interfaces.IOperations;
import mobi.stos.podataka_lib.service.AbstractService;

public class UsuarioBo extends AbstractService<Usuario> {

    private UsuarioDao dao;

    public UsuarioBo(Context context) {
        super();
        this.dao = new UsuarioDao(context);
    }

    @Override
    protected IOperations<Usuario> getDao() {
        return dao;
    }

    public Usuario autenticado(){
      return dao.get(null, null);
    }
}
