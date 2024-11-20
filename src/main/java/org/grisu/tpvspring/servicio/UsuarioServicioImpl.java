package org.grisu.tpvspring.servicio;

import org.grisu.tpvspring.modelo.Usuario;
import org.grisu.tpvspring.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioServicioImpl implements IUsuarioServicio {
    @Autowired
    private UsuarioRepositorio repositorio;

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listar() {
        return repositorio.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario buscar(long id) {
        return repositorio.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario buscar(String nombre) {
        return repositorio.findByNombre(nombre);
    }

//    @Override
//    @Transactional(readOnly = true)
//    public Usuario buscarActivo(boolean activo) {
//        return repositorio.findByActivo(activo);
//    }

    @Override
    @Transactional
    public void agregar(Usuario usuario) {
        repositorio.save(usuario);
    }

    @Override
    @Transactional
    public void eliminar(Usuario usuario) {
        repositorio.delete(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario buscarActivoConCuentas(boolean activo) {
        return repositorio.findByActivoWithCuentas(activo);
    }


}
