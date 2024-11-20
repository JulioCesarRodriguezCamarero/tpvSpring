package org.grisu.tpvspring.servicio;

import org.grisu.tpvspring.modelo.CajaPrincipal;
import org.grisu.tpvspring.repositorio.CajaPrincipalRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CajaPrincipalImpl implements ICajaPrincipalServicio {
    @Autowired
    private CajaPrincipalRepositorio repositorio;

    @Override
    @Transactional(readOnly = true)
    public List<CajaPrincipal> listar() {
        return repositorio.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public CajaPrincipal buscar(long id) {
        return repositorio.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void agregar(CajaPrincipal cajaPrincipal) {
        repositorio.save(cajaPrincipal);
    }

    @Override
    @Transactional
    public void eliminar(CajaPrincipal cajaPrincipal) {
        repositorio.delete(cajaPrincipal);

    }

    @Override
    public CajaPrincipal buscarUsuarioActivo(boolean activo) {
        return repositorio.findByActivoWithUsuarios(activo);
    }


}
